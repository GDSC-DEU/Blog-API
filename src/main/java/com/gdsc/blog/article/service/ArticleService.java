package com.gdsc.blog.article.service;

import com.gdsc.blog.article.dto.ArticleUpdateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Tag(name = "게시글 Service", description = "Article service API")
public class ArticleService {
    private final ArticleRepository articleRepository;

    /**
     * 게시글 생성
     * @param article Article 객체
     * @param user 작성자
     */
    @Operation(summary = "게시글 생성")
    public Article createArticle(
        @Parameter(name = "게시글 객체") Article article,
        @Parameter(name = "유저 객체") User user){ //create new article
        article.setCreateDate(LocalDateTime.now());
        article.setModifyDate(LocalDateTime.now());
        article.setUser(user);
        return articleRepository.save(article);
    }

    /**
     * 유저 게시글 목록 조회
     * @return 게시글 목록
     */
    @Operation(summary = "유저 게시글 목록 가져오기")
    public List<Article> getUserArticle(User user){ //get all articles
        return user.getArticles();
    }

    /**
     * id로 게시글 조회
     * @param idx 게시글 id
     * @return Article 객체
     */
    @Operation(summary = "게시글 조회")
    public Article getArticleById(
        @Parameter(name = "게시글 id") Long idx){ //get article by id
        Optional <Article> article = articleRepository.findById(idx);

        if(article.isPresent()){
            return article.get();
        }
        else{ //not found article by id
            throw new NullPointerException("Not found Article by id");
        }
    }

    /**
     * 게시글 수정
     * @param id 게시글 id
     * @param dto 게시글 수정 DTO
     * @param user 작성자
     * @return Article 객체
     */
    @Operation(summary = "게시글 수정")
    public Article updateArticle(
        @Parameter(name = "게시글 id") Long id,
        @Parameter(name = "게시글 수정 DTO") ArticleUpdateDto dto,
        @Parameter(name = "작성자") User user){

        Article article = getArticleById(id); //게시글 id로 게시글 가져오기
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setModifyDate(LocalDateTime.now());
        return articleRepository.save(article);
    }

    /**
     * 게시글 삭제
     * @param article 게시글 객체
     */
    @Operation(summary = "게시글 삭제")
    public void deleteArticle(
        @Parameter(name = "개시글 객체") Article article){
        articleRepository.delete(article);
    }
}
