package kr.or.rlog.mail;

import java.time.LocalDateTime;

public interface MailRepositorySupport {
    Mail findByEmailAndSecretKeyAndExpireDateGreaterThan(String email, String secretKey, LocalDateTime now);

}
