package kr.or.rlog.likey;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.Status;
import kr.or.rlog.common.TimeEntity;
import kr.or.rlog.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "TBL_POST_LIKEY")
@Setter
@Getter
public class PostLike extends TimeEntity {

    public PostLike(Account account){
        this.account = account;
        this.status = Status.ENABLE;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="like_post_seq")
    @SequenceGenerator(name = "like_post_seq", sequenceName = "like_post_seq", initialValue = 1, allocationSize=1)
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Post post;

    // ENABLE || UNABLE
    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ENABLE' ")
    @Enumerated(EnumType.STRING)
    private Status status;

}
