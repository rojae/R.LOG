package kr.or.rlog.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmailAndIsAuthIsTrue(String email);
    Account findByEmail(String email);
}
