package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.category.Category;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.common.TimeEntity;
import kr.or.rlog.common.Status;
import kr.or.rlog.likey.PostLike;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TBL_POST")
public class Post extends TimeEntity {

    public Post(Long id){
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "post_seq", initialValue = 60, allocationSize=1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Category category;

    @Column(name = "thumbNail", nullable = true)
    private String thumbNail;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<PostLike> postLikes = new HashSet<PostLike>();

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ENABLE' ")
    @Enumerated(EnumType.STRING)
    private Status status;

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setPost(this);
    }

    public void addPostLike(PostLike postLike){
        this.postLikes.add(postLike);
        postLike.setPost(this);
    }

}
