package com.gdsc.blog.comment.repository;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticle(Article article);

    List<Comment> findByArticle_Idx(Long idx);

}
