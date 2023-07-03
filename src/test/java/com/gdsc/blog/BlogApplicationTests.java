package com.gdsc.blog;

import com.gdsc.blog.user.Member;
import com.gdsc.blog.user.MemberRepository;
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
	MemberRepository userRepository;
	@Test
	void testJoin() { //회원가입 테스트
		Member user1 = new Member();
		user1.setID("cj8556");
		user1.setPassword("test1234");
		this.userRepository.save(user1);

		Optional<Member> oq = this.userRepository.findById("cj8556");
		if(oq.isPresent()) {
			Member q = oq.get();
			assertEquals("cj8556", q.getID());
			assertEquals("test1234",q.getPassword());
		}
	}

	@Test
	void testGetInformation() {

	}
}
