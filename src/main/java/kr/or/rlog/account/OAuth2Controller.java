package kr.or.rlog.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class OAuth2Controller {

    @GetMapping("/oauth/login")
    public String loginPage() {
        return "connect/oauth-login";
    }

    @GetMapping("/login/oauth2/code/kakao")
    public String callback_KAKAO(HttpServletResponse response){
        System.out.println(response.toString());
        return "redirect:/index";
    }

}
