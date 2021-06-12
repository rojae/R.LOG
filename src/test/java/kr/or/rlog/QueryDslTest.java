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

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QueryDslTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;



    @Before
    public void testBefore() {
        createUser("rojae", "rojae", "skdltmwotjd@naver.com", true);
        createUser("rojae", "rojae", "nectiofsky@naver.com", false);
    }


    @Test
    public void accountTest() {
        // WEHN && GIVEN
        Account user1 = accountRepository.findByEmailAndIsAuthIsTrueAndPlatformType("skdltmwotjd@naver.com", PlatformType.RLOG);
        Account user2 = accountRepository.findByEmailAndIsAuthIsTrueAndPlatformType("nectiofsky@naver.com", PlatformType.RLOG);

        // TEST
        assertThat(user1).isNotNull();
        assertThat(user2).isNull();

    }


    public Account createUser(String userName, String password, String email, boolean auth) {
        Account account = new Account();
        account.setUserName(userName);
        account.setPassword(password);
        account.setEmail(email);
        account.setRole("USER");
        account.setAuth(auth);

        return accountService.createNew(account, PlatformType.RLOG);
    }


}
