package com.gdsc.blog.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Tag(name = "User service", description = "User service API")
public class MemberService {
    //
    private final MemberRepository memberRepository; //get Repository
    private final PasswordEncoder passwordEncoder;

    /**
     * sign up
     * @param username username(id)
     * @param password password
     * @return Member
     */
    @Operation(summary = "Create user service", description = "Create user service")
    public Member join(String username, String password) {
        Member member = new Member(); //create new user

        member.setUsername(username); //set username
        member.setPassword(passwordEncoder.encode(password)); //set password

        this.memberRepository.save(member); //save user to database

        return member;
    }

    /**
     * login
     * @param username username(id)
     * @param password password
     * @return Member
     * @throws IllegalArgumentException if wrong password or no such user
     */
    public Member login(String username, String password) {
        //find user by username
        Optional<Member> member = this.memberRepository.findByUsername(username);

        if (member.isPresent()) { //exist user
            //check password
            if (passwordEncoder.matches(password, member.get().getPassword())) {
                return member.get(); //return user
            } else { //wrong password
                throw new IllegalArgumentException("Wrong id or password");
            }
        } else { //not exist user
            throw new IllegalArgumentException("Wrong id or password");
        }
    }
}
