package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.ArticleCreationDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.service.article.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    Article createArticle(@Valid @RequestBody ArticleCreationDto inputDto) {
        // this.articleService.
        // TODO implement
        return this.articleService.create(inputDto);
    }

}
