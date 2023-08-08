package com.gdsc.blog.user.entity;

import com.gdsc.blog.article.entity.Article;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(unique = true)
    private String email;

    private String password;

    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    List<UserRole> roles;

    //set relationship between article and user
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Article> articles;
}
