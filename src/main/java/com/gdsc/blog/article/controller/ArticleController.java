package com.gdsc.blog.article.controller;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Tag(name = "게시글 Controller", description = "Article controller API")
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    ArticleService articleService;
    UserService userService;

    @PostMapping("/create")
    @Operation(summary = "게시글 생성")
    public void createArticle(
        @Parameter(name = "제목") String title,
        @Parameter(name = "내용") String content,
        @Parameter(name = "Principal", description = "로그인 유저 정보를 가지는 principal 객체") Principal principal
    ){
        User user = this.userService.getUser(principal.getName()); //login 유저 정보 가져오기
        this.articleService.createArticle(title, content, user); //게시글 생성
    }

    @GetMapping("/update/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public void updateArticle(
        @Parameter(name = "제목") String title,
        @Parameter(name = "수정할 내용") String content,
        @Parameter(name = "게시글 id") Long id,
        @Parameter(name = "Principal", description = "로그인 유저 정보를 가지는 principal 객체") Principal principal){
        Article article = this.articleService.getArticle(id); //게시글 정보 가져오기
        if(!article.getUser().getUsername().equals(principal.getName())){ //게시글 작성자와 로그인 유저가 다를 경우
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글을 수정할 권한이 없습니다.");
        }
        article.setTitle(title);
        article.setContent(content);
        article.setModifyDate(LocalDateTime.now());
        this.articleService.update(article);
    }

    @GetMapping("/delete/{articleId}")
    @PreAuthorize("isAuthenticated()")
    public void deleteArticle(
        @Parameter(name = "제목") Long id,
        @Parameter(name = "Principal", description = "로그인 유저 정보를 가지는 principal 객체") Principal principal){
        Article article = this.articleService.getArticle(id); //게시글 정보 가져오기
        if(!article.getUser().getUsername().equals(principal.getName())){ //게시글 삭제 권한 확인
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "게시글을 삭제할 권한이 없습니다.");
        }
        this.articleService.delete(article);
    }
}
