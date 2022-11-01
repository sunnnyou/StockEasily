package de.throsenheim.unlimited.stockeasilyapi.service;

import de.throsenheim.unlimited.stockeasilyapi.dto.ArticleCreationDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;

public class ArticlesService implements IArticlesService {

    @Override
    public Article create(ArticleCreationDto creationDto) {
        Article result = new Article();
        result.setId(1); // TODO get from DB
        result.setName(creationDto.getName());
        result.setProperties(creationDto.getProperties());
        return result;
    }

}
