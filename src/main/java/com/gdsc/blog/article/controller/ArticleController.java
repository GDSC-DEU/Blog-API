package com.gdsc.blog.article.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Article", description = "Article API")
@RestController
@RequestMapping("/api/article")
public class ArticleController {


}
