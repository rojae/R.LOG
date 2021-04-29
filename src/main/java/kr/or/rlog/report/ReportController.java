package kr.or.rlog.report;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
import kr.or.rlog.common.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/error")
    public String error() {
        return "/blog/error";
    }
    @PostMapping("/error/report")
    public ResponseEntity<Message> reportSave(@CurrentUser Account user, @RequestBody Report report) {
        report.setWriter(user);
        reportRepository.save(report);
        return new ResponseEntity<Message>(Message.builder()
                .code(MessageUtils.getMessage("error.report.ok.code"))
                .response(MessageUtils.getMessage("error.report.ok.message")).build()
                , HttpStatus.OK);
    }

}
