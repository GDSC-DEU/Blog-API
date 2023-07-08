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

    /**
     * Create comment
     * @param article article object
     * @param content comment content
     */
    public void create(Article article, String content){
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setContent(content);
        comment.setCreeateData(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    /**
     * Get comment object
     * @param idx comment id
     * @return comment object
     */
    public Comment getComment(Long idx){
        //return comment object if exists, or thorw exception
        return this.commentRepository.findById(idx).orElseThrow();
    }
}