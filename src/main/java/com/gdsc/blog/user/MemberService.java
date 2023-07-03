package com.gdsc.blog.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member create(String ID, String pwd, String phone, String email){
        Member member = new Member();
        member.setID(ID);
        member.setPhone(phone);
        member.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(pwd));
        this.memberRepository.save(member);
        return member;
    }
}
