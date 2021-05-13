package kr.or.rlog.account;

import kr.or.rlog.account.platform.PlatformType;
import kr.or.rlog.mail.Mail;
import kr.or.rlog.mail.MailRepository;
import kr.or.rlog.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

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
        Account account = accountRepository.findByEmailAndIsAuthIsTrueAndPlatformType(email, PlatformType.RLOG);
        if (account != null) {
            System.out.println(account.getUserName() + "님이 로그인 시도.");
            return new UserAccount(account);
        } else if (accountRepository.findByEmailAndIsAuthIsTrueAndPlatformType(email, PlatformType.KAKAO) != null) {
            System.out.println("카카오톡 회원입니다. 카카오톡을 통해서 로그인을 해야합니다.");
            throw new AccountExpiredException("카카오톡 회원입니다. 카카오톡을 통해서 로그인을 해야합니다.");
        } else if (accountRepository.findByEmail(email) != null) {
            System.out.println("메일 인증이 되지 않은 사용자입니다");
            throw new DisabledException("메일 인증이 되지 않은 사용자입니다");
        } else {
            System.out.println("존재하지 않는 사용자입니다");
            throw new BadCredentialsException("존재하지 않는 사용자 혹은 비밀번호입니다");
        }
    }

    public UserDetails loadUserByUsername(String email, PlatformType platformType) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmailAndIsAuthIsTrueAndPlatformType(email, platformType);
        if (account != null) {
            System.out.println("[" + platformType.name() + "]  - " + account.getUserName() + "님이 로그인 성공하셨습니다.");
            return new UserAccount(account);
        } else {
            System.out.println("존재하지 않는 사용자입니다");
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다");
        }
    }

    /*
     * 메일에 해당하는 계정이 있으면 (외래키 매핑)
     * JPA 영속성 상태를 사용하여 update 쿼리를 날려준다
     *  --> 추후, 2차 캐싱기법을 사용하여 고도화가 가능하다.
     */
    @Transactional
    public boolean doSignup(String email, String secretKey) {
        Mail mail = mailRepository.findByEmailAndSecretKeyAndExpireDateGreaterThan(email, secretKey, LocalDateTime.now());
        if (mail == null)
            return false;

        Account account = accountRepository.findByMailsContains(mail);
        if (account == null)
            return false;

        mail.setAuth(true);
        account.setAuth(true);
        return true;
    }

    public AccountInfoDto getMyInfo(Long accountId) {
        Optional<Account> user = accountRepository.findById(accountId);
        return user.map(AccountInfoDto::new).orElse(null);
    }

    @Transactional
    public boolean saveMyInfo(Long currentUserId, AccountInfoDto accountInfoDto){
        Optional<Account> user = accountRepository.findById(currentUserId);
        if(user.isPresent()){
            user.get().setProfileImage(accountInfoDto.getProfileImage());
            user.get().setEmail(accountInfoDto.getEmail());
            user.get().setUserName(accountInfoDto.getUserName());
            user.get().setRecvMail(accountInfoDto.isRecvMail());
            return true;
        }
        return false;
    }

    public Account createNew(Account account, PlatformType platformType) {
        account.setPlatformType(platformType);
        account.encodePassword(passwordEncoder);
        account.setProfileImage("https://rlog.or.kr/assets/img/illustrations/profiles/profile-2.png");
        return this.accountRepository.save(account);
    }

    public boolean isDuplicate(Account account) {
        return this.accountRepository.existsAccountByEmail(account.getEmail());
    }

    public boolean isDuplicate(String email) {
        return this.accountRepository.existsAccountByEmail(email);
    }

    public boolean isAuth(Account account) {
        return this.accountRepository.existsAccountByEmailAndIsAuthIsTrue(account.getEmail());
    }

    public boolean passwordCheck(String currentPwd, String password) {
        return passwordEncoder.matches(currentPwd, password);
    }

    @Transactional
    public boolean passwordUpdate(String newPwd, Long userId) {
        Optional<Account> savedAccount = accountRepository.findById(userId);
        savedAccount.ifPresent(account -> account.setPassword(passwordEncoder.encode(newPwd)));
        return true;
    }

    @Transactional
    public boolean mailUpdate(String secretKey, String newEmail) {
        Mail mail = mailRepository.findByEmailAndSecretKeyAndExpireDateGreaterThan(newEmail, secretKey, LocalDateTime.now());
        if (mail == null)
            return false;

        Account account = mail.getAccount();
        if (account == null)
            return false;

        mail.setAuth(true);
        account.setEmail(newEmail);
        return true;
    }

    @Transactional
    public Account findMe(Account user) {
        return accountRepository.findById(user.getId())
                .orElseThrow(RuntimeException::new);
    }
}
