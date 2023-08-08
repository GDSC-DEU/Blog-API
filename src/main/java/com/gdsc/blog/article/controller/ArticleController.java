package com.gdsc.blog.article.controller;

import com.gdsc.blog.article.dto.ArticleCreateDto;
import com.gdsc.blog.article.dto.ArticleDto;
import com.gdsc.blog.article.dto.ArticleUpdateDto;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.security.jwt.JwtTokenProvider;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/article")
@RestController
@RequiredArgsConstructor
@Tag(name = "게시글 Controller", description = "Article controller API")
@Slf4j
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 게시글 생성 API
     *
     * @param articleCreateDto 게시글 생성 DTO
     * @return 생성된 게시글
     */
    @PostMapping //컨트롤러 메핑
    @Operation(summary = "게시글 생성") //swagger 설명
    public ArticleDto createArticle(
            @Parameter(name = "게시글 생성 DTO") @RequestBody ArticleCreateDto articleCreateDto,
            @Parameter(hidden = true) HttpServletRequest req) {

        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        return articleService.createArticle(articleCreateDto, username);
    }

    /**
     * 최근 게시글 조회 API
     *
     * @return 게시글 목록
     */
    @GetMapping
    @Operation(summary = "최근 게시글 조회")
    public List<ArticleDto> getUserArticle() {
        return articleService.getRecentArticle();
    }

    /**
     * id 로 게시글 조회
     *
     * @param id  게시글 id
     * @param req HTTP 파싱 객체
     * @return 게시글
     */
    @GetMapping("/{id}")
    @Operation(summary = "id로 게시글 조회")
    public ArticleDto getArticleById(
            @Parameter(description = "게시글 id") @PathVariable("id") Long id) {
        return articleService.getArticleById(id);
    }

    /**
     * 제목으로 게시글 조회
     *
     * @param title 게시글 제목
     * @param req   HTTP 파싱 객체
     * @return 게시글
     */
    @GetMapping("/search")
    @Operation(summary = "제목으로 게시글 조회")
    public List<ArticleDto> getArticleByTitle(
            @Parameter(description = "게시글 제목") @RequestParam String title,
            @Parameter(hidden = true) HttpServletRequest req) {
        return articleService.findArticleByTitle(title);
    }

    /**
     * 게시글 수정
     *
     * @param id  게시글 id
     * @param dto 게시글 수정 정보
     * @param req HTTP 파싱 객체
     */
    @PatchMapping("/{id}")
    @Operation(summary = "게시글 수정")
    public ArticleDto updateArticle(
            @Parameter(description = "게시글 id") @PathVariable(value = "id") Long id,
            @Parameter(name = "게시글 수정 DTO") @RequestBody ArticleUpdateDto dto,
            @Parameter(hidden = true) HttpServletRequest req) {

        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        return articleService.updateArticle(id, dto, username);
    }

    /**
     * 게시글 삭제 API
     *
     * @param id  게시글 id
     * @param req HTTP 파싱 객체
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @Operation(summary = "게시글 삭제")
    public void deleteArticle(
            @Parameter(description = "게시글 id") @PathVariable(value = "id") Long id,
            @Parameter(hidden = true) HttpServletRequest req) {

        String username = jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req));
        articleService.deleteArticle(id, username);
    }
}
