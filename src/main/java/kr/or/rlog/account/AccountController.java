package kr.or.rlog.account;

import kr.or.rlog.account.platform.PlatformType;
import kr.or.rlog.comment.CommentDto;
import kr.or.rlog.comment.CommentService;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import kr.or.rlog.mail.Mail;
import kr.or.rlog.mail.MailService;
import kr.or.rlog.mail.MailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.util.Map;

@Controller
public class AccountController {

    private final static int pageSize = 7;
    private final static int blockSize = 5;

    @Autowired
    AccountService accountService;

    @Autowired
    MailService mailService;

    @Autowired
    CommentService commentService;

    @GetMapping("/signup")
    public String signUp() {
        return "blog/page-blog-signup";
    }

    @PostMapping("/signup")
    public String createAccount(Model model, @ModelAttribute Account account) throws MessagingException {
        if (accountService.isDuplicate(account)) {
            model.addAttribute("loginResult", "이미 가입된 이메일입니다. 이메일 인증을 진행하세요.");
            if (accountService.isAuth(account))
                model.addAttribute("loginResult", "이미 가입된 이메일입니다. 로그인을 진행해주세요.");
            return "blog/login";
        }
        // 아래 두개는 합치는게 .. 좋으려나??
        Account savedUser = accountService.createNew(account, PlatformType.RLOG);
        Mail newMail = mailService.createMail(savedUser, MailType.USER_SIGNUP);
        mailService.signupSend(newMail);
        model.addAttribute("loginResult", "회원가입이 완료되었습니다. 이메일 인증을 진행하세요.");
        return "blog/login";
    }

    @GetMapping("/my/info")
    public ModelAndView myInfo(@CurrentUser Account user) {
        ModelAndView mav = new ModelAndView("blog/my-info");
        if (user == null)
            mav.addObject("message", "로그인 이후에 접근 가능한 페이지입니다.");
        else if (user.getPlatformType().equals(PlatformType.RLOG))
            mav.addObject("myInfo", accountService.getMyInfo(user.getId()));
        else
            mav.addObject("message", "소셜 사용자는 정보조회만 가능합니다.");
        return mav;
    }

    @PostMapping("/my/info")
    public ResponseEntity<Message> saveMyEdit(@CurrentUser Account user, @RequestBody AccountInfoDto accountInfoDto) {
        if (user == null) {
            Message message = Message.builder().code("403").response("로그인을 해야 댓글을 남길 수 있습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (user.getPlatformType().equals(PlatformType.RLOG)) {
            if (accountService.saveMyInfo(user.getId(), accountInfoDto)) {
                Message message = Message.builder().code("200").response("정상적으로 변경되었습니다.").build();
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {
                Message message = Message.builder().code("200").response("시스템 오류입니다.").build();
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
        } else {
            Message message = Message.builder().code("403").response("로그인을 해야 댓글을 남길 수 있습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/my/info/password")
    public String updatePasswordView() {
        return "blog/my-info-password";
    }

    @PutMapping("/my/info/password")
    public ResponseEntity<Message> updatePassword(@CurrentUser Account user, @RequestBody Map<String, Object> data) {
        String currentPwd = (String) data.get("currentPwd");
        String newPwd = (String) data.get("newPwd");

        if (user == null) {
            Message message = Message.builder().code("403").response("권한이 없는 요청입니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (!accountService.passwordCheck(currentPwd, user.getPassword())) {
            Message message = Message.builder().code("201").response("입력하신 현재 비밀번호가 틀립니다. 다시 시도하세요.").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (accountService.passwordUpdate(newPwd, user.getId())) {
            Message message = Message.builder().code("200").response("비밀번호가 수정되었습니다. 다시 로그인해주세요.").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            Message message = Message.builder().code("500").response("시스템 오류입니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/my/info/email")
    public String updateMailView() {
        return "blog/my-info-email";
    }

    @PostMapping("/my/info/email")
    public ResponseEntity<Message> updateMail(@CurrentUser Account user, @RequestBody Map<String, Object> data) {
        String currentPwd = (String) data.get("currentPwd");
        String newMail = (String) data.get("newMail");

        if (user == null) {
            Message message = Message.builder().code("403").response("권한이 없는 요청입니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else if (!accountService.passwordCheck(currentPwd, user.getPassword())) {
            Message message = Message.builder().code("201").response("입력하신 현재 비밀번호가 틀립니다. 다시 시도하세요.").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            try {
                if (!accountService.passwordCheck(currentPwd, user.getPassword())) {
                    Message message = Message.builder().code("201").response("입력하신 현재 비밀번호가 틀립니다. 다시 시도하세요.").build();
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }else if(accountService.isDuplicate(newMail)){
                    Message message = Message.builder().code("201").response("이미 사용 중인 이메일입니다.").build();
                    return new ResponseEntity<>(message, HttpStatus.OK);
                }else{
                    mailService.mailSend(mailService.updateMail(user, newMail), "신규 메일을 인증합니다");
                }
            } catch (MessagingException e) {
                Message message = Message.builder().code("200").response("죄송합니다. 시스템 오류가 발생했습니다.").build();
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
            Message message = Message.builder().code("200").response("해당 이메일로 인증메일을 보냈습니다. 인증 이후 로그인을 진행하세요.").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    @GetMapping("/my/info/comments")
    @ResponseBody
    public Page<CommentDto> myComments(@CurrentUser Account user, @PageableDefault(page = 0, size = pageSize, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        return commentService.getPage(pageable, user);
    }


}
