package com.gdsc.blog.comment.controller;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.repository.CommentRepository;
import com.gdsc.blog.comment.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Comment", description = "Comment API")
public class CommentController {
    private final ArticleService articleService;
    private final CommentService commentService;

    /**
     * Create comment
     * @param idx article id
     * @param content comment content
     */
    @PostMapping("/create/{postId}")
    public void createComment(@PathVariable("id") Long idx, @RequestParam String content){
        Article article = this.articleService.getArticle(idx); //get article object
        this.commentService.create(article, content); //create comment
    }

    /**
     * Read comment
     * @param idx comment id
     * @return comment content
     */
    @PostMapping("/read/{commentId}")
    public String readComment(@PathVariable("commentId") Long idx){
        Comment comment = this.commentService.getComment(idx); //get comment object
        return comment.getContent(); //return comment content
    }

    /**
     * Update comment
     * @param idx comment id
     * @param content comment content
     */
    @PostMapping("/update/{commentId}")
    public void updateComment(@PathVariable("commentId") Long idx, @RequestParam String content){
        Comment comment = this.commentService.getComment(idx); //get comment object
        comment.setContent(content); //update comment content
        comment.setModifyData(LocalDateTime.now()); //update modify date
        this.commentService.update(comment); //save comment
    }

    @PostMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long idx){
        Comment comment = this.commentService.getComment(idx); //get comment object
        this.commentService.delete(comment); //delete comment
    }
}
