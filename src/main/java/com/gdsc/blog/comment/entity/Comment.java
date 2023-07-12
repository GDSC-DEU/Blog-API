package com.gdsc.blog.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private Article article;

    @ManyToOne
    @JsonBackReference
    private User user;
}
