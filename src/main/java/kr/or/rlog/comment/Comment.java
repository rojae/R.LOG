package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.BaseTimeEntity;
import kr.or.rlog.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "TBL_COMMENT")
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @ManyToOne
    private Post post;

    @Column
    private Long parentId;

}
