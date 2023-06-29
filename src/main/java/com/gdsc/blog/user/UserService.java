package com.gdsc.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository; //get Repository
    private final PasswordEncoder passwordEncoder; //get PasswordEncoder

    public User create(String username, String password) {
        User user = new User(); //create new user

        //password encryption
        user.setPassword(passwordEncoder.encode(password)); //set password
        user.setUsername(username); //set username

        this.userRepository.save(user); //save user to database

        return user;
    }
}
