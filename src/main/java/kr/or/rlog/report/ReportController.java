package kr.or.rlog.report;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ReportController {

    private final static int pageSize = 10;
    private final static int blockSize = 5;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping("/error")
    public String error() {
        return ".empty/blog/error";
    }

    @PostMapping("/error/report")
    public ResponseEntity<Message> reportSave(@CurrentUser Account user, @RequestBody Report report) {
        report.setWriter(user);
        report.setCheckStatus(CheckStatus.UNCHECK);
        reportRepository.save(report);
        return new ResponseEntity<Message>(Message.builder()
                .code(MessageUtils.getMessage("error.report.ok.code"))
                .response(MessageUtils.getMessage("error.report.ok.message")).build()
                , HttpStatus.OK);
    }

    @Secured("ADMIN")
    @GetMapping("/manage/error")
    public String errorView(Model model) {
        model.addAttribute("list", reportRepository.findAll());
        return ".empty/manage/blog-error-list";
    }

    @Secured("ADMIN")
    @GetMapping("/error/reports")
    @ResponseBody
    public ResponseEntity getReports(@PageableDefault(page = 0, size = pageSize, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Map<String, Object> message = new HashMap<>();

        Page<ReportListDto> reports = reportService.getReports(pageable);

        int pageNumber = (reports.getPageable().isPaged()) ? reports.getPageable().getPageNumber() : 0;    //  현재페이지
        int totalPages = reports.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
        int pageBlock = blockSize;  //블럭의 수 1, 2, 3, 4, 5
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1; //현재 페이지가 7이라면 1*5+1=6
        int endBlockPage = startBlockPage + pageBlock - 1; //6+5-1=10. 6,7,8,9,10해서 10.
        endBlockPage = Math.min(totalPages, endBlockPage);

        message.put("startBlockPage", startBlockPage);
        message.put("endBlockPage", endBlockPage);
        message.put("reports", reports);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Secured("ADMIN")
    @GetMapping("/error/report/{id}")
    @ResponseBody
    public ResponseEntity getReport(@PathVariable Long id) {
        ReportDto report = reportService.findOne(id);
        return (report == null) ? new ResponseEntity<>(null, HttpStatus.OK) : new ResponseEntity<>(report, HttpStatus.OK);
    }

    @Secured("ADMIN")
    @PutMapping("/error/report/{id}")
    @ResponseBody
    public ResponseEntity updateReport(@PathVariable Long id, @RequestBody Report newCheckStatus){
        if(reportService.updateCheckStatus(id, newCheckStatus.getCheckStatus()))
            return new ResponseEntity<Message>(Message.builder().response("정상적으로 변경되었습니다").code("200").build(), HttpStatus.OK);
        else
            return new ResponseEntity<Message>(Message.builder().response("시스템 오류입니다.").code("500").build(), HttpStatus.OK);
    }

}
