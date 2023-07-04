package com.gdsc.blog.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gdsc.blog.security.jwt.JwtTokenProvider;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.entity.UserRole;
import com.gdsc.blog.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;


	public String singin(String email, String password) {
		try {
			User user = userRepository.findByEmail(email).get();
			log.info("user: {}", user);
			log.info("email: {}", email);
			log.info("password: {}", password);
			if (!passwordEncoder.matches(password, user.getPassword())) {
				log.error("Invalid password");
				throw new RuntimeException("Invalid password");
			}
			return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

		} catch (Exception e) {
			log.info(e.getMessage());
			log.error("Invalid username/password supplied");
			throw new RuntimeException("Invalid username/password supplied");
		}
	}

	public User signup(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// Client 역할 부여
		if (user.getRoles() == null) {
			List<UserRole> set = new ArrayList<UserRole>();
			set.add(UserRole.ROLE_CLIENT);
			user.setRoles(set);
		}
		return userRepository.save(user);
	}

	public User getUser(String username) {
		return userRepository.findByEmail(username).get();
	}

	public User whoami(HttpServletRequest req) {
		log.info(jwtTokenProvider.resolveToken(req));
		return userRepository
				.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)))
				.get();
	}

}
