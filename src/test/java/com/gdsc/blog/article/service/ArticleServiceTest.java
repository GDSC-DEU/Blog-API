package com.gdsc.blog.article.service;

import com.gdsc.blog.article.dto.ArticleCreateDto;
import com.gdsc.blog.article.dto.ArticleDto;
import com.gdsc.blog.article.dto.ArticleUpdateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.entity.UserRole;
import com.gdsc.blog.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // @BeforeAll을 사용하기 위해 필요
class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    User user;

    @BeforeAll
    void setUp() {
        // 회원 가입
        user = userService.signup(User.builder()
                .username("user")
                .email("user@test.com")
                .password("password")
                .build());
    }

    @Test
    @DisplayName("게시글 생성")
    @Transactional
    void createArticle() {
        // given
        ArticleCreateDto articleCreateDto = ArticleCreateDto.builder()
                .title("제목")
                .content("내용")
                .build();
        // when
        ArticleDto article = articleService.createArticle(articleCreateDto, user);

        // then
        assertEquals(articleCreateDto.getTitle(), article.getTitle());
        assertEquals(articleCreateDto.getContent(), article.getContent());
        assertEquals(user.getUsername(), article.getUsername());
        assertNotNull(article.getIdx());
    }

    @Test
    @DisplayName("유저별 게시글 조회")
    @Transactional
    void getUserArticle() {
        // given
        List<ArticleCreateDto> articleCreateDtoList = Arrays.asList(
                ArticleCreateDto.builder()
                        .title("제목1")
                        .content("내용1")
                        .build(),
                ArticleCreateDto.builder()
                        .title("제목2")
                        .content("내용2")
                        .build(),
                ArticleCreateDto.builder()
                        .title("제목3")
                        .content("내용3")
                        .build()
        );
        for (ArticleCreateDto articleCreateDto : articleCreateDtoList) {
            articleService.createArticle(articleCreateDto, user);
        }
        // when
        List<ArticleDto> articleDtoList = articleService.getUserArticle(user);

        // then
        assertEquals(articleCreateDtoList.size(), articleDtoList.size());
        for (int i = 0; i < articleCreateDtoList.size(); i++) {
            assertEquals(articleCreateDtoList.get(i).getTitle(), articleDtoList.get(i).getTitle());
            assertEquals(articleCreateDtoList.get(i).getContent(), articleDtoList.get(i).getContent());
            assertEquals(user.getUsername(), articleDtoList.get(i).getUsername());
        }

    }

    @Test
    @Transactional
    @DisplayName("게시글 번호로 게시글 조회")
    void getArticleById() {
        // given
        ArticleCreateDto articleCreateDto = ArticleCreateDto.builder()
                .title("제목")
                .content("내용")
                .build();
        ArticleDto SavedArticle = articleService.createArticle(articleCreateDto, user);


        // when
        ArticleDto foundArticle = articleService.getArticleById(SavedArticle.getIdx());

        // then
        assertEquals(SavedArticle.getIdx(), foundArticle.getIdx());
        assertEquals(SavedArticle.getTitle(), foundArticle.getTitle());
        assertEquals(SavedArticle.getContent(), foundArticle.getContent());
        assertEquals(SavedArticle.getUsername(), foundArticle.getUsername());
    }

    @Test
    @Transactional
    @DisplayName("게시글 제목으로 검색")
    void findArticleByTitle() {
        // given
        List<ArticleCreateDto> articleCreateDtoList = Arrays.asList(
                ArticleCreateDto.builder()
                        .title("제목1")
                        .content("내용1")
                        .build(),
                ArticleCreateDto.builder()
                        .title("제목2")
                        .content("내용2")
                        .build(),
                ArticleCreateDto.builder()
                        .title("제목3")
                        .content("내용3")
                        .build(),
                ArticleCreateDto.builder()
                        .title("이상한 제목")
                        .content("이상한 내용")
                        .build(),
                ArticleCreateDto.builder()
                        .title("NULL")
                        .content("NULL")
                        .build()
        );

        for (ArticleCreateDto articleCreateDto : articleCreateDtoList) {
            articleService.createArticle(articleCreateDto, user);
        }

        // when
        List<ArticleDto> articleDtoList = articleService.findArticleByTitle("제목");

        // then
        for (ArticleDto articleDto : articleDtoList) {
            assertTrue(articleDto.getTitle().contains("제목"));
        }


    }

    @Test
    @DisplayName("게시글 수정")
    void updateArticle() {
        // given
        ArticleCreateDto articleCreateDto = ArticleCreateDto.builder()
                .title("제목")
                .content("내용")
                .build();
        ArticleDto SavedArticle = articleService.createArticle(articleCreateDto, user);

        ArticleUpdateDto articleUpdateDto = ArticleUpdateDto.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        // when
        ArticleDto updatedArticle = articleService.updateArticle(SavedArticle.getIdx(), articleUpdateDto, user);

        // then
        assertEquals(articleUpdateDto.getTitle(), updatedArticle.getTitle());
        assertEquals(articleUpdateDto.getContent(), updatedArticle.getContent());
        assertEquals(user.getUsername(), updatedArticle.getUsername());
        assertEquals(SavedArticle.getIdx(), updatedArticle.getIdx());
    }

    @Test
    @DisplayName("게시글 수정 - 권한 없음")
    void updateArticleNoPermission() {
        // given
        ArticleCreateDto articleCreateDto = ArticleCreateDto.builder()
                .title("제목")
                .content("내용")
                .build();
        ArticleDto SavedArticle = articleService.createArticle(articleCreateDto, user);

        ArticleUpdateDto articleUpdateDto = ArticleUpdateDto.builder()
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        // when
        User anotherUser = User.builder()
                .username("anotherUser")
                .email("anotherUser@test.com")
                .password("anotherUser")
                .build();

        assertThrows(IllegalArgumentException.class, () -> articleService.updateArticle(SavedArticle.getIdx(), articleUpdateDto, anotherUser));
    }

    @Test
    @DisplayName("작성자가 게시글을 삭제")
    void deleteArticle() {
        // given
        ArticleCreateDto articleCreateDto = ArticleCreateDto.builder()
                .title("제목")
                .content("내용")
                .build();
        ArticleDto SavedArticle = articleService.createArticle(articleCreateDto, user);

        // when
        articleService.deleteArticle(SavedArticle.getIdx(), user);

        // then
        assertThrows(IllegalArgumentException.class, () -> articleService.getArticleById(SavedArticle.getIdx()));
    }

    @Test
    @DisplayName("관리자가 게시글을 삭제")
    void deleteArticleByAdmin() {
        // given
        ArticleCreateDto articleCreateDto = ArticleCreateDto.builder()
                .title("제목")
                .content("내용")
                .build();
        ArticleDto SavedArticle = articleService.createArticle(articleCreateDto, user);

        // when

        User admin = User.builder().roles(Collections.singletonList(UserRole.ROLE_ADMIN)).build();
        articleService.deleteArticle(SavedArticle.getIdx(), admin);

        // then
        assertThrows(IllegalArgumentException.class, () -> articleService.getArticleById(SavedArticle.getIdx()));
    }

}
