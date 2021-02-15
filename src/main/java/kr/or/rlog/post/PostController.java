package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/write")
    public String write(){
        return "write";
    }

    @PostMapping("/write")
    public String savePost(@CurrentUser Account user, @ModelAttribute Post post){
        Post savedPost = postService.createNew(post, user);
        if(savedPost != null)
            return "redirect:/post/" + savedPost.getId();
        else
            return "/error";
    }

    @GetMapping("post/{id}")
    public String getPost(Model model, @PathVariable Long id){
        Optional<Post> post = postService.getPost(id);
        if(post.isPresent())
            model.addAttribute("post", post.get());
        else
            model.addAttribute("message", "존재하지 않는 글입니다");
        return "/post";
    }

    @GetMapping("posts")
    public String getPosts(Model model){
        model.addAttribute("posts", postService.postAll());
        return "/posts";
    }

}
