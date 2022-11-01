package de.throsenheim.unlimited.stockeasilyapi.service;

import de.throsenheim.unlimited.stockeasilyapi.dto.ArticleCreationDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;

public interface IArticlesService {
    Article create(ArticleCreationDto creationDto);
}
