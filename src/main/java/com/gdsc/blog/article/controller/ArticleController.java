package com.gdsc.blog.article.controller;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "게시글 Controller", description = "Article controller API")
@RestController
@RequestMapping("/api/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    /**
     * 게시글 생성
     * @param title 제목
     * @param content 내용
     * @return 생성된 게시글
     */
    @PostMapping("/create") //컨트롤러 메핑
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')") //권한 설정
    @Operation(summary = "게시글 생성") //swagger 설명
    public Article createArticle(
        @Parameter(name = "제목") String title, //swagger 파라미터 설명
        @Parameter(name = "내용") String content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user = userService.getUserByName(userName); //로그인 유저 정보 가져오기
        return articleService.createArticle(title, content, user); //게시글 생성
    }

    /**
     * 모든 게시글 조회
     * @param req HTTP 파싱 객체
     * @return 게시글 목록
     */
    @GetMapping("/allArticle")
    @Operation(summary = "모든 게시글 조회")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public List<Article> getUserArticle(
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req
    ) {
        User user = userService.whoami(req); //로그인 유저 정보 가져오기

        return articleService.getUserArticle(user);
    }

    /**
     * 게시글 수정
     * @param title 제목
     * @param content 내용
     * @param id 게시글 id
     * @param req HTTP 파싱 객체
     */
    @GetMapping("/update/{articleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "게시글 수정")
    public void updateArticle(
        @Parameter(name = "제목") String title,
        @Parameter(name = "수정할 내용") String content,
        @Parameter(name = "게시글 id") Long id,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req) {
        User user = userService.whoami(req);

        Article article = articleService.getArticle(id); //게시글 정보 가져오기
        article.setTitle(title);
        article.setContent(content);
        article.setModifyDate(LocalDateTime.now());
        articleService.update(article);
    }

    /**
     * 게시글 삭제
     * @param id 게시글 id
     * @param req HTTP 파싱 객체
     */
    @GetMapping("/delete/{articleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @Operation(summary = "게시글 삭제")
    public void deleteArticle(
        @Parameter(name = "게시글 id") Long id,
        @Parameter(name = "HTTP 파싱 객체") HttpServletRequest req) {
        User user = userService.whoami(req);
        Article article = articleService.getArticle(id);

        articleService.delete(article);
    }
}
