package kr.or.rlog.post;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    @GetMapping("/write")
    public String write(){
        return "write";
    }

}
