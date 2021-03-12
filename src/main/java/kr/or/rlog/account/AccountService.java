package kr.or.rlog.account;

import kr.or.rlog.mail.Mail;
import kr.or.rlog.mail.MailRepository;
import kr.or.rlog.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MailRepository mailRepository;

    @Autowired
    MailService mailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmailAndIsAuthIsTrue(email);
        if (account == null && accountRepository.findByEmail(email) == null) {
            // not sign-up
            throw new UsernameNotFoundException("not sign-up");
        }else if (account == null){
            // should be mail authentication
            throw new UsernameNotFoundException("mail Authentication");
        }

        System.out.println(account.getUserName() + "님이 로그인 성공하셨습니다.");
        return new UserAccount(account);
    }

    /*
     * 메일에 해당하는 계정이 있으면 (외래키 매핑)
     * JPA 영속성 상태를 사용하여 update 쿼리를 날려준다
     *  --> 추후, 2차 캐싱기법을 사용하여 고도화가 가능하다.
     */
    @Transactional
    public boolean doSignup(String email, String secretKey) {
        Mail mail = mailRepository.findByEmailAndSecretKeyAndExpireDateGreaterThan(email, secretKey, LocalDateTime.now());
        if(mail == null)
            return false;

        Account account = accountRepository.findByMailsContains(mail);
        if(account == null)
            return false;

        mail.setAuth(true);
        account.setAuth(true);
        return true;
    }

    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        return this.accountRepository.save(account);
    }

    public boolean isDuplicate(Account account){
        return this.accountRepository.existsAccountByEmail(account.getEmail());
    }

    public boolean isAuth(Account account){
        return this.accountRepository.existsAccountByEmailAndIsAuthIsTrue(account.getEmail());
    }

}
