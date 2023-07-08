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

    @PostMapping("/create/{postId}")
    public void createComment(@PathVariable("id") Long idx, @RequestParam String content){
        Article article = this.articleService.getArticle(idx);
        this.commentService.create(article, content);
    }
}
