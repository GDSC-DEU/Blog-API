package com.gdsc.blog.comment.service;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.repository.CommentRepository;
import com.gdsc.blog.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Tag(name = "댓글 service", description = "Comment service API")
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * Create comment
     * @param article article object
     * @param content comment content
     * @param user login user
     */
    @Operation(summary = "댓글 생성")
    public void create(
        @Parameter(name = "article", description = "댓글을 생성할 게시글") Article article,
        @Parameter(name = "댓글 내용") String content,
        @Parameter(name = "로그인 유저", description = "user 객체") User user){
        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setContent(content);
        comment.setCreateData(LocalDateTime.now());
        comment.setModifyData(LocalDateTime.now());
        comment.setUser(user);
        this.commentRepository.save(comment);
    }

    /**
     * Get comment object
     * @param idx comment id
     * @return comment object
     */
    @Operation(summary = "id로 댓글 가져오기")
    public Comment getComment(
        @Parameter(name = "댓글 id") Long idx) {
        //return comment object if exists, or throw exception
        return this.commentRepository.findById(idx).orElseThrow();
    }

    /**
     * Update comment
     * @param comment comment object
     */
    @Operation(summary = "댓글 수정")
    public void update(
        @Parameter(name = "댓글", description = "수정할 댓글 객체") Comment comment){
        this.commentRepository.save(comment);
    }

    @Operation(summary = "댓글 삭제")
    public void delete(
        @Parameter(name = "댓글", description = "삭제할 댓글 객체") Comment comment){
        this.commentRepository.delete(comment);
    }
}
