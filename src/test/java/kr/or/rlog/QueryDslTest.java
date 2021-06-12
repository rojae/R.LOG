package kr.or.rlog;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountRepository;
import kr.or.rlog.account.AccountService;
import kr.or.rlog.account.platform.PlatformType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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

    /*
     * QyeryDsl method test
     */
    @Test
    @Transactional
    public void accountTest() {
        // WEHN && GIVEN
        Account user1 = accountRepository.findByEmailAndIsAuthIsTrueAndPlatformType("skdltmwotjd@naver.com", PlatformType.RLOG);
        Account user2 = accountRepository.findByEmailAndIsAuthIsTrueAndPlatformType("nectiofsky@naver.com", PlatformType.RLOG);
        boolean res1 = accountRepository.existsAccountByEmailAndIsAuthIsTrue("skdltmwotjd@naver.com");
        boolean res2 = accountRepository.existsAccountByEmailAndIsAuthIsTrue("nectiofsky@naver.com");

        // TEST
        assertThat(user1).isNotNull();
        assertThat(user2).isNull();
        assertThat(res1).isTrue();
        assertThat(res2).isFalse();
    }

    /*
     * QueryDsl 1차 캐싱 update
     */
    @Test
    @Transactional
    public void queryDsl_cache(){
        // WEHN && GIVEN
        Account user1 = accountRepository.findByEmailAndIsAuthIsTrueAndPlatformType("skdltmwotjd@naver.com", PlatformType.RLOG);
        boolean res1 = accountRepository.existsAccountByEmailAndIsAuthIsTrue("skdltmwotjd@naver.com");

        // TEST
        assertThat(user1).isNotNull();
        assertThat(res1).isTrue();

        user1.setAuth(false);

        res1 = accountRepository.existsAccountByEmailAndIsAuthIsTrue("skdltmwotjd@naver.com");

        assertThat(res1).isFalse();
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
