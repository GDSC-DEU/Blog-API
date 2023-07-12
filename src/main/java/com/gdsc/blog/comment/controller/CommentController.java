package com.gdsc.blog.comment.controller;

import com.gdsc.blog.article.dto.ArticleCreateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.comment.dto.CommentCreateDto;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.service.CommentService;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.command.Command;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("api/comment")
@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "댓글", description = "Comment Controller API")
public class CommentController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final UserService userService;

    /**
     * 댓글 생성
     * @param id 게시글 id
     * @param dto 댓글 생성 정보
     * @param req HTTP 파싱 객체
     */
    @PostMapping("/create/{postId}") //컨트롤러 메핑
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')") //권한 설정
    @Operation(summary = "댓글 생성")
    public Comment createComment(
        @PathVariable("postId") Long id,
        @Parameter(name="댓글 생성 DTO") @RequestBody CommentCreateDto dto,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req) {

        Comment comment = Comment.builder()
            .content(dto.getContent())
            .build();

        User user = userService.whoami(req); //로그인 유저 정보 가져오기

        Article article = this.articleService.getArticleById(id); //get article object
        return this.commentService.create(comment, article, user); //create comment
    }

    /**
     * 게시글에 대한 모든 댓글 조회
     * @param id 게시글 id
     * @param req HTTP 파싱 객체
     * @return 댓글 객체 List
     */
    @GetMapping("/allCommands/{postId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "게시글에 대한 모든 댓글 조회")
    public List<Comment> getAllCommand(
        @PathVariable("postId") Long id,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req) {
        User user = userService.whoami(req);

        Article article = this.articleService.getArticleById(id); //get article object

        return commentService.getAllCommandByArticle(article); //return comment content
    }

    /**
     * id로 댓글 조회
     * @param id 댓글 id
     * @param req HTTP 파싱 객체
     * @return 댓글 객체
     */
    @GetMapping("/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "id로 댓글 조회")
    public Comment getCommentById(
        @PathVariable("commentId") Long id,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req) {
        User user = userService.whoami(req);

        return commentService.getCommentById(id);
    }

    /**
     * Update comment
     * @param idx comment id
     * @param content comment content
     * @param req HTTP parsing object
     */
    @PostMapping("/update/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "댓글 수정")
    public void updateComment(
        @Parameter(name = "댓글 id") @PathVariable("commentId") Long idx,
        @Parameter(name = "수정 내용") @RequestParam String content,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req){
        User user = userService.whoami(req);

        Comment comment = this.commentService.getCommentById(idx); //get comment object
        comment.setContent(content); //update comment content
        comment.setModifyData(LocalDateTime.now()); //update modify date
        this.commentService.update(comment); //save comment
    }

    /**
     * 댓글 삭제
     * @param idx 댓글 id
     * @param req HTTP 파싱 객체
     */
    @GetMapping("/delete/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "댓글 삭제")
    public void deleteComment(
        @Parameter(name = "댓글 id") @PathVariable("commentId") Long idx,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req){
        User user = userService.whoami(req);

        Comment comment = this.commentService.getCommentById(idx); //get comment object
        this.commentService.delete(comment); //delete comment
    }
}
