package kr.or.rlog;

import kr.or.rlog.account.Account;
import kr.or.rlog.common.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index(Model model, @CurrentUser Account account){
        if(account == null)
            return "comeSoon";
        else
            model.addAttribute("message", "반갑습니다" + account.getUserName() + "님");
        return "main";
    }

}
