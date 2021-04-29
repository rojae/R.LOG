package kr.or.rlog.account;

import kr.or.rlog.common.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInOutController {

    @GetMapping("/login")
    public String loginForm(@CurrentUser Account account) {
        if(account == null)
            return "blog/login";
        return "blog/index";
    }

    @GetMapping("/logout")
    public String logoutForm(Model model) {
        model.asMap().clear();
        model.addAttribute("loginResult", "로그아웃 되었습니다");
        return "blog/logout";
    }

    @GetMapping("main")
    public String main() {
        return "blog/main";
    }

}