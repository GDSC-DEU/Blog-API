package com.gdsc.blog.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Getter
@Setter
@Entity
public class User {

    @Id
    private Long id;

    private String username;
    private String password;
}
