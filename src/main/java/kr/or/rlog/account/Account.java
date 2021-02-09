package kr.or.rlog.account;

import kr.or.rlog.common.BaseTimeEntity;
import kr.or.rlog.mail.Mail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "TBL_ACCOUNT")
@Getter
@Setter
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "userName", nullable = false)
    private String userName;

    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'USER' ")
    private String role;

    @Column(name = "isAuth", columnDefinition = "TINYINT DEFAULT 0", length = 1)
    private boolean isAuth;

    @OneToMany(mappedBy = "account")
    private Set<Mail> mails = new HashSet<Mail>();

    public void addMail(Mail mail){
        this.mails.add(mail);
        mail.setAccount(this);
    }

    // 패스워드 암호화 기법
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

}