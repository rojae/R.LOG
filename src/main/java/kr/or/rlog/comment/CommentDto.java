package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountDto;
import kr.or.rlog.common.Status;
import kr.or.rlog.utils.TimeUtils;
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
    private String modifiedDate;
    private List<CommentDto> subComments;
    private Status status;

    public CommentDto(Long commentId, String content, AccountDto writer, LocalDateTime modifiedDate, Long parentId){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = TimeUtils.dateTimeToYYYYMMDD(modifiedDate);
        this.parentId = parentId;
    }

    public CommentDto(Long commentId, String content, AccountDto writer, LocalDateTime modifiedDate, Long parentId, Status status){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = TimeUtils.dateTimeToYYYYMMDD(modifiedDate);
        this.parentId = parentId;
        this.status = status;
    }

    public CommentDto(Long commentId, String content, AccountDto writer, Account user, LocalDateTime modifiedDate, Long parentId, Status status){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = TimeUtils.dateTimeToYYYYMMDD(modifiedDate);
        this.parentId = parentId;
        this.setMine(checkMine(user));
        this.status = status;
    }

    public boolean checkMine(Account user){
        if(user == null)
            return false;
        else
            return (this.writer.getId().equals(user.getId()));
    }


}
