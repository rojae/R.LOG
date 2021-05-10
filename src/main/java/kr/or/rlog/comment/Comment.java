package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.TimeEntity;
import kr.or.rlog.common.Status;
import kr.or.rlog.likey.CommentLikes;
import kr.or.rlog.likey.PostLikes;
import kr.or.rlog.post.Post;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity(name = "TBL_COMMENT")
public class Comment extends TimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @ManyToOne
    private Post post;

    @Column(columnDefinition = "bigint(20) default '0'", nullable = false)
    private Long parentId;

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ENABLE' ")
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private Set<CommentLikes> commentLikes = new HashSet<CommentLikes>();

    public void addCommentLike(CommentLikes commentLikes){
        this.commentLikes.add(commentLikes);
        commentLikes.setComment(this);
    }

}
