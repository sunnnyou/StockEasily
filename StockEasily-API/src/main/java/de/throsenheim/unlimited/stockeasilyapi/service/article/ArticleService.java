package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;

import java.util.Optional;

public interface ArticleService {
    Article create(CreateArticleRequestDto request);

    Optional<Article> search(long id);
}
