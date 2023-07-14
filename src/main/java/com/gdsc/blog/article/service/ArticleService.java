package com.gdsc.blog.article.service;

import com.gdsc.blog.article.dto.ArticleCreateDto;
import com.gdsc.blog.article.dto.ArticleUpdateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.user.entity.User;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    /**
     * 게시글 생성
     *
     * @param dto 게시글 생성 정보
     * @param user    작성자
     */
    @Transactional
    public Article createArticle(ArticleCreateDto dto, User user) { //create new article
        Article article = Article.builder()
            .title(dto.getTitle())
            .content(dto.getContent())
            .user(user)
            .build();

        return articleRepository.save(article);
    }

    /**
     * 유저 게시글 목록 조회
     *
     * @return 게시글 목록
     */
    public List<Article> getUserArticle(User user) { //get all articles
        return user.getArticles();
    }

    /**
     * id로 게시글 조회
     *
     * @param idx 게시글 id
     * @return Article 객체
     */
    public Article getArticleById(Long idx) { //get article by id
        Optional<Article> article = articleRepository.findById(idx);

        return article.orElseThrow(() -> new NullPointerException("Not found Article by id"));
    }

    public Article getArticleByTitle(String title){
        Optional<Article> article = articleRepository.findByTitle(title);

        return article.orElseThrow(() -> new NullPointerException("Not found Article by title"));
    }

    /**
     * 게시글 수정
     *
     * @param id   게시글 id
     * @param dto  게시글 수정 DTO
     */
    @Transactional
    public void updateArticle(Long id, ArticleUpdateDto dto) {
        Article article = getArticleById(id); //게시글 id로 게시글 가져오기
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());

        //articleRepository.save(article);
        //Transactional 어노테이션으로 save() 메소드를 사용하지 않아도 된다.
    }

    /**
     * 게시글 삭제
     *
     * @param article 게시글 객체
     */
    @Transactional
    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }
}
