package com.gdsc.blog.comment.service;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.comment.dto.CommentUpdateDto;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.repository.CommentRepository;
import com.gdsc.blog.user.entity.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     *
     * @param article 개시글 객체
     * @param user    로그인 유저 객체
     */
    public Comment create(Article article, User user) {
        Comment comment = Comment.builder()
            .article(article)
            .content(article.getContent())
            .user(user)
            .build();

        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommandByArticle(Article article) {
        return commentRepository.findByArticle(article);
    }

    /**
     * id로 댓글 조회
     *
     * @param idx 댓글 id
     * @return 댓글 객체
     */
    public Comment getCommentById(Long idx) {
        Optional<Comment> comment = commentRepository.findById(idx);

        return comment.orElseThrow(() -> new NullPointerException("Not found Comment by id"));
    }

    /**
     * 댓글 수정
     *
     * @param id 댓글 id
     * @param commentUpdateDto 댓글 수정 DTO
     */
    public Comment updateComment(Long id, CommentUpdateDto commentUpdateDto) {

        Comment comment = getCommentById(id);
        comment.setContent(commentUpdateDto.getContent());

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
