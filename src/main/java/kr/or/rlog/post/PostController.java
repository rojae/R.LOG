package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.category.Category;
import kr.or.rlog.category.CategoryService;
import kr.or.rlog.comment.CommentService;
import kr.or.rlog.common.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/admin/write")
    public String write(){
        return "/admin/page-blog-write";
    }

    @PostMapping("/admin/write")
    public String savePost(@CurrentUser Account user, @ModelAttribute Post post){
        Post savedPost = postService.createNew(post, user);
        if(savedPost != null)
            return "redirect:/post/" + savedPost.getId();
        else
            return "/error";
    }

    @GetMapping("post/{id}")
    public String getPost(Model model, @PathVariable Long id, @CurrentUser Account account){
        Optional<Post> post = postService.getPost(id);
        if(post.isPresent()) {
            model.addAttribute("post", post.get());
            model.addAttribute("categories", categoryService.getParentsAndMe(post.get().getCategory()));
            model.addAttribute("comments", commentService.getComment(post.get()));
        }
        else
            model.addAttribute("message", "존재하지 않는 글입니다");
        return "page-blog-post";
    }

    @GetMapping("posts")
    @ResponseBody
    public ResponseEntity getPosts(Pageable pageable){
        Page<PostDto> posts = postService.getPage(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
