package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    @ResponseBody
    public String home(){
        return "안녕하세요 sbb에 오신 것을 환영합니다.";
    }

    @GetMapping("/about")
    @ResponseBody
    public String about(){
        return "about page";
    }
}
