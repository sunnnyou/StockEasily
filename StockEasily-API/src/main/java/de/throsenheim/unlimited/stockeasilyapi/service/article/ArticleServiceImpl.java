package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CategoryRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.request.PropertyRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.request.UpdateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.GetArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.UpdateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ArticleServiceImpl implements ArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    private long maxImageSize;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, @Value("${article.image.maxsize}") long maxImageSize) {
        this.articleRepository = articleRepository;
        this.maxImageSize = maxImageSize;
    }

    @Override
    public CreateArticleResponseDto create(CreateArticleRequestDto request) {
        String image = request.getImage();
        byte[] decodedBytes = null;
        if (image != null) {
            image = image.substring(image.indexOf(',') + 1);
            decodedBytes = Base64.getDecoder().decode(image);
            if (!this.validateImage(decodedBytes)) {
                return new CreateArticleResponseDto();
            }
        }
        final Article article = new Article(request, decodedBytes);
        Article saveResult = this.articleRepository.save(article);
        return saveResult == null ? null : new CreateArticleResponseDto(article);
    }

    @Override
    public FieldError getImageFieldError() {
        return new FieldError("image", "image", "Image size must be <= " + maxImageSize);
    }

    @Override
    @Nullable
    public GetArticleResponseDto search(long id) {
        Optional<Article> result = articleRepository.findById(id);
        if (result != null && result.isPresent()) {
            return new GetArticleResponseDto(result.get());
        }
        return null;
    }

    @Override
    public List<GetArticleResponseDto> searchAllByName(String name) {
        List<Article> articles = articleRepository.findAllByName(name);
        List<GetArticleResponseDto> result = new ArrayList<>(articles.size());
        for (Article article : articles) {
            result.add(new GetArticleResponseDto(article));
        }
        return result;
    }

    @Override
    public List<GetArticleResponseDto> searchAll() {
        final List<Article> articleList = articleRepository.findAll();
        final List<GetArticleResponseDto> result = new ArrayList<>();
        for (Article article : articleList) {
            result.add(new GetArticleResponseDto(article));
        }
        return result;
    }

    @Override
    public List<GetArticleResponseDto> searchAllPage(int limit, int page) {
        final List<Article> articleList = articleRepository.findAllPage(limit, page);
        final List<GetArticleResponseDto> result = new ArrayList<>();
        for (Article article : articleList) {
            result.add(new GetArticleResponseDto(article));
        }
        return result;
    }

    @Override
    public boolean validateImage(byte[] data) {
        return data.length <= maxImageSize;
    }

    @Override
    public int getArticleRepositorySize() {
        return articleRepository.getSize();
    }

    @Override
    @Nullable
    public Long getParsedIdOrNull(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<GetArticleResponseDto> searchAllByQuery(String query, int limit, int page) {
        final List<Article> articleList = articleRepository.findAllByQuery(query, limit, page);
        final List<GetArticleResponseDto> articleResponseList = new ArrayList<>();
        for (Article article : articleList) {
            articleResponseList.add(new GetArticleResponseDto(article));
        }
        return articleResponseList;
    }

    @Override
    public int getArticleRepositorySizeQuery(String query) {
        return articleRepository.getSizeQuery(query);
    }

    @Override
    public Optional<Integer> deleteArticle(long articleId) {
        return Optional.of(articleRepository.delete(articleId));
    }

    @Override
    public UpdateArticleResponseDto update(UpdateArticleRequestDto request, @NonNull GetArticleResponseDto existingArticle) {
        Article updatedArticle = existingArticle.toModel();

        final CategoryRequestDto category = request.getCategory();
        if (category != null) {
            updatedArticle.setCategory(category.toModel());
        }

        final List<PropertyRequestDto> properties = request.getProperties();
        if (properties != null && properties.size() > 0) {
            updatedArticle.setProperties(properties.stream().map(PropertyRequestDto::toModel).collect(Collectors.toList()));
        }

        final int quantity = request.getQuantity();
        if (quantity >= 0) {
            updatedArticle.setQuantity(quantity);
        }

        final String name = request.getName();
        if (name != null) {
            updatedArticle.setName(name);
        }

        final String image = request.getImage();
        if (image != null) {
            final String previousValue = updatedArticle.getImage() == null ? "NULL" : "";
            updatedArticle.setImage(image);
            LOGGER.debug("Updating article with id, set image {} -> {}", previousValue, image);
        }

        Article articleUpdated = this.articleRepository.save(updatedArticle);
        if (articleUpdated == null) {
            LOGGER.error("Could not update article with id {}", updatedArticle.getId());
            return null;
        }
        return new UpdateArticleResponseDto(articleUpdated);

    }
}
