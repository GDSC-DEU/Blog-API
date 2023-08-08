package com.gdsc.blog.configuration;

import com.gdsc.blog.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 요청 권한 설정
        http.authorizeRequests()
                .requestMatchers("/h2-console/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/api/user/signin",
                        "/api/user/signup",
                        "/", "index.html") // 홈페이지
                .permitAll()
                .anyRequest()
                .authenticated();

        // Disable CSRF (cross site request forgery)
        http.csrf(AbstractHttpConfigurer::disable);

        // JWT Provider 등록
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

        return http.build();
    }
}
