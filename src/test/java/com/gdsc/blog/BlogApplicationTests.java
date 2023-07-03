package com.gdsc.blog;

import com.gdsc.blog.user.Customer;
import com.gdsc.blog.user.userRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
class BlogApplicationTests {
	@Autowired
	userRepository userRepository;
	@Test
	void testJoin() {
		Customer user1 = new Customer();
		user1.setID("cj8556");
		user1.setPassword("test1234");
		this.userRepository.save(user1);
	}
}
