package com.gdsc.blog.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {
    @Id
    private String ID;

    private String password;
    private String phone;
    @Column(unique = true)
    private String email;
}
