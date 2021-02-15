package kr.or.rlog.post;

import kr.or.rlog.account.Account;
import kr.or.rlog.comment.Comment;
import kr.or.rlog.common.BaseTimeEntity;
import kr.or.rlog.mail.Mail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name = "TBL_POST")
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long Id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", name = "content", nullable = false)
    private String content;

    @ManyToOne
    private Account writer;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<Comment>();



}
