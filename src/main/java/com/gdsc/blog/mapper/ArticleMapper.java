package com.gdsc.blog.mapper;

import com.gdsc.blog.article.dto.ArticleDto;
import com.gdsc.blog.article.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mapping(target = "username", expression = "java(article.getUser().getUsername())")
    @Mapping(target = "createdAt", expression = "java(article.getCreateDate().toLocalDateTime())")
    @Mapping(target = "modifiedAt", expression = "java(article.getModifyDate().toLocalDateTime())")
    ArticleDto toDto(Article article);
}
