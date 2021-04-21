package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import kr.or.rlog.common.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class PostLikesController {

    @Autowired
    private PostLikesService postLikesService;

    @PostMapping("post/{postId}/like")
    public ResponseEntity<Message> postLike(@CurrentUser Account user, @PathVariable Long postId) {
        if (user == null) {
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("post.like.deny.code"))
                    .response(MessageUtils.getMessage("post.like.deny.message"))
                    .build(),
                    HttpStatus.OK);
        } else if (postLikesService.createNew(user, postId)) {
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("post.like.ok.code"))
                    .response(MessageUtils.getMessage("post.like.ok.message"))
                    .build(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("post.like.fail.code"))
                    .response(MessageUtils.getMessage("post.like.fail.message"))
                    .build(),
                    HttpStatus.OK);
        }
    }

    @PutMapping("post/{postId}/like")
    public ResponseEntity<Message> postLikeUpdate(@CurrentUser Account user, @PathVariable Long postId){
        if(user == null){
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("post.like.edit.deny.code"))
                    .response(MessageUtils.getMessage("post.like.edit.deny.message"))
                    .build(),
                    HttpStatus.OK);
        }else if(postLikesService.editProc(user, postId)){
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("post.like.edit.ok.code"))
                    .response(MessageUtils.getMessage("post.like.edit.ok.message"))
                    .build(),
                    HttpStatus.OK);
        }else{
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("post.like.edit.fail.code"))
                    .response(MessageUtils.getMessage("post.like.edit.fail.message"))
                    .build(),
                    HttpStatus.OK);
        }
    }

}
