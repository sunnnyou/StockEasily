package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.ApiErrorDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.exception.InvalidBodyException;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.service.article.ArticleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {"Articles"}) // Set correct heading
@RestController
@RequestMapping(path = "/api/v1/articles", consumes = "application/json", produces = "application/json")
public class ArticlesController {

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "Add new article", response = CreateArticleResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Article added", response = CreateArticleResponseDto.class),
            @ApiResponse(code = 400, message = "Parameter validation error", response = ApiErrorDto.class),
            @ApiResponse(code = 500, message = "Entity serialization error", response = ApiErrorDto.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<CreateArticleResponseDto> createArticle(
            @ApiParam(name = "request") @Valid @RequestBody CreateArticleRequestDto request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidBodyException(bindingResult);
        }
        final Article result = this.articleService.create(request);
        // INTERNAL SERVER ERROR should NOT occur
        final HttpStatus httpStatus = result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
        return new ResponseEntity<>(new CreateArticleResponseDto(result), httpStatus);
    }

}
