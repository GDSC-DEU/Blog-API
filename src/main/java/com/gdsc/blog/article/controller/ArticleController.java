package com.gdsc.blog.article.controller;

import com.gdsc.blog.article.dto.ArticleCreateDto;
import com.gdsc.blog.article.dto.ArticleUpdateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/article")
@RestController
@RequiredArgsConstructor
@Tag(name = "게시글 Controller", description = "Article controller API")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;

    /**
     * 게시글 생성
     * @param dto 게시글 생성 정보
     * @return 생성된 게시글
     */
    @PostMapping //컨트롤러 메핑
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')") //권한 설정
    @Operation(summary = "게시글 생성") //swagger 설명
    public Article createArticle(
        @Parameter(name="게시글 생성 DTO") @RequestBody ArticleCreateDto dto){
        //로그인 유저 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.getUserByName(userName); //유저 이름 가져오기

        return articleService.createArticle(dto, user);
    }

    /**
     * 모든 게시글 조회
     * @param req HTTP 파싱 객체
     * @return 게시글 목록
     */
    @GetMapping("/get")
    @Operation(summary = "모든 게시글 조회")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    public List<Article> getUserArticle(
        @Parameter(hidden = true) HttpServletRequest req
    ) {
        User user = userService.whoami(req); //로그인 유저 정보 가져오기

        return articleService.getUserArticle(user);
    }

    /**
     * id로 게시글 조회
     * @param id 게시글 id
     * @param req HTTP 파싱 객체
     * @return 게시글
     */
    @GetMapping("/get/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "id로 게시글 조회")
    public Article getArticleById(
        @Parameter(description = "게시글 id") @PathVariable("id") Long id,
        @Parameter(hidden = true) HttpServletRequest req){
        return articleService.getArticleById(id);
    }

    /**
     * 제목으로 게시글 조회
     * @param title 게시글 제목
     * @param req HTTP 파싱 객체
     * @return 게시글
     */
    @GetMapping("/get/title/{title}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "제목으로 게시글 조회")
    public Article getArticleByTitle(
        @Parameter(description = "게시글 제목") @PathVariable(value = "title") String title,
        @Parameter(hidden = true) HttpServletRequest req){
        return articleService.getArticleByTitle(title);
    }

    /**
     * 게시글 수정
     * @param id 게시글 id
     * @param dto 게시글 수정 정보
     * @param req HTTP 파싱 객체
     */
    @PostMapping("/patch/{id}") //생성 & 수정은 PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "게시글 수정")
    public void updateArticle(
        @Parameter(description = "게시글 id") @PathVariable(value = "id") Long id,
        @Parameter(name="게시글 수정 DTO") @RequestBody ArticleUpdateDto dto,
        @Parameter(hidden = true) HttpServletRequest req) {
        User user = userService.whoami(req);

        articleService.updateArticle(id, dto);
    }

    /**
     * 게시글 삭제
     * @param id 게시글 id
     * @param req HTTP 파싱 객체
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "게시글 삭제")
    public void deleteArticle(
        @Parameter(description = "게시글 id") @PathVariable(value = "id") Long id,
        @Parameter(hidden = true) HttpServletRequest req) {
        User user = userService.whoami(req);

        Article article = articleService.getArticleById(id);

        articleService.deleteArticle(article);
    }
}
