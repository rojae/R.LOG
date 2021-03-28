package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;


    @PostMapping("/comments/{postId}")
    @ResponseBody
    public ResponseEntity<Message> createRoot(@PathVariable Long postId, @CurrentUser Account user, @RequestBody Comment comment){
        if(user == null) {
            Message message = Message.builder().code("403").response("로그인을 해야 댓글을 남길 수 있습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if(commentService.createNew(comment, user, postId)){
            Message message = Message.builder().code("200").response("작성되었습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else{
            Message message = Message.builder().code("999").response("시스템 오류입니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @PostMapping("/comments/{postId}/{parentId}")
    @ResponseBody
    public ResponseEntity<Message> createChild(@PathVariable Long postId, @PathVariable Long parentId, @CurrentUser Account user, @RequestBody Comment comment){
        if(user == null) {
            Message message = Message.builder().code("403").response("로그인을 해야 댓글을 남길 수 있습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if(commentService.createNew(comment, user, postId, parentId)) {
            Message message = Message.builder().code("200").response("작성되었습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else {
            Message message = Message.builder().code("999").response("시스템 오류입니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/comments/{postId}")
    @ResponseBody
    public ResponseEntity<CommentDto> getComments(@PathVariable Long postId, @CurrentUser Account user){
        CommentDto root = commentService.createRoot(postId);
        return new ResponseEntity<>(root, HttpStatus.OK);
    }


}
