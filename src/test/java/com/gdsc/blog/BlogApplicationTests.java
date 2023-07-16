package com.gdsc.blog;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.repository.CommentRepository;
import com.gdsc.blog.comment.service.CommentService;
import com.gdsc.blog.user.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Profile("test")
class BlogApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true);
    }
}
