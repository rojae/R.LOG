package kr.or.rlog.mail;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static kr.or.rlog.mail.QMail.mail;

public class MailRepositorySupportImpl implements MailRepositorySupport{

    @Autowired
    JPAQueryFactory query;

    @Override
    public Mail findByEmailAndSecretKeyAndExpireDateGreaterThan(String email, String secretKey, LocalDateTime now) {
        return query.selectFrom(mail)
                .where(
                        mail.email.eq(email)
                        .and(mail.secretKey.eq(secretKey))
                        .and(mail.expireDate.after(now))
                ).fetchOne();
    }
}
