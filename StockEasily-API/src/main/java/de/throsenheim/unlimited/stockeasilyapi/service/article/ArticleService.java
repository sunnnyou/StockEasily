package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.GetArticleResponseDto;
import org.springframework.validation.FieldError;

import java.util.List;

public interface ArticleService {
    CreateArticleResponseDto create(CreateArticleRequestDto request);

    FieldError getImageFieldError();

    GetArticleResponseDto search(long id);

    List<GetArticleResponseDto> searchAllByName(String name);

    List<GetArticleResponseDto> searchAll();

    List<GetArticleResponseDto> searchAllPage(int limit, int page);

    boolean validateImage(byte[] data);

    int getArticleRepositorySize();

    List<GetArticleResponseDto> searchAllByQuery(String query, int limit, int page);

    int getArticleRepositorySizeQuery(String query);
}
