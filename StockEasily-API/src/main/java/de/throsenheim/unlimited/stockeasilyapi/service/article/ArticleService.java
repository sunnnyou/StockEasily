package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.SearchArticleResponse;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    CreateArticleResponseDto create(CreateArticleRequestDto request);

    FieldError getImageFieldError();

    Optional<Article> search(long id);

    List<Article> searchAllByName(String name);

    List<SearchArticleResponse> searchAll();

    List<SearchArticleResponse> searchAllPage(int limit, int page);

    boolean validateImage(byte[] data);
}
