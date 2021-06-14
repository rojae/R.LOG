package kr.or.rlog.mail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MailRepository extends JpaRepository<Mail, Long>, MailRepositorySupport{

}
