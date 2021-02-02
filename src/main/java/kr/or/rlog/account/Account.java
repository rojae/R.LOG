package kr.or.rlog.account;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity(name = "TBL_ACCOUNT")
@Getter
@Setter
public class Account {
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

    // 패스워드 암호화 기법
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

}