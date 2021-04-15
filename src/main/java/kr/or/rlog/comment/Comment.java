package kr.or.rlog.comment;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.TimeEntity;
import kr.or.rlog.common.Status;
import kr.or.rlog.post.Post;
import lombok.*;

import javax.persistence.*;

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
}
