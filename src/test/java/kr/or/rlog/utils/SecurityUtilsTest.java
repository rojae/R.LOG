package kr.or.rlog.utils;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.WithAdmin;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
public class SecurityUtilsTest {

    /** =================================
     * Security getAccount Method Test
     =================================== **/
    @Test
    @WithAdmin
    @Transactional
    public void accountTest() {
        Account account = SecurityUtils.getAccount();
        log.info("CLASS TYPE : " + account.getClass());
        log.info("GET ACCOUNT NAME : " + account.getUserName());
    }


}