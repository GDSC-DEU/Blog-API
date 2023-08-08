package com.gdsc.blog.article.repository;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitleLike(String title);

    List<Article> findByUser(User user);
}
