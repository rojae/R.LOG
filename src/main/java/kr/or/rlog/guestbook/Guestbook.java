package kr.or.rlog.guestbook;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.platform.PlatformType;
import kr.or.rlog.common.BaseTimeEntity;
import kr.or.rlog.common.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "TBL_GUESTBOOK")
public class Guestbook extends BaseTimeEntity {

    public Guestbook(){

    }

    public Guestbook(Account writer, String content, Status status){
        this.writer = writer;
        this.content = content;
        this.status = status;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'ENABLE' ")
    @Enumerated(EnumType.STRING)
    private Status status;

}
