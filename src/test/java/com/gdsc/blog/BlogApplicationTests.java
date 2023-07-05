package com.gdsc.blog;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
class BlogApplicationTests {
    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void contextLoads() {}

    @Test
    void testRepository(){
        //create new post
        Article post1 = new Article();
        //set title and content
        post1.setTitle("Post test 1 title");
        post1.setContent("Post test 2 content");
        post1.setCreateDate(LocalDateTime.now());
        //save post
        this.articleRepository.save(post1);
    }

}
