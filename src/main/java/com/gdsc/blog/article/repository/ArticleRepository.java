package com.gdsc.blog.article.repository;

import com.gdsc.blog.article.entity.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article findByTitle(String title);
    List<Article> findByTitleLikeOrContentLike(String title, String content);
}