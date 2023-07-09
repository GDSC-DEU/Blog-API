package com.gdsc.blog.article.service;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    /**
     * 게시글 생성
     * @param title 제목
     * @param content 내용
     * @param user 작성자
     */
    public void createArticle(String title, String content, User user){ //create new article
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        article.setModifyDate(LocalDateTime.now());
        article.setUser(user);
        this.articleRepository.save(article);
    }

    /**
     * 게시글 목록 조회
     * @return 게시글 목록
     */
    public List<Article> getArticleList(){ //get all articles
        return this.articleRepository.findAll();
    }

    /**
     * id로 게시글 조회
     * @param idx 게시글 id
     * @return Article 객체
     */
    public Article getArticle(Long idx){ //get article by id
        Optional <Article> article = this.articleRepository.findById(idx);
        if(article.isPresent()){
            return article.get();
        }
        else{ //not found article by id
            throw new NullPointerException("Article not found");
        }
    }

    /**
     * 게시글 수정
     * @param article Article 객체
     */
    public void update(Article article){
        this.articleRepository.save(article);
    }

    /**
     * 게시글 삭제
     * @param article Article 객체
     */
    public void delete(Article article){
        this.articleRepository.delete(article);
    }
}
