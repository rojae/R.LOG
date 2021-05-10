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
public class CommentLikeController {

    @Autowired
    private CommentLikesService commentLikesService;

    @PostMapping("comment/{commentId}/like")
    public ResponseEntity<Message> postLike(@CurrentUser Account user, @PathVariable Long commentId) {
        if (user == null) {
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("comment.like.deny.code"))
                    .response(MessageUtils.getMessage("comment.like.deny.message"))
                    .build(),
                    HttpStatus.OK);
        } else if (commentLikesService.createNew(user, commentId)) {
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("comment.like.ok.code"))
                    .response(MessageUtils.getMessage("comment.like.ok.message"))
                    .build(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("comment.like.fail.code"))
                    .response(MessageUtils.getMessage("comment.like.fail.message"))
                    .build(),
                    HttpStatus.OK);
        }
    }

    @PutMapping("comment/{commentId}/like")
    public ResponseEntity<Message> postLikeUpdate(@CurrentUser Account user, @PathVariable Long commentId){
        if(user == null){
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("comment.like.edit.deny.code"))
                    .response(MessageUtils.getMessage("comment.like.edit.deny.message"))
                    .build(),
                    HttpStatus.OK);
        }else if(commentLikesService.editProc(user, commentId)){
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("comment.like.edit.ok.code"))
                    .response(MessageUtils.getMessage("comment.like.edit.ok.message"))
                    .build(),
                    HttpStatus.OK);
        }else{
            return new ResponseEntity<>(Message.builder()
                    .code(MessageUtils.getMessage("comment.like.edit.fail.code"))
                    .response(MessageUtils.getMessage("comment.like.edit.fail.message"))
                    .build(),
                    HttpStatus.OK);
        }
    }

}
