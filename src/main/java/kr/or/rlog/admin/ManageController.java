package kr.or.rlog.admin;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import kr.or.rlog.visit.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ManageController {

    @Autowired
    VisitorService visitorService;

    @GetMapping("/gate/manage")
    @ResponseBody
    public ResponseEntity<Message> open(@CurrentUser Account user) {
        if (user == null)
            return new ResponseEntity<Message>(Message.builder().code("403").response("관리자만 사용 가능한 기능입니다. 로그인을 해야합니다.").build(), HttpStatus.OK);
        else if (user.getRole().contains("ADMIN"))
            return new ResponseEntity<Message>(Message.builder().code("200").response("관리자 페이지로 이동합니다").build(), HttpStatus.OK);
        else
            return new ResponseEntity<Message>(Message.builder().code("403").response("관리자만 사용 가능한 기능입니다").build(), HttpStatus.OK);
    }

    @GetMapping("/manage")
    @Secured("ADMIN")
    public String dashBoard(@CurrentUser Account user) {
        return ".empty/manage/dashboard";
    }

    @GetMapping("/manage/my/info")
    @Secured("ADMIN")
    public String myInfo(@CurrentUser Account user) {
        return ".empty/manage/my-info";
    }

    @GetMapping("/manage/posts")
    @Secured("ADMIN")
    public String myPosts(@CurrentUser Account user) {
        return ".empty/manage/posts";
    }

    @GetMapping("/manage/categories")
    @Secured("ADMIN")
    public String categories(@CurrentUser Account user) {
        return ".empty/manage/categories";
    }

    @GetMapping("/manage/statistic/today")
    @Secured("ADMIN")
    public ResponseEntity<Message> getToday() {
        Long cnt = visitorService.todayCount();
        return new ResponseEntity<Message>(Message.builder().code("200").response(String.valueOf(cnt)).build(), HttpStatus.OK);
    }

    @GetMapping("/manage/statistic/month")
    @Secured("ADMIN")
    public ResponseEntity<Message> getMonth() {
        Long cnt = visitorService.monthCount();
        return new ResponseEntity<Message>(Message.builder().code("200").response(String.valueOf(cnt)).build(), HttpStatus.OK);
    }

    @GetMapping("/manage/statistic/all")
    @Secured("ADMIN")
    public ResponseEntity<Message> getAll() {
        Long cnt = visitorService.allCount();
        return new ResponseEntity<Message>(Message.builder().code("200").response(String.valueOf(cnt)).build(), HttpStatus.OK);
    }
}
