package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.category.Category;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.common.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "TBL_POST")
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Category category;

    @Column(name = "header", nullable = false)
    private String header;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Account writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<Comment> comments = new HashSet<Comment>();

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setPost(this);
    }

}
