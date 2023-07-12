package com.gdsc.blog.comment.controller;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.service.CommentService;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/comment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "댓글", description = "Comment Controller API")
public class CommentController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    /**
     * Create comment
     * @param idx article id
     * @param content comment content
     * @param req HTTP parsing object
     */
    @PostMapping("/create/{postId}") //컨트롤러 메핑
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //권한 설정
    @Operation(summary = "댓글 생성")
    public void createComment(
        @Parameter(name = "게시글 id") @PathVariable("postId") Long idx,
        @Parameter(name = "내용") String content,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req) {
        User user = userService.whoami(req); //로그인 유저 정보 가져오기

        Article article = this.articleService.getArticleById(idx); //get article object
        this.commentService.create(article, content, user); //create comment
    }

    /**
     * Read comment
     * @param idx comment id
     * @return comment content
     */
    @PostMapping("/read/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "댓글 읽기")
    public String readComment(
        @Parameter(name = "댓글 id") @PathVariable("commentId") Long idx){
        Comment comment = this.commentService.getComment(idx); //get comment object
        return comment.getContent(); //return comment content
    }

    /**
     * Update comment
     * @param idx comment id
     * @param content comment content
     * @param req HTTP parsing object
     */
    @PostMapping("/update/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "댓글 수정")
    public void updateComment(
        @Parameter(name = "댓글 id") @PathVariable("commentId") Long idx,
        @Parameter(name = "수정 내용") @RequestParam String content,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req){
        User user = userService.whoami(req);

        Comment comment = this.commentService.getComment(idx); //get comment object
        comment.setContent(content); //update comment content
        comment.setModifyData(LocalDateTime.now()); //update modify date
        this.commentService.update(comment); //save comment
    }

    /**
     * 댓글 삭제
     * @param idx 댓글 id
     * @param req HTTP 파싱 객체
     */
    @PostMapping("/delete/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "댓글 삭제")
    public void deleteComment(
        @Parameter(name = "댓글 id") @PathVariable("commentId") Long idx,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req){
        User user = userService.whoami(req);

        Comment comment = this.commentService.getComment(idx); //get comment object
        this.commentService.delete(comment); //delete comment
    }
}
