package com.gdsc.blog.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class MemberController {

    @GetMapping("/login")
    @ResponseBody
    public String loginUI(){
        return "login 접근 성공";
    }

}
