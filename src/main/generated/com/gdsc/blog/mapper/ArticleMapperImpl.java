package com.gdsc.blog.mapper;

import com.gdsc.blog.article.dto.ArticleDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.comment.entity.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-17T01:17:13+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public ArticleDto toDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleDto.ArticleDtoBuilder articleDto = ArticleDto.builder();

        articleDto.idx( article.getIdx() );
        articleDto.title( article.getTitle() );
        articleDto.content( article.getContent() );
        List<Comment> list = article.getComments();
        if ( list != null ) {
            articleDto.comments( new ArrayList<Comment>( list ) );
        }

        articleDto.username( article.getUser().getUsername() );
        articleDto.createdAt( article.getCreateDate().toLocalDateTime() );
        articleDto.modifiedAt( article.getModifyDate().toLocalDateTime() );

        return articleDto.build();
    }
}
