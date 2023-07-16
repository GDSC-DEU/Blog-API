package com.gdsc.blog.article.service;

import com.gdsc.blog.article.dto.ArticleUpdateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.user.entity.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
     * @param article Article 객체
     * @param user    작성자
     */
    public Article createArticle(
        Article article,
        User user) { //create new article
        article.setCreateDate(new Timestamp(System.currentTimeMillis()));
        article.setModifyDate(new Timestamp(System.currentTimeMillis()));
        article.setUser(user);
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
     * @param user 작성자
     * @return Article 객체
     */
    public Article updateArticle(Long id, ArticleUpdateDto dto, User user) {

        Article article = getArticleById(id); //게시글 id로 게시글 가져오기
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setModifyDate(new Timestamp(System.currentTimeMillis()));

        return articleRepository.save(article);
    }

    /**
     * 게시글 삭제
     *
     * @param article 게시글 객체
     */
    public void deleteArticle(Article article) {
        articleRepository.delete(article);
    }
}
