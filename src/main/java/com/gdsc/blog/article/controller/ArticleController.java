package com.gdsc.blog.article.controller;

import com.gdsc.blog.article.entity.Article;
import com.gdsc.blog.article.service.ArticleService;
import com.gdsc.blog.user.entity.User;
import com.gdsc.blog.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Article", description = "Article API")
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    ArticleService articleService;
    UserService userService;

    @PostMapping("/create")
    public void createArticle(String title, String content, Principal principal){
        User user = this.userService.getUser(principal.getName()); //login 유저 정보 가져오기

        this.articleService.createArticle(title, content, user); //게시글 생성
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Article> articleList = this.articleService.getArticleList();
        model.addAttribute("articleList", articleList);
        return "article/list";
    }
}
