package com.gdsc.blog.article.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdsc.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder //빌더 패턴을 사용
@AllArgsConstructor //모든 필드를 매개변수로 받는 생성자
@NoArgsConstructor //매개변수가 없는 기본 생성자
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String subject;

    private String content;

    private LocalDateTime createDate;

    @JsonBackReference
    @ManyToOne
    private User user;
}
