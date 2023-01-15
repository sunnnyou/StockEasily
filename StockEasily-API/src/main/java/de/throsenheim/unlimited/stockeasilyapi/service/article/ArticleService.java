package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.request.UpdateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.GetArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.UpdateArticleResponseDto;
import org.springframework.validation.FieldError;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ArticleService {
    CreateArticleResponseDto create(CreateArticleRequestDto request);

    FieldError getImageFieldError();

    GetArticleResponseDto search(long id);

    List<GetArticleResponseDto> searchAllByName(String name);

    List<GetArticleResponseDto> searchAll();

    List<GetArticleResponseDto> searchAllPage(int limit, int page);

    boolean validateImage(byte[] data);

    int getArticleRepositorySize();

    Long getParsedIdOrNull(String id);

    List<GetArticleResponseDto> searchAllByQuery(String query, int limit, int page);

    int getArticleRepositorySizeQuery(String query);

    Optional<Integer> deleteArticle(long articleId);

    UpdateArticleResponseDto update(long id, UpdateArticleRequestDto request, @NotNull GetArticleResponseDto existingArticle);
}
