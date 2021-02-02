package kr.or.rlog.mail;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "TBL_MAIL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Mail {

    @Id
    @GeneratedValue
    private Integer id;

    // 돔명이인은 존재할 수 있다
    @Column(name = "userName", nullable = false)
    private String userName;

    // 이메일은 같을 수 없다
    @Column(name = "email", nullable = false)
    private String email;

    // 랜던 인증키
    @Column(name = "authKey", nullable = false)
    private String authKey;

    // 보낸 날짜
    @Column(name = "sentDate", nullable = false)
    @CreatedDate
    private LocalDateTime sentDate;

    // 만료시간
    @Column(name = "expireDate", nullable = false)
    private LocalDateTime expireDate;

    // 이메일 인증 여부
    @Column(name = "isAuth", nullable = true, columnDefinition = "TINYINT DEFAULT 0", length = 1)
    private boolean auth;

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", authKey='" + authKey + '\'' +
                ", sentDate=" + sentDate +
                ", expireDate=" + expireDate +
                ", isAuth=" + auth +
                '}';
    }
}

