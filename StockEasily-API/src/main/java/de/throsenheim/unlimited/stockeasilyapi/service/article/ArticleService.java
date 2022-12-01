package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.CreateArticleDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;

public interface ArticleService {
    Article create(CreateArticleDto inputDto);
}
