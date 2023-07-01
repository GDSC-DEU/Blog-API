package com.gdsc.blog.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@Tag(name = "User", description = "The user API")
public class MemberController {

    //user service
    private final MemberService memberService;

    /**
     * Constructor service
     * @param memberService
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * Sign up
     * @param username username
     * @param password password
     * @return redirect signup-success
     */
    @PostMapping("/signup")
    public String signup(
        @RequestParam(name = "username") String username,
        @RequestParam(name = "password") String password
    ) {
        this.memberService.join(username, password);
        return "redirect:/signup-success";
    }

    /**
     * Sign up success page
     * @return string signup-success
     */
    @GetMapping("/signup-success")
    @ResponseBody
    public String signupSuccess() {
        return "signup-success";
    }

    /**
     * Login username and password
     * @param username username (id)
     * @param password password
     * @return login success or fail
     */
    @GetMapping("/login")
    @ResponseBody
    @Operation(summary = "Login", description = "Login")
    public String login(
        @Parameter(name = "username", description = "user id") String username,
        @Parameter(name = "password", description = "user password") String password
    ) {
        this.memberService.login(username, password);
        return "login-success";
    }
}
