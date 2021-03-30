package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private String content;
    private AccountDto writer;
    private boolean mine;
    private Long parentId;
    private LocalDateTime modifiedDate;
    private List<CommentDto> subComments;

    public CommentDto(Long commentId, String content, AccountDto writer, LocalDateTime modifiedDate, Long parentId){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = modifiedDate;
        this.parentId = parentId;
    }

    public CommentDto(Long commentId, String content, AccountDto writer, Account user, LocalDateTime modifiedDate, Long parentId){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = modifiedDate;
        this.parentId = parentId;
        this.setMine(checkMine(user));
    }

    public boolean checkMine(Account user){
        if(user == null)
            return false;
        else
            return (this.writer.getId().equals(user.getId()));
    }


}
