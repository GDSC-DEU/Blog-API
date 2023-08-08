package com.gdsc.blog.user.controller;


import com.gdsc.blog.security.jwt.JwtTokenProvider;
import com.gdsc.blog.user.dto.SignInDto;
import com.gdsc.blog.user.dto.SignUpDto;
import com.gdsc.blog.user.dto.Token;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    @PostMapping("/signup")
    @Operation(summary = "회원가입")
    public User signup(@RequestBody SignUpDto user) {

        User user1 = User.builder().email(user.getEmail()).password(user.getPassword())
                .username(user.getUsername()).build();


        return userService.signup(user1);
    }

    @PostMapping("/signin")
    @Operation(summary = "로그인")
    public Token signin(@RequestBody SignInDto signInDto) {
        Token token = Token.builder()
                .token(userService.singin(signInDto.getEmail(), signInDto.getPassword())).build();
        return token;

    }

    @PostMapping("/becomeAdmin")
    public String addRole(
            @Parameter(hidden = true) HttpServletRequest req
    ) {
        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        userService.becomeAdmin(username);
        return "success";
    }

    @GetMapping("/getUser/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getUser(@PathVariable("email") String email) {
        return userService.getUser(email);
    }

    @GetMapping("/whoisme")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public User whoisme(HttpServletRequest req) {
        return userService.whoami(req);
    }

}
