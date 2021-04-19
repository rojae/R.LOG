package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.category.Category;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.common.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PostDetailDto {
    private Long id;
    private Category category;
    private String thumbNail;
    private String header;
    private String title;
    private String content;
    private Account writer;
    private Set<Comment> comments = new HashSet<Comment>();
    private boolean postLikes;
    private Status status;
    private String createdDate;
    private String modifiedDate;

    public PostDetailDto() {

    }

    public PostDetailDto(Long id, Category category, String thumbNail,
                         String header, String title, String content,
                         Account writer, Set<Comment> comments, boolean postLikes,
                         Status status, String createdDate, String modifiedDate) {
        this.id = id;
        this.category = category;
        this.thumbNail = thumbNail;
        this.header = header;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.comments = comments;
        this.postLikes = postLikes;
        this.status = status;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
