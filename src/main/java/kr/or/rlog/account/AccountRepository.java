package kr.or.rlog.account;

import kr.or.rlog.mail.Mail;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmailAndIsAuthIsTrue(String email);
    boolean existsAccountByEmailAndIsAuthIsTrue(String email);
    boolean existsAccountByEmail(String email);
    Account findByEmail(String email);
    Account findByMailsContains(Mail mail);
    Account findByEmailAndPlatformType(String email, String platformType);
}
