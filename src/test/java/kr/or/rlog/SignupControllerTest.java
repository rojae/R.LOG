package kr.or.rlog;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SignupControllerTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;

    private String userName = "rojae";
    private String password = "rojae";
    private String email = "rojae@kakao.com";
    private String role = "USER";

    @Test
    public void signupTest() throws Exception {
        // When
        mockMvc.perform(post("/signup")
            .param("userName", userName)
            .param("password", password)
            .param("email", email)
            .param("role", role))
                .andDo(print())
                .andExpect(status().isOk());

        // Then
        Account user = accountRepository.findByEmailAndIsAuthIsTrue(userName);
        assertThat(user.getEmail()).isEqualTo(email);
    }

}
