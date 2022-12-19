package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    CreateArticleResponseDto create(CreateArticleRequestDto request);

    ObjectError getImageValidationError();

    Optional<Article> search(long id);

    List<Article> searchAllByName(String name);

    List<Article> searchAll();

    boolean validateImage(byte[] data);
}
