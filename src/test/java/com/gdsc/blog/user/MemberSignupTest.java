package com.gdsc.blog.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberSignupTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSignup() throws Exception{
        String username = "test"; //set username
        String password = "test123"; //set password

        Member member = memberService.join(username, password); //sign up

        assertEquals(username, member.getUsername());
        //not use assertEquals to password
        assertTrue(passwordEncoder.matches(password, member.getPassword()));
    }
}
