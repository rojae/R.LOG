package kr.or.rlog.mail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long> {

    Mail findByEmailAndAuthKeyIsFalse(String email);

    Mail findByEmailAndAuthKeyIsTrue(String email);

}
