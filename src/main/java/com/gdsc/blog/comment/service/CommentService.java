package com.gdsc.blog.comment.service;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.repository.CommentRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public void create(Article article, String content){
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setContent(content);
        comment.setCreeateData(LocalDateTime.now());
        this.commentRepository.save(comment);
    }
}
