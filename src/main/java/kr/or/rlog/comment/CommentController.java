package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import kr.or.rlog.guestbook.Guestbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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
        CommentDto root = commentService.createRoot(postId, user);
        return new ResponseEntity<>(root, HttpStatus.OK);
    }

    @PostMapping("/comment/{commentId}")
    public String editView(Model model, @PathVariable Long commentId, @CurrentUser Account user){
        Optional<Comment> comment = commentService.getOne(commentId);
        if (!comment.isPresent()) {
            model.addAttribute("message", "존재하지 않거나 권한이 없는 요청입니다");
        } else {
            model.addAttribute("content", comment.get().getContent());
            model.addAttribute("commentId", comment.get().getId());
        }

        return ".empty/blog/comment-edit";
    }

    @PutMapping("/comment/{commentId}")
    @ResponseBody
    public ResponseEntity<Message> editProc(@RequestBody Comment comment, @PathVariable Long commentId, @CurrentUser Account user) {
        if (commentService.editProc(commentId, comment))
            return new ResponseEntity<Message>(Message.builder().response("수정되었습니다").code("200").build(), HttpStatus.OK);
        else
            return new ResponseEntity<Message>(Message.builder().response("존재하지 않거나 권한이 없는 요청입니다").code("500").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public ResponseEntity<Message> deleteProc(@PathVariable Long commentId, @CurrentUser Account user){
        if (commentService.deleteProc(commentId))
            return new ResponseEntity<Message>(Message.builder().response("삭제되었습니다").code("200").build(), HttpStatus.OK);
        else
            return new ResponseEntity<Message>(Message.builder().response("존재하지 않거나 권한이 없는 요청입니다").code("500").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
