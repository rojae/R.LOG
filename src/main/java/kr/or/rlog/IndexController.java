package kr.or.rlog;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import kr.or.rlog.post.PostDto;
import kr.or.rlog.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {

    private final static int pageSize = 7;
    private final static int blockSize = 5;
    private final static int popularSize = 3;

    @Autowired
    PostService postService;

    @GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    /*
     * Index 첫 페이지
     * Tiles 사용
     */
    @GetMapping("/index")
    public String welcome(Model model
            , @CurrentUser Account user
            , @RequestParam(value = "keyword", defaultValue = "") String keyword
            , @PageableDefault(page = 0, size = pageSize, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        List<PostDto> topPost = postService.getTopPost(popularSize);
        Page<PostDto> postPage = postService.getPage(pageable, keyword, user);

        int pageNumber = (postPage.getPageable().isPaged()) ? postPage.getPageable().getPageNumber() : 0;    //  현재페이지
        int totalPages = postPage.getTotalPages(); //총 페이지 수. 검색에따라 10개면 10개..
        int pageBlock = blockSize;  //블럭의 수 1, 2, 3, 4, 5
        int startBlockPage = ((pageNumber) / pageBlock) * pageBlock + 1; //현재 페이지가 7이라면 1*5+1=6
        int endBlockPage = startBlockPage + pageBlock - 1; //6+5-1=10. 6,7,8,9,10해서 10.
        endBlockPage = Math.min(totalPages, endBlockPage);

        model.addAttribute("startBlockPage", startBlockPage);
        model.addAttribute("endBlockPage", endBlockPage);
        model.addAttribute("postPage", postPage);
        model.addAttribute("topPost", topPost);
        model.addAttribute("keyword", keyword);
        return ".blog.nav-fixed/index";
    }


}
