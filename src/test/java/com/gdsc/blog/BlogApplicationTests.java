package com.gdsc.blog;

import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Profile("test")
class BlogApplicationTests {
	@Autowired
	UserRepository userRepository;
	@Test
	void testJoin() { //회원가입 테스트

	}

	@Test
	void testGetInformation() {

	}
}
