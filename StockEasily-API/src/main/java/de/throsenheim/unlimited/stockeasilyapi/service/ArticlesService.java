package de.throsenheim.unlimited.stockeasilyapi.service;

import de.throsenheim.unlimited.stockeasilyapi.dto.ArticleCreationDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;

public class ArticlesService implements IArticlesService {

    @Override
    public Article create(ArticleCreationDto creationDto) {
        return new Article();
    }

}
