package kr.or.rlog;

import kr.or.rlog.account.Account;
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
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LoginControllerTest {

    private String userName = "rojae";
    private String password = "rojae";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Before
    public void signUp(){
        System.out.println(createAdmin(userName, password));
    }

    @Test
    public void loginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(post("/login")
                .param("userName", userName)
                .param("password", password))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    public Account createAdmin(String userName, String password){
        Account account = new Account();
        account.setUserName(userName);
        account.setPassword(password);
        account.setEmail("rojae@kakao.com");
        account.setRole("ADMIN");
        return accountService.createNew(account, PlatformType.RLOG);
    }

    public Account createUser(String userName, String password){
        Account account = new Account();
        account.setUserName(userName);
        account.setPassword(password);
        account.setEmail("rojae@kakao.com");
        account.setRole("USER");
        return accountService.createNew(account, PlatformType.RLOG);
    }

}
