package com.gdsc.blog.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity create(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username);

        user.setPassword(passwordEncoder.encode(password));     // Bean으로 등록한 PassWordEncoder 객체를 주입받아 사용

        this.userRepository.save(user);
        return user;
    }
}
