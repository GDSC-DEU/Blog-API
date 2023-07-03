package com.gdsc.blog.user;

import com.gdsc.blog.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@Controller
public class MemberController {

    private MemberService memberService;
    private JwtTokenProvider tokenProvider;
    public MemberController(MemberService memberService, JwtTokenProvider tokenProvider) {
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/login_ok")
    @ResponseBody
    public String login_ok(){
        return "로그인 성공";
    }
    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String ID, String pwd){
        memberService.login(ID, pwd);
        return "redirect:/user/login_ok";
    }
    @PostMapping("/register")
    public String register(@RequestBody Member member){
        ResponseEntity.ok(memberService.register(member));
        return "redirect:/user/login";
    }
}
