package com.gdsc.blog.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Tag(name = "User service", description = "User service API")
public class MemberService {
    private final MemberRepository memberRepository; //get Repository
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Create user service", description = "Create user service")
    public Member join(String username, String password) {
        Member member = new Member(); //create new user

        member.setUsername(username); //set username
        member.setPassword(passwordEncoder.encode(password)); //set password

        this.memberRepository.save(member); //save user to database

        return member;
    }
}
