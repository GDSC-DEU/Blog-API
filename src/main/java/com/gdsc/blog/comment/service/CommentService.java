package com.gdsc.blog.comment.service;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.comment.dto.CommentUpdateDto;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.repository.CommentRepository;
import com.gdsc.blog.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    /**
     * 댓글 생성
     *
     * @param comment 댓글 객체
     * @param article 개시글 객체
     * @param user    로그인 유저 객체
     */
    public Comment create(Comment comment, Long id, User user) {
        comment.setArticle(articleRepository.findById(id).orElseThrow());
        comment.setUser(user);
        comment.setCreateData(LocalDateTime.now());
        comment.setModifyData(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommandByArticle(Long articleId) {
        return commentRepository.findByArticle_Idx(articleId);
    }

    /**
     * id로 댓글 조회
     *
     * @param idx 댓글 id
     * @return 댓글 객체
     */
    public Comment getCommentById(Long idx) {
        Optional<Comment> comment = commentRepository.findById(idx);

        return comment.orElseThrow();

    }

    /**
     * 댓글 수정
     *
     * @param id               댓글 id
     * @param commentUpdateDto 댓글 수정 DTO
     */
    public Comment updateComment(Long id, CommentUpdateDto commentUpdateDto) {

        Comment comment = getCommentById(id);
        comment.setContent(commentUpdateDto.getContent());
        comment.setModifyData(LocalDateTime.now());

        return commentRepository.save(comment);
    }


    /**
     * 댓글 삭제
     *
     * @param comment 댓글 객체
     */
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
