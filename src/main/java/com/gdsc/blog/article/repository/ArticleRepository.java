package com.gdsc.blog.article.repository;

import com.gdsc.blog.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {
}
