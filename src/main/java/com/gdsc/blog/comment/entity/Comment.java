package com.gdsc.blog.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; //id

    @Column(length = 200)
    private String content; //내용

    private LocalDateTime createData; //create date
    private LocalDateTime modifyData; //update date

    @ManyToOne
    @JsonBackReference
    private Article article;

    @ManyToOne
    @JsonBackReference
    private User user;
}
