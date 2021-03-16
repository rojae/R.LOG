package kr.or.rlog.comment;

import kr.or.rlog.account.AccountDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private String content;
    private AccountDto writer;
    private Long parentId;
    private List<CommentDto> subCategories;

    public CommentDto(Long commentId, String content, AccountDto writer, Long parentId){
        this.commentId = commentId;
        this.content = content;
        this.writer = writer;
        this.parentId = parentId;
    }

}
