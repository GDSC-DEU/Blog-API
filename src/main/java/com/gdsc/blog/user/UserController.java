package com.gdsc.blog.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@Tag(name = "User", description = "The user API")
public class UserController {

    @GetMapping("/login")
    @ResponseBody
    @Operation(summary = "Login", description = "Login")
    public String login(
        String id,
        String password
    ) {
        return "Hello, login!";
    }
}
