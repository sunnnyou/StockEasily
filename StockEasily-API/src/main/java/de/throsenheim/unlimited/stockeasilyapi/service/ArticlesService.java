package de.throsenheim.unlimited.stockeasilyapi.service;

import de.throsenheim.unlimited.stockeasilyapi.dto.ArticleCreationDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService implements IArticlesService {

    @Override
    public Article create(ArticleCreationDto inputDto) {
        Article result = new Article();
        result.setId(1); // TODO get from DB
        result.setName(inputDto.getName());
        result.setProperties(inputDto.getProperties());
        return result;
    }

}
