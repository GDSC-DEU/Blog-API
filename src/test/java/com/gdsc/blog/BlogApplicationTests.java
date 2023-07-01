package com.gdsc.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.gdsc.blog.user.Member;
import com.gdsc.blog.user.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Profile("test")
class BlogApplicationTests {

    @Autowired
    private MemberService memberService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSignup() throws Exception {
        String username = "test"; //set username
        String password = "test123"; //set password

        Member member = memberService.join(username, password); //sign up

        assertEquals(username, member.getUsername());
        //not use assertEquals to password
        assertTrue(passwordEncoder.matches(password, member.getPassword()));
    }

    @Test
    public void testLogin() throws Exception{
        //normar member login
        String username1 = "test";
        String password1 = "test123";

        //worng member login
        String username2 = "test2";
        String password2 = "test1234";


        //test signup
        Member member = memberService.join(username1, password1);
        assertEquals(username1, member.getUsername());
        assertTrue(passwordEncoder.matches(password1, member.getPassword()));

        //test normal login
        try{
            Member getMember = memberService.login(username1, password2);
        } catch (IllegalArgumentException e) {
            assertEquals("Wrong password", e.getMessage());
        }

        //test wrong login
        try {
            Member getMember = memberService.login(username2, password2);
        } catch (IllegalArgumentException e) {
            assertEquals("No such user", e.getMessage());
        }
    }
}
