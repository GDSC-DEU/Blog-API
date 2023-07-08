package com.gdsc.blog.article.controller;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Article", description = "Article API")
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    ArticleService articleService;

    @GetMapping("/article/list")
    public String list(Model model) {
        List<Article> articleList = this.articleService.getArticleList();
        model.addAttribute("articleList", articleList);
        return "article/list";
    }
}
