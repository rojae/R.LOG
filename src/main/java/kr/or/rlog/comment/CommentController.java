package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comments/{postId}")
    public String createComment(@PathVariable Long postId, @CurrentUser Account user, @ModelAttribute Comment comment){
        if(commentService.createNew(comment, user, postId))
            return "redirect:/post/" + postId;
        else
            return "/error";
    }

}
