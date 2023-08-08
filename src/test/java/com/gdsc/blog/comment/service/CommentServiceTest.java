package com.gdsc.blog.comment.service;

import com.gdsc.blog.article.dto.ArticleCreateDto;
import com.gdsc.blog.article.dto.ArticleDto;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.comment.dto.CommentUpdateDto;
import com.gdsc.blog.comment.entity.Comment;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.entity.UserRole;
import com.gdsc.blog.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommentServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CommentService commentService;

    User user;
    User admin;
    ArticleDto articleDto;

    @BeforeAll
    void setUp() {
        // 회원 가입
        user = userService.signup(User.builder()
                .username("user1")
                .email("user1@test.com")
                .password("password")
                .build());
        admin = userService.signup(User.builder()
                .username("admin1")
                .email("admin1@admin.com")
                .password("password")
                .roles(Collections.singletonList(UserRole.ROLE_ADMIN))
                .build());
        userService.becomeAdmin(admin.getUsername());

        // 게시글 생성
        articleDto = articleService.createArticle(ArticleCreateDto.builder()
                .title("title")
                .content("content")
                .build(), user.getUsername());

    }

    @Test
    @Transactional
    @DisplayName("댓글 생성")
    void create() {
        Comment comment = Comment.builder()
                .content("content")
                .build();
        Comment saved = commentService.create(comment, articleDto.getIdx(), user);
        assertEquals(comment.getContent(), saved.getContent());
    }

    @Test
    @Transactional
    @DisplayName("게시글 별 댓글 조회")
    void getAllCommandByArticle() {
        // given
        Comment comment1 = Comment.builder()
                .content("content1")
                .build();
        Comment comment2 = Comment.builder()
                .content("content2")
                .build();
        Comment comment3 = Comment.builder()
                .content("content3")
                .build();
        commentService.create(comment1, articleDto.getIdx(), user);
        commentService.create(comment2, articleDto.getIdx(), user);
        commentService.create(comment3, articleDto.getIdx(), user);

        // when
        List<Comment> comments = commentService.getAllCommandByArticle(articleDto.getIdx());

        // then
        assertEquals(3, comments.size());
    }

    @Test
    @Transactional
    @DisplayName("특정 댓글 조회")
    void getCommentById() {
        // given
        Comment comment = Comment.builder()
                .content("content")
                .build();
        Comment saved = commentService.create(comment, articleDto.getIdx(), user);

        // when
        Comment found = commentService.getCommentById(saved.getIdx());

        // then
        assertEquals(saved.getIdx(), found.getIdx());
        assertEquals(saved.getContent(), found.getContent());
    }

    @Test
    @Transactional
    @DisplayName("댓글 수정")
    void updateComment() {
        // given
        Comment comment = Comment.builder()
                .content("content")
                .build();
        Comment saved = commentService.create(comment, articleDto.getIdx(), user);

        CommentUpdateDto commentUpdateDto = CommentUpdateDto.builder()
                .content("updated")
                .build();

        // when
        Comment updated = commentService.updateComment(saved.getIdx(), commentUpdateDto);

        // then
        assertEquals(saved.getIdx(), updated.getIdx());
        assertEquals("updated", updated.getContent());
    }

    @Test
    @DisplayName("댓글 삭제")
    @Transactional
    void delete() {
        // given
        Comment comment = Comment.builder()
                .content("content")
                .build();
        Comment saved = commentService.create(comment, articleDto.getIdx(), user);

        // when
        commentService.delete(saved);

        // then
        assertThrows(NoSuchElementException.class, () -> commentService.getCommentById(saved.getIdx()));
    }
}
