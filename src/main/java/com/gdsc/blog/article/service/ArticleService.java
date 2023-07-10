package com.gdsc.blog.article.service;

import com.gdsc.blog.article.dto.UpdateDto;
import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article create(Article article, User user){
        article.setCreateDate(LocalDateTime.now());
        article.setUser(user);
        return articleRepository.save(article);
    }

    public Article getArticle(Long id){
        List<Article> Allarticle = articleRepository.findAll();

        for(Article article: Allarticle){
            if(article.getIdx() == id){
                return article;
            }
        }
        throw new RuntimeException("No article");
    }

    public List<Article> myAllArticle(User user){
        return user.getArticleList();
    }

    public List<Article> getAllAriticle(){
        return articleRepository.findAll();
    }

    public Article updateArticle(Long idx, UpdateDto newarticle, User user){
        List<Article> Allarticle = articleRepository.findAll();

        for(Article article: Allarticle){
            if(article.getIdx() == idx){
                article.setSubject(newarticle.getSubject());
                article.setContent(newarticle.getContent());
                return articleRepository.save(article);
            }
        }
        throw new RuntimeException("No article");
    }

    public void deleteArticle(Long id, User user){
        // find article by id
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        // check if the logged-in user is the owner of the article
        if (!article.getUser().getIdx().equals(user.getIdx())) {
            throw new RuntimeException("Permission denied");
        }

        // delete the article
        articleRepository.delete(article);
    }
}
