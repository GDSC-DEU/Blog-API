package com.gdsc.blog.user.controller;

import java.util.HashSet;
import java.util.Set;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.Operation;
import com.gdsc.blog.user.dto.SignInDto;
import com.gdsc.blog.user.dto.SignUpDto;
import com.gdsc.blog.user.dto.Token;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.entity.UserRole;

@Slf4j
@Tag(name = "User", description = "User API")
@RestController
@RequestMapping("/api/user")
public class UserController {

	UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

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

	@GetMapping("/getUser/{email}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User getUser(@PathVariable("email") String email) {
		return userService.getUser(email);
	}

	@GetMapping("/whoisme")
	public User whoisme(HttpServletRequest req) {
		return userService.whoami(req);
	}

}
