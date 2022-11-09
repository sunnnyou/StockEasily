package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.ArticleCreationDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.service.article.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticlesController {

    private final IArticleService articleService;

    @Autowired
    public ArticlesController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") IArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid @RequestBody ArticleCreationDto inputDto) {
        return new ResponseEntity<>(this.articleService.create(inputDto), HttpStatus.CREATED);
    }

}
