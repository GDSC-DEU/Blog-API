package com.gdsc.blog.article.service;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.repository.ArticleRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    

    public List<Article> getArticleList(){ //get all articles
        return this.articleRepository.findAll();
    }

    public Article getArticle(Long idx){ //get article by id
        Optional <Article> article = this.articleRepository.findById(idx);
        if(article.isPresent()){
            return article.get();
        }
        else{ //not found article by id
            throw new NullPointerException("Article not found");
        }
    }
}
