package com.gdsc.blog.comment.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.base.entity.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comments")
public class Comment extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //id

    @ManyToOne
    @JsonBackReference
    private Article article;

}
