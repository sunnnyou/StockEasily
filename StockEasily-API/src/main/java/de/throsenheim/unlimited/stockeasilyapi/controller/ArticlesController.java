package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.ApiErrorDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.SearchArticleResponse;
import de.throsenheim.unlimited.stockeasilyapi.exception.InvalidBodyException;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.service.article.ArticleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Api(tags = {"Articles"}) // Set correct heading
@RestController
@RequestMapping(path = "/api/v1/articles", consumes = "application/json", produces = "application/json")
public class ArticlesController {

    private final ArticleService articleService;

    @Autowired
    public ArticlesController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ApiOperation(value = "Add new article", response = CreateArticleResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Article added", response = CreateArticleResponseDto.class),
            @ApiResponse(code = 400, message = "Parameter validation error", response = ApiErrorDto.class),
            @ApiResponse(code = 500, message = "Entity serialization error", response = ApiErrorDto.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<CreateArticleResponseDto> createArticle(
            @ApiParam(name = "article") @Valid @RequestBody CreateArticleRequestDto request,
            BindingResult bindingResult) {
        if (bindingResult != null && bindingResult.hasErrors()) {
            throw new InvalidBodyException(bindingResult);
        }
        final CreateArticleResponseDto result = this.articleService.create(request);
        if (result != null && request.getImage() != null && result.isImageInvalid()) {
            if (bindingResult != null) {
                bindingResult.addError(this.articleService.getImageFieldError());
                throw new InvalidBodyException(bindingResult);
            }
            throw new InvalidBodyException();
        }
        // INTERNAL SERVER ERROR should NOT occur
        final HttpStatus httpStatus = result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @CrossOrigin
    @GetMapping(path = "/{articleId}", consumes = {"*/*"})
    public ResponseEntity<SearchArticleResponse> searchArticle(
            @PathVariable String articleId) {
        try {
            final Optional<Article> resultOptional = articleService.search(Long.parseLong(articleId));
            if (resultOptional.isPresent()) {
                final Article result = resultOptional.get();
                final HttpStatus httpStatus = HttpStatus.OK;
                return new ResponseEntity<>(new SearchArticleResponse(result), httpStatus);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException ex) {
            // log
            return ResponseEntity.badRequest().build();
        }
    }

//    @ApiOperation(value = "Get article list with specific name", response = List.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Article list issued", response = List.class),
//            @ApiResponse(code = 400, message = "Api parameter not a string", response = HttpClientErrorException.BadRequest.class),
//            @ApiResponse(code = 404, message = "No articles with this name found", response = HttpClientErrorException.NotFound.class)
//    })
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping(path = "/{articleName}", consumes = {"*/*"})
//    public ResponseEntity<List<Article>> searchAllArticlesByName(
//            @PathVariable String articleName) {
//        final List<Article> resultList = articleService.searchAllByName(articleName);
//        return validateResponseList(resultList);
//    }

    @CrossOrigin
    @ApiOperation(value = "Get all articles in list", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Article list issued", response = List.class),
            @ApiResponse(code = 400, message = "Api parameter not a string", response = HttpClientErrorException.BadRequest.class),
            @ApiResponse(code = 404, message = "No articles found", response = HttpClientErrorException.NotFound.class)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(consumes = {"*/*"})
    public ResponseEntity<List<Article>> searchAllArticles() {
        final List<Article> resultList = articleService.searchAll();
        return validateResponseList(resultList);
    }

    private ResponseEntity<List<Article>> validateResponseList(List<Article> resultList) {
        try {
            if (resultList.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                final HttpStatus httpStatus = HttpStatus.OK;
                return new ResponseEntity<>(resultList, httpStatus);
            }
        } catch (NumberFormatException ex) {
            // log
            return ResponseEntity.badRequest().build();
        }
    }
}
