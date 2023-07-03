package com.gdsc.blog.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;   //비밀번호 암호화


    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member register(Member member){
        member.setID(member.getID());
        member.setPhone(member.getPhone());
        member.setEmail(member.getEmail());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        this.memberRepository.save(member);
        return member;
    }
    public Member login(String ID, String pwd){
        Optional<Member> member = memberRepository.findById(ID);
        if(member != null && passwordEncoder.matches(pwd, member.get().getPassword())){
            return member.get();
        }
        else
            return null;
    }
}
