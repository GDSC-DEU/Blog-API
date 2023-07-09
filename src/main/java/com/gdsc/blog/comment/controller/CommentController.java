package com.gdsc.blog.comment.controller;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.comment.service.CommentService;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/comment")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Comment", description = "Comment API")
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
    public void createComment(@PathVariable("id") Long idx, @RequestParam String content, Principal principal){
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
    public String readComment(@PathVariable("commentId") Long idx){
        Comment comment = this.commentService.getComment(idx); //get comment object
        return comment.getContent(); //return comment content
    }

    /**
     * Update comment
     * @param idx comment id
     * @param content comment content
     */
    @PostMapping("/update/{commentId}")
    public void updateComment(@PathVariable("commentId") Long idx, @RequestParam String content){
        Comment comment = this.commentService.getComment(idx); //get comment object
        comment.setContent(content); //update comment content
        comment.setModifyData(LocalDateTime.now()); //update modify date
        this.commentService.update(comment); //save comment
    }

    @PostMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long idx){
        Comment comment = this.commentService.getComment(idx); //get comment object
        this.commentService.delete(comment); //delete comment
    }
}
