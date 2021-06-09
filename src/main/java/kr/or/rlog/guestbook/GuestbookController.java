package kr.or.rlog.guestbook;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.common.Message;
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



/*
    R.LOG -> 방명록 기능
 */
@Controller
public class GuestbookController {

    private final static int pageSize = 7;
    private final static int blockSize = 5;

    @Autowired
    private GuestbookService guestbookService;

    @PostMapping("/guestbook")
    @ResponseBody
    public ResponseEntity<Message> create(@CurrentUser Account user, @RequestBody Guestbook guestbook) {
        if (user == null) {
            Message message = Message.builder().code("403").response("로그인을 해야 방명록을 남길 수 있습니다.<br/>로그인 페이지로 이동합니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        if (guestbookService.createNew(user, guestbook)) {
            Message message = Message.builder().code("200").response("작성되었습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            Message message = Message.builder().code("999").response("시스템 오류가 발생했습니다").build();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /*
     * 방명록 페이지
     * Tiles 사용
     */
    @GetMapping("/guestbook")
    public String getGuestbooks(Model model, @CurrentUser Account user, @PageableDefault(page = 0, size = pageSize, sort = "createdDate", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Page<GuestbookDto> guestbookPage = guestbookService.getPage(pageable, user);
        int pageNumber = (guestbookPage.getPageable().isPaged()) ? guestbookPage.getPageable().getPageNumber() : 0;    //  현재페이지
        int totalPages = guestbookPage.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
        int pageBlock = blockSize;  //블럭의 수 1, 2, 3, 4, 5
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1; //현재 페이지가 7이라면 1*5+1=6
        int endBlockPage = startBlockPage + pageBlock - 1; //6+5-1=10. 6,7,8,9,10해서 10.
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("guestbookPage", guestbookPage);

        return ".blog.nav/guestbook";
    }

    @PostMapping("/guestbook/{bookId}")
    public String editView(Model model, @PathVariable Long bookId, @CurrentUser Account user) {
        Guestbook guestbook = guestbookService.getOneIfMine(bookId, user);
        if (guestbook == null) {
            model.addAttribute("message", "존재하지 않거나 권한이 없는 요청입니다");
        } else {
            model.addAttribute("content", guestbook.getContent());
        }
        return "blog/guestbook-edit";
    }

    @PutMapping("/guestbook/{bookId}")
    @ResponseBody
    public ResponseEntity<Message> editProc(@RequestBody Guestbook guestbook, @PathVariable Long bookId, @CurrentUser Account user) {
        if (guestbookService.editProc(bookId, guestbook))
            return new ResponseEntity<Message>(Message.builder().response("수정되었습니다").code("200").build(), HttpStatus.OK);
        else
            return new ResponseEntity<Message>(Message.builder().response("존재하지 않거나 권한이 없는 요청입니다").code("500").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/guestbook/{bookId}")
    @ResponseBody
    public ResponseEntity<Message> deleteProc(@PathVariable Long bookId, @CurrentUser Account user){
        if (guestbookService.deleteProc(bookId))
            return new ResponseEntity<Message>(Message.builder().response("삭제되었습니다").code("200").build(), HttpStatus.OK);
        else
            return new ResponseEntity<Message>(Message.builder().response("존재하지 않거나 권한이 없는 요청입니다").code("500").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
