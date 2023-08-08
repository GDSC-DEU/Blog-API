package com.gdsc.blog.mapper;

import com.gdsc.blog.article.dto.ArticleDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.user.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class ArticleMapperTest {

    ArticleMapper articleMapper = Mappers.getMapper(ArticleMapper.class);

    @Test
    void toDto() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime localDateTime = timestamp.toLocalDateTime();

        log.info("timestamp: {}", timestamp);
        log.info("localDateTime: {}", localDateTime);

        // given
        Article article = Article.builder()
                .idx(1L)
                .title("제목")
                .content("내용")
                .createDate(timestamp)
                .modifyDate(timestamp)
                .user(User.builder().username("user").build())
                .build();
        // when
        ArticleDto articleDto = articleMapper.toDto(article);

        // then
        assertEquals(article.getIdx(), articleDto.getIdx());
        assertEquals(article.getTitle(), articleDto.getTitle());
        assertEquals(article.getContent(), articleDto.getContent());
        assertEquals(article.getUser().getUsername(), articleDto.getUsername());
        assertEquals(localDateTime, articleDto.getCreatedAt());
        assertEquals(localDateTime, articleDto.getModifiedAt());
        assertEquals(article.getUser().getUsername(), articleDto.getUsername());
    }
}
