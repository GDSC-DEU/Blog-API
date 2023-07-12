package com.gdsc.blog.comment.service;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.repository.CommentRepository;
import com.gdsc.blog.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Tag(name = "댓글 service", description = "Comment service API")
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     * 댓글 생성
     * @param comment 댓글 객체
     * @param article 개시글 객체
     * @param user 로그인 유저 객체
     */
    @Operation(summary = "댓글 생성")
    public Comment create(
        @Parameter(name = "댓글 객체") Comment comment,
        @Parameter(name = "게시글 내용") Article article,
        @Parameter(name = "로그인 유저") User user){
        comment.setArticle(article);
        comment.setUser(user);
        comment.setCreateData(LocalDateTime.now());
        comment.setModifyData(LocalDateTime.now());
        
        return commentRepository.save(comment);
    }

    public List<Comment> getAllCommandByArticle(Article article){
        return commentRepository.findByArticle(article);
    }

    /**
     * id로 댓글 조회
     * @param idx 댓글 id
     * @return 댓글 객체
     */
    @Operation(summary = "id로 댓글 조회")
    public Comment getCommentById(
        @Parameter(name = "댓글 id") Long idx) {
        Optional <Comment> comment = commentRepository.findById(idx);

        if(comment.isPresent()){
            return comment.get();
        }
        else{
            throw new NullPointerException("Not found Comment by id");
        }
    }

    /**
     * Update comment
     * @param comment comment object
     */
    @Operation(summary = "댓글 수정")
    public void update(
        @Parameter(name = "댓글", description = "수정할 댓글 객체") Comment comment){
        commentRepository.save(comment);
    }

    @Operation(summary = "댓글 삭제")
    public void delete(
        @Parameter(name = "댓글", description = "삭제할 댓글 객체") Comment comment){
        commentRepository.delete(comment);
    }
}
