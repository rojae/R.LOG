package kr.or.rlog.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }

    @PostMapping("/signup")
    public String createAccount(@ModelAttribute Account account){
        accountService.createNew(account);
        return "main";
    }

}
