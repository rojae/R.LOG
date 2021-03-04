package kr.or.rlog;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, @CurrentUser Account account) {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String welcome(Pageable pageable) {
        return "index";
    }

}
