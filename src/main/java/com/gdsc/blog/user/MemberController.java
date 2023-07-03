package com.gdsc.blog.user;

import com.gdsc.blog.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/login")
    @ResponseBody
    public String loginUI(){
        return "login 접근 성공";
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Member member){
        return ResponseEntity.ok(memberService.register(member));
    }
}
