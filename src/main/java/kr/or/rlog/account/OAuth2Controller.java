package kr.or.rlog.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class OAuth2Controller {

    @GetMapping("/oauth/login")
    public String loginPage() {
        return "connect/oauth-login";
    }

    @GetMapping("/oauth/success")
    public String loginSuccess(@CookieValue(value = "greeting", defaultValue = "사용자") String name, @CookieValue(value = "mail", defaultValue = "???") String email, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + name + "님, 계정이 등록 되었습니다!      이메일 : " + email + "'); </script>");
        out.flush();

        return "blog";
    }

}
