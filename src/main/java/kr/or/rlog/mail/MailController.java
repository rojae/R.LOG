package kr.or.rlog.mail;

import kr.or.rlog.account.AccountRepository;
import kr.or.rlog.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

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
    public String signup(Model model, @RequestParam String secretKey, @RequestParam String email){
        if(accountService.doSignup(email, secretKey))
            model.addAttribute("loginResult", "이메일 인증이 완료되었습니다. 로그인을 진행하세요");
        else
            model.addAttribute("loginResult", "잘못된 접근입니다");
        return "blog/login";
    }

    @GetMapping("/api/v1/update/mail")
    public String updateMail(Model model, @RequestParam String secretKey, @RequestParam String email) throws MessagingException {
        Mail savedMail = mailRepository.findByEmailAndSecretKeyAndExpireDateGreaterThan(email, secretKey, LocalDateTime.now());
        mailService.expireMail(mailService.createMail(savedMail.getAccount(), MailType.MAIL_EXPIRE), "신규 이메일 인증으로 인해서 해당 메일이 만료되었습니다.");

        if(accountService.mailUpdate(secretKey, email))
            model.addAttribute("loginResult", "신규 이메일 인증이 완료되었습니다. 로그인을 진행하세요");
        else
            model.addAttribute("loginResult", "잘못된 접근입니다");

        return "blog/login";
    }


}
