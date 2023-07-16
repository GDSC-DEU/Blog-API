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

    @Autowired
    private UserController userController;


    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Test
    void testComment() {
        //create new post
        Article post1 = new Article();
        //set title and content
        post1.setTitle("Post1 title");
        post1.setContent("Post1 content");
        post1.setCreateDate(LocalDateTime.now());
        //save post
        this.articleRepository.save(post1);

        //create new comment
        for (int i = 0; i < 3; i++) {
            this.commentService.create(post1, "post1 comment" + String.valueOf(i + 1));
        }

        //get comment by article
        Long articleIdx = post1.getIdx();
        List<Comment> comments = this.commentRepository.findByArticleIdx(articleIdx);
        assertEquals(3, comments.size()); //check comment count

        //get comment content
        Comment c = this.commentService.getComment(Long.valueOf(3)); //get comment by id
        String content = c.getContent(); //get comment content
        assertEquals("post1 comment3", content); //check comment content

        //update comment
        c.setContent("post1 comment3 updated");
        c.setModifyData(LocalDateTime.now());
        this.commentService.update(c);
        assertEquals("post1 comment3 updated", c.getContent()); //check updated content

        //delete comment
        this.commentService.delete(c);
        assertEquals(2, this.commentRepository.count()); //check comment count
    }
}
