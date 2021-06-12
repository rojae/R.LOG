package kr.or.rlog.account;

import kr.or.rlog.account.platform.PlatformType;
import kr.or.rlog.mail.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositorySupport {
    //Account findByEmailAndIsAuthIsTrueAndPlatformType(String email, PlatformType platformType);
    Account findByEmailAndIsAuthIsTrue(String email);
    boolean existsAccountByEmailAndIsAuthIsTrue(String email);
    boolean existsAccountByEmail(String email);
    Account findAccountByEmail(String email);
    Account findByEmail(String email);
    Account findByMailsContains(Mail mail);
}
