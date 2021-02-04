package kr.or.rlog.admin;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("message", "로그인을 성공하였습니다.");
        return "admin";
    }

}
