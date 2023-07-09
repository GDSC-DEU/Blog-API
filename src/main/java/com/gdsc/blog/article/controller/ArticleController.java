package com.gdsc.blog.article.controller;

import com.gdsc.blog.article.dto.CreateDto;
import com.gdsc.blog.article.dto.UpdateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "Article", description = "Article API")
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService; //private final로 안넣은 이유??
    private final UserService userService;

    public ArticleController(ArticleService articleService, UserService userService){
        this.articleService = articleService;
        this.userService = userService;
    }

    @PostMapping("/create")
    @Operation(summary = "게시글 생성")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")

    public Article create(@RequestBody CreateDto article) {

        Article article1 = Article.builder().subject(article.getSubject()).content(article.getContent()).build();

        //현재 인증된 사용자 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // 현재 인증된 사용자의 이름(아이디)를 가져온다.

        User user = userService.getUserByName(username); // DB에서 사용자를 조회합니다.
        return articleService.create(article1, user);
    }
    @GetMapping("/myallarticle")   //내 게시글 목록
    @Operation(summary = "내 게시글 목록")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<Article> myallarticle(HttpServletRequest req) {
        User user = userService.whoami(req);
        return articleService.myAllArticle(user);
    }

    @GetMapping("article/{id}")  // 특정 게시글
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "특정 게시글")
    public Article getarticle(HttpServletRequest req, @PathVariable("id") Long id){
        return articleService.getArticle(id);
    }

    @GetMapping("/allarticle")  // 모든 게시글 조회
    @Operation(summary = "모든 게시글 조회 ")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<Article> allarticle(HttpServletRequest req) {
        return articleService.getAllAriticle();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "게시글 업데이트")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public Article updateArticle(@PathVariable("id") Long id, @RequestBody UpdateDto article, HttpServletRequest req) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        // 현재 인증된 사용자 가져오기
        User user = userService.whoami(req);
        Article updatedArticle = articleService.updateArticle(id, article, user);
        return updatedArticle;
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "게시글 삭제")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> deleteArticle(@PathVariable("id") Long id, HttpServletRequest req) throws ChangeSetPersister.NotFoundException, AccessDeniedException {
        // 현재 인증된 사용자 가져오기
        User user = userService.whoami(req);
        articleService.deleteArticle(id, user);
        return ResponseEntity.ok().build();
    }
}
