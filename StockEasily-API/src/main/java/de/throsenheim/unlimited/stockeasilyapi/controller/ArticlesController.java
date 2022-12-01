package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.CreateArticleDto;
import de.throsenheim.unlimited.stockeasilyapi.exception.InvalidBodyException;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticlesController {

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Article> createArticle(@Valid @RequestBody ArticleCreationDto inputDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidBodyException(bindingResult);
        }
        final Article result = this.articleService.create(inputDto);
        // INTERNAL SERVER ERROR should NOT occur
        final HttpStatus httpStatus = result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
        return new ResponseEntity<>(result, httpStatus);
    }

}
