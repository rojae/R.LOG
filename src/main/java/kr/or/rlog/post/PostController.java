package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.category.CategoryService;
import kr.or.rlog.comment.CommentService;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    @Secured("ROLE_ADMIN")
    public String write() {
        return "/admin/page-blog-write";
    }

    @PostMapping("/admin/write")
    @Secured("ROLE_ADMIN")
    public String savePost(@CurrentUser Account user, @ModelAttribute Post post) {
        Post savedPost = postService.createNew(post, user);
        if (savedPost != null)
            return "redirect:/post/" + savedPost.getId();
        else
            return "/error";
    }

    @GetMapping("/admin/write/{id}")
    @Secured("ROLE_ADMIN")
    public String edit(Model model, @PathVariable Long id, @CurrentUser Account account) {
        Optional<Post> post = postService.getPost(id);
        if (post.isPresent()) {
            if (post.get().getWriter().getId().equals(account.getId())) {
                model.addAttribute("post", post.get());
                model.addAttribute("category", post.get().getCategory());
            } else
                model.addAttribute("message", "권한이 없는 글입니다");
        } else
            model.addAttribute("message", "존재하지 않는 글입니다");
        return "/admin/page-blog-write";
    }

    @PostMapping("/admin/write/{id}")
    @Secured("ROLE_ADMIN")
    public String editSave(Model model, @ModelAttribute Post newPost, @PathVariable Long id, @CurrentUser Account account) {
        Optional<Post> oldPost = postService.getPost(id);
        if (oldPost.isPresent()) {
            if (oldPost.get().getWriter().getId().equals(account.getId())) {
                postService.editSave(newPost);
            } else
                model.addAttribute("message", "권한이 없는 글입니다");
        } else
            model.addAttribute("message", "존재하지 않는 글입니다");
        return "redirect:/post/" + newPost.getId();
    }

    @GetMapping("post/{id}")
    public String getPost(Model model, @PathVariable Long id, @CurrentUser Account account) {
        Optional<Post> post = postService.getPost(id);
        if (post.isPresent()) {
            if (account != null)
                model.addAttribute("isWriter", post.get().getWriter().getId().equals(account.getId()));
            model.addAttribute("post", post.get());
            model.addAttribute("categories", categoryService.getParentsAndMe(post.get().getCategory()));
        } else
            model.addAttribute("message", "존재하지 않는 글입니다");
        return "page-blog-post";
    }

    // delete to update로 수정하여, 삭제되도 보관되도록 개발 필요
    @DeleteMapping("post/{id}")
    @ResponseBody
    public ResponseEntity<Message> deletePost(Model model, @PathVariable Long id, @CurrentUser Account account) {
        if(postService.deletePost(id, account))
            return new ResponseEntity<Message>(Message.builder().response("삭제되었습니다. 자동으로 메인으로 이동합니다.").code("200").build(), HttpStatus.OK);
        else
            return new ResponseEntity<Message>(Message.builder().response("접근이 거부되었습니다.").code("403").build(), HttpStatus.FORBIDDEN);
    }

    @GetMapping("posts")
    @ResponseBody
    public ResponseEntity getPosts(Pageable pageable, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        System.out.println(pageable.getPageNumber());
        Page<PostDto> posts = postService.getPage(pageable, keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
