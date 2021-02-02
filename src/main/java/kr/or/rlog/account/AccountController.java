package kr.or.rlog.account;

import kr.or.rlog.mail.Mail;
import kr.or.rlog.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    MailService mailService;

    @GetMapping("/signup")
    public String signUp(){
        return "signup";
    }

    @PostMapping("/signup")
    public String createAccount(Model model, @ModelAttribute Account account) throws MessagingException {
        accountService.createNew(account);
        Mail newMail = mailService.createMail(account);
        mailService.mailSend(newMail);
        model.addAttribute("message", "회원가입이 완료되었습니다. 이메일 인증을 진행하세요.");
        return "login";
    }

}
