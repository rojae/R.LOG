package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.BaseTimeEntity;
import kr.or.rlog.post.Post;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "TBL_COMMENT")
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long Id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private Account writer;

    @ManyToOne
    private Post post;

}
