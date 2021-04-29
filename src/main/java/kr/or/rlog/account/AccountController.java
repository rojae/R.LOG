package kr.or.rlog.account;

import kr.or.rlog.account.platform.PlatformType;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import kr.or.rlog.mail.Mail;
import kr.or.rlog.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    MailService mailService;

    @GetMapping("/signup")
    public String signUp(){
        return "blog/page-blog-signup";
    }

    @PostMapping("/signup")
    public String createAccount(Model model, @ModelAttribute Account account) throws MessagingException {
        if(accountService.isDuplicate(account)) {
            model.addAttribute("loginResult", "이미 가입된 이메일입니다. 이메일 인증을 진행하세요.");
            if(accountService.isAuth(account))
                model.addAttribute("loginResult", "이미 가입된 이메일입니다. 로그인을 진행해주세요.");
            return "blog/login";
        }
        // 아래 두개는 합치는게 .. 좋으려나??
        Account savedUser = accountService.createNew(account, PlatformType.RLOG);
        Mail newMail = mailService.createMail(savedUser);
        mailService.mailSend(newMail);
        model.addAttribute("loginResult", "회원가입이 완료되었습니다. 이메일 인증을 진행하세요.");
        return "blog/login";
    }

    @GetMapping("/my/info")
    public ModelAndView myInfo(@CurrentUser Account user){
        ModelAndView mav = new ModelAndView("blog/my-info");
        if(user == null)
            mav.addObject("message", "로그인 이후에 접근 가능한 페이지입니다.");
        else if(user.getPlatformType().equals(PlatformType.RLOG))
            mav.addObject("myInfo", accountService.getMyInfo(user.getId()));
        else
            mav.addObject("message", "소셜 사용자는 정보조회만 가능합니다.");
        return mav;
    }

    @PostMapping("/my/info")
    public ResponseEntity<Message> saveMyEdit(@CurrentUser Account user, @RequestBody AccountInfoDto accountInfoDto){
        if(user == null) {
            Message message = Message.builder().code("403").response("로그인을 해야 댓글을 남길 수 있습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else if(user.getPlatformType().equals(PlatformType.RLOG)){
            if(accountService.saveMyInfo(user.getId(), accountInfoDto)){
                Message message = Message.builder().code("200").response("정상적으로 변경되었습니다.").build();
                return new ResponseEntity<>(message, HttpStatus.OK);
            }else{
                Message message = Message.builder().code("200").response("시스템 오류입니다.").build();
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
        }else{
            Message message = Message.builder().code("403").response("로그인을 해야 댓글을 남길 수 있습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }


}
