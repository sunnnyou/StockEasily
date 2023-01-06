package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.SearchArticleResponse;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    CreateArticleResponseDto create(CreateArticleRequestDto request);

    Optional<Article> search(long id);

    List<Article> searchAllByName(String name);

    List<SearchArticleResponse> searchAll();

    List<SearchArticleResponse> searchAllPage(int limit, int page);

    int getArticleRepositorySize();

    List<SearchArticleResponse> searchAllByQuery(String query, int limit, int page);

    int getArticleRepositorySizeQuery(String query);
}
