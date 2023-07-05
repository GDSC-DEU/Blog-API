package com.gdsc.blog.user.entity;

import com.gdsc.blog.article.entity.Article;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
