package kr.or.rlog.mail;

import kr.or.rlog.account.AccountRepository;
import kr.or.rlog.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;

@Controller
public class MailController {
    @Autowired
    EntityManager entityManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MailRepository mailRepository;

    @Autowired
    MailService mailService;

    @Autowired
    AccountService accountService;

    @GetMapping("/api/v1/signup/mail")
    public String mailAuth(Model model, @RequestParam String secretKey, @RequestParam String email){
        if(accountService.doSignup(email, secretKey))
            model.addAttribute("message", "이메일 인증이 완료되었습니다. 로그인을 진행하세요");
        else
            model.addAttribute("message", "잘못된 접근입니다");
        return "login";

    }


}
