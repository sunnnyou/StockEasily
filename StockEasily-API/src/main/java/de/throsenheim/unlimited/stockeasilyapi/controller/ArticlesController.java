package de.throsenheim.unlimited.stockeasilyapi.controller;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.request.UpdateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.ApiErrorDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.GetArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.UpdateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.exception.InvalidBodyException;
import de.throsenheim.unlimited.stockeasilyapi.service.article.ArticleService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(ArticlesController.class);

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
        if (bindingResult.hasErrors()) {
            throw new InvalidBodyException(bindingResult);
        }
        final CreateArticleResponseDto result = this.articleService.create(request);
        if (result != null && request.getImage() != null && result.isImageInvalid()) {
            bindingResult.addError(this.articleService.getImageFieldError());
            throw new InvalidBodyException(bindingResult);
        }
        // INTERNAL SERVER ERROR should NOT occur
        final HttpStatus httpStatus = result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.CREATED;
        return new ResponseEntity<>(result, httpStatus);
    }

    @CrossOrigin
    @GetMapping(path = "/{id}", consumes = {"*/*"})
    public ResponseEntity<GetArticleResponseDto> searchArticle(
            @PathVariable String id) {
        long numericId;
        try {
            numericId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().build();
        }
        final GetArticleResponseDto result = articleService.search(numericId);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @CrossOrigin
    @ApiOperation(value = "Get all articles", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Articles found", response = List.class),
            @ApiResponse(code = 400, message = "Parameter validation error", response = HttpClientErrorException.BadRequest.class),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(consumes = {"*/*"})
    public ResponseEntity<List<GetArticleResponseDto>> searchAllArticles() {
        final List<GetArticleResponseDto> result = articleService.searchAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Update existing article", response = UpdateArticleResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Article updated", response = UpdateArticleResponseDto.class),
            @ApiResponse(code = 400, message = "Parameter validation error", response = ApiErrorDto.class),
            @ApiResponse(code = 500, message = "Entity serialization error", response = ApiErrorDto.class)
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(path = "/{id}")
    public ResponseEntity<UpdateArticleResponseDto> updateArticle(
            @ApiParam(name = "article") @Valid @RequestBody UpdateArticleRequestDto request,
            @PathVariable String id,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidBodyException(bindingResult);
        }
        Long articleId = articleService.getParsedIdOrNull(id);
        UpdateArticleResponseDto result;
        if (articleId == null) {
            return ResponseEntity.badRequest().build();
        }
        GetArticleResponseDto existingArticle = articleService.search(articleId);
        if (existingArticle == null) {
            return ResponseEntity.notFound().build();
        }
        result = articleService.update(articleId, request, existingArticle);
        if (result != null && request.getImage() != null && !result.isImageInvalid()) {
            bindingResult.addError(articleService.getImageFieldError());
            throw new InvalidBodyException(bindingResult);
        }

        // INTERNAL SERVER ERROR should NOT occur
        final HttpStatus httpStatus = result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
        return new ResponseEntity<>(result, httpStatus);
    }

    @CrossOrigin
    @ApiOperation(value = "Get articles from list with the amount depending on the limit and which ones on page", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Article list issued", response = List.class),
            @ApiResponse(code = 400, message = "Api parameter not an int", response = HttpClientErrorException.BadRequest.class),
            @ApiResponse(code = 404, message = "No articles found", response = HttpClientErrorException.NotFound.class)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/page/{page}", consumes = {"*/*"})
    public ResponseEntity<List<GetArticleResponseDto>> searchAllArticlesPage(
            @PathVariable int page) {
        final int limit = 10;
        if (page > 0) {
            final List<GetArticleResponseDto> resultList = articleService.searchAllPage(limit, page);
            return new ResponseEntity<>(resultList, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @CrossOrigin
    @ApiOperation(value = "Get articles from list with the amount depending on the limit and which ones on page", response = List.class)
    @ApiResponse(code = 200, message = "Article size issued", response = Integer.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/size", consumes = {"*/*"})
    public ResponseEntity<Integer> getArticleRepositorySize() {
        final int articleRepositorySize = articleService.getArticleRepositorySize();
        return ResponseEntity.ok(articleRepositorySize);
    }

    @CrossOrigin
    @ApiOperation(value = "Get articles from list with the amount depending on the limit and which ones on page", response = List.class)
    @ApiResponse(code = 200, message = "Article size issued", response = Integer.class)
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/size/{query}", consumes = {"*/*"})
    public ResponseEntity<Integer> getArticleRepositorySizeQuery(
            @PathVariable String query) {
        final int articleRepositorySize = articleService.getArticleRepositorySizeQuery(query);
        return ResponseEntity.ok(articleRepositorySize);
    }

    @CrossOrigin
    @ApiOperation(value = "Get articles from list with the amount depending on the limit and which ones on page and search query", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Article list issued", response = List.class),
            @ApiResponse(code = 400, message = "Api parameter not an int", response = HttpClientErrorException.BadRequest.class)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/search/{query}/{page}", consumes = {"*/*"})
    public ResponseEntity<List<GetArticleResponseDto>> searchFromQuery(
            @PathVariable String query,
            @PathVariable int page) {
        if (query == null || query.isBlank() || page < 1) {
            LOG.debug("Query: {}, Page: {}", query, page);
            return ResponseEntity.badRequest().build();
        }
        final int limit = 10;
        LOG.debug("Query: {}, Limit:{} ,Page: {}", query, limit, page);
        final List<GetArticleResponseDto> resultList = articleService.searchAllByQuery(query, limit, page);
        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @CrossOrigin
    @ApiOperation(value = "Get articles from list with the amount depending on the limit and which ones on page and search query", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted Article"),
            @ApiResponse(code = 404, message = "No article found with matching id", response = HttpClientErrorException.NotFound.class)
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/{id}", consumes = {"*/*"})
    public ResponseEntity<GetArticleResponseDto> deleteArticle(@PathVariable long id) {

        LOG.debug("Delete-Request for Article with ID: {}", id);
        final Optional<Integer> result = articleService.deleteArticle(id);
        if (result.isPresent() && result.get() > 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
