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
import java.security.Principal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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
     * @param principal login user
     */
    @PostMapping("/create/{postId}")
    @Operation(summary = "댓글 생성")
    public void createComment(
        @Parameter(name = "게시글 id") @PathVariable("postId") Long idx,
        @Parameter(name = "내용") String content,
        @Parameter(name = "principal", description = "로그인 유저 정보를 가지는 principal 객체") Principal principal) {
        Article article = this.articleService.getArticle(idx); //get article object
        User user = this.userService.getUser(principal.getName()); //login 유저 정보 가져오기

        this.commentService.create(article, content, user); //create comment
    }

    /**
     * Read comment
     * @param idx comment id
     * @return comment content
     */
    @PostMapping("/read/{commentId}")
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
     */
    @PostMapping("/update/{commentId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 수정")
    public void updateComment(
        @Parameter(name = "댓글 id") @PathVariable("commentId") Long idx,
        @Parameter(name = "수정 내용") @RequestParam String content,
        @Parameter(name = "principal", description = "로그인 유저 정보를 가지는 principal 객체") Principal principal){
        Comment comment = this.commentService.getComment(idx); //get comment object
        if(!comment.getUser().getUsername().equals(principal.getName())){ //수정 권한 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글을 수정할 권한이 없습니다.");
        }
        comment.setContent(content); //update comment content
        comment.setModifyData(LocalDateTime.now()); //update modify date
        this.commentService.update(comment); //save comment
    }

    @PostMapping("/delete/{commentId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 삭제")
    public void deleteComment(
        @Parameter(name = "댓글 id") @PathVariable("commentId") Long idx,
        @Parameter(name = "principal", description = "로그인 유저 정보를 가지는 principal 객체") Principal principal){
        Comment comment = this.commentService.getComment(idx); //get comment object
        if(comment.getUser().getUsername().equals(principal.getName())){ //삭제 권한 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글을 삭제할 권한이 없습니다.");
        }
        this.commentService.delete(comment); //delete comment
    }
}
