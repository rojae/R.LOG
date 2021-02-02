package kr.or.rlog.mail;

import kr.or.rlog.account.Account;
import kr.or.rlog.account.AccountService;
import kr.or.rlog.common.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MailController {

    @Autowired
    MailService mailService;

    @Autowired
    AccountService accountService;

    @GetMapping("/mail-auth")
    public String mailForm(){
        return "mailAuth";
    }

    @PostMapping("/mail-auth")
    public String mailAuth(@CurrentUser Account account, @RequestBody String authKey){
        return "";
    }

}
