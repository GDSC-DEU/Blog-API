package com.gdsc.blog.article.repository;

import com.gdsc.blog.article.entity.Article;

import java.util.List;

import com.gdsc.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleLike(String title);

    List<Article> findByUser(User user);

}
