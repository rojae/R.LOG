package kr.or.rlog;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountRepository;
import kr.or.rlog.account.AccountService;
import kr.or.rlog.account.platform.PlatformType;
import kr.or.rlog.mail.Mail;
import kr.or.rlog.mail.MailService;
import kr.or.rlog.utils.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MailSenderTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MailService mailService;

    @Before
    public void testBefore(){
        System.out.println(createUser("rojae", "rojae"));
    }

    @Test
    public void sendMail() throws MessagingException {
        Account user = accountRepository.findByEmailAndIsAuthIsTrue("rojae@kakao.com");

        Mail mail = Mail.builder().userName(user.getUserName())
                .email(user.getEmail()).expireDate(LocalDateTime.now().plusMinutes(5))
                .secretKey(RandomUtils.getAlpha(64)).build();

        System.out.println("Generated Mail : " + mail);

        mailService.signupSend(mail);
    }

    public Account createUser(String userName, String password){
        Account account = new Account();
        account.setUserName(userName);
        account.setPassword(password);
        account.setEmail("rojae@kakao.com");
        account.setRole("USER");
        account.setAuth(true);

        return accountService.createNew(account, PlatformType.RLOG);
    }

}
