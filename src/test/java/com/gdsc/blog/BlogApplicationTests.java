package com.gdsc.blog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.repository.CommentRepository;
import com.gdsc.blog.comment.service.CommentService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    void testArticle(){
        ////create new post
        Article post1 = new Article();
        //set title and content
        post1.setTitle("Post1 title");
        post1.setContent("Post1 content");
        post1.setCreateDate(LocalDateTime.now());
        //save post
        this.articleRepository.save(post1);

        Article post2 = new Article();
        post2.setTitle("Post2 title");
        post2.setContent("Post2 content");
        post2.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(post2);

        ////find all post
        List<Article> posts = this.articleRepository.findAll();
        assertEquals(2, posts.size()); //check post count

        ////check post title and content
        Article p1 = posts.get(0);
        assertEquals("Post1 title", p1.getTitle());
        assertEquals("Post1 content", p1.getContent());
        Article p2 = posts.get(1);
        assertEquals("Post2 title", p2.getTitle());
        assertEquals("Post2 content", p2.getContent());

        ////get id
        Optional<Article> oa1 = this.articleRepository.findById(p1.getIdx());
        Optional<Article> oa2 = this.articleRepository.findById(p2.getIdx());
        if(oa1.isPresent() && oa2.isPresent()){
            Article a1 = oa1.get();
            Article a2 = oa2.get();
            assertEquals("Post1 title", a1.getTitle());
            assertEquals("Post1 content", a1.getContent());
            assertEquals("Post2 title", a2.getTitle());
            assertEquals("Post2 content", a2.getContent());
        }

        //search by title
        Article a1 = this.articleRepository.findByTitle("Post1 title");
        assertEquals(1, a1.getIdx());
        Article a2 = this.articleRepository.findByTitle("Post2 title");
        assertEquals(2, a2.getIdx());

        //search by title or content
        List<Article> findPost = this.articleRepository.findByTitleLikeOrContentLike("%Post1%", "%Post1%");
        post1 = findPost.get(0);
        assertEquals("Post1 title", post1.getTitle());
        assertEquals("Post1 content", post1.getContent());

        //update post
        post1.setTitle("Post1 title updated");
        post1.setContent("Post1 content updated");
        this.articleRepository.save(post1); //save post
        assertEquals("Post1 title updated", post1.getTitle());
        assertEquals("Post1 content updated", post1.getContent());

        //delete post
        assertEquals(2, this.articleRepository.count()); //before delete post
        this.articleRepository.delete(post1); //delete one post
        assertEquals(1, this.articleRepository.count()); //after delete post
    }


    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Test
    void testComment(){
        //create new post
        Article post1 = new Article();
        //set title and content
        post1.setTitle("Post1 title");
        post1.setContent("Post1 content");
        post1.setCreateDate(LocalDateTime.now());
        //save post
        this.articleRepository.save(post1);

        //create new comment
        for(int i = 0; i < 3; i++){
            this.commentService.create(post1, "post1 comment" + String.valueOf(i+1));
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
    }
}
