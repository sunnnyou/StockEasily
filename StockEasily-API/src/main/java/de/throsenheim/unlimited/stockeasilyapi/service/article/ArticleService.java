package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.ArticleCreationDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;

public interface ArticleService {
    Article create(ArticleCreationDto inputDto);
}
