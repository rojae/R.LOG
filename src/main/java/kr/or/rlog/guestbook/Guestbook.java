package kr.or.rlog.guestbook;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "TBL_GUESTBOOK")
public class Guestbook extends BaseTimeEntity {

    public Guestbook(){

    }

    public Guestbook(Account writer, String content){
        this.writer = writer;
        this.content = content;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account writer;

}
