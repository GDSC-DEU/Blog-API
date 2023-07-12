package com.gdsc.blog.article.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor //생성자 자동 생성
@Getter
@Setter
@Builder
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; //id

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT") //no limit length of text
    private String content;

    private LocalDateTime createDate; //생성 날짜
    private LocalDateTime modifyDate; //수정된 날짜

    //set relationship between article and user
    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}