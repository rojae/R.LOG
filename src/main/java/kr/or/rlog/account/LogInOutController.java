package kr.or.rlog.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInOutController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutForm() {
        return "logout";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("main")
    public String main() {
        return "main";
    }

}