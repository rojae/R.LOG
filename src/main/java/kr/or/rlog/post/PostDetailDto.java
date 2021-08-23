package kr.or.rlog.post;

import kr.or.rlog.account.AccountDto;
import kr.or.rlog.category.CategoryDto;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.common.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class PostDetailDto {
    private Long id;
    private CategoryDto category;
    private String thumbNail;
    private String header;
    private String title;
    private String content;
    private AccountDto writer;
    private Set<Comment> comments = new HashSet<Comment>();
    private Long likesCount;
    private boolean postLikes;
    private Status status;
    private String createdDate;
    private String modifiedDate;

    public PostDetailDto() {

    }

    public PostDetailDto(Long id, CategoryDto category, String thumbNail, String header, String title, String content, AccountDto writer, Long likesCount, boolean postLikes, Status status, String createdDate, String modifiedDate) {
        this.id = id;
        this.category = category;
        this.thumbNail = thumbNail;
        this.header = header;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.likesCount = likesCount;
        this.postLikes = postLikes;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
