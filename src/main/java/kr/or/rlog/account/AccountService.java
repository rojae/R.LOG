package kr.or.rlog.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

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

    public Account createNew(Account account) {
        account.encodePassword(passwordEncoder);
        return this.accountRepository.save(account);
    }

}
