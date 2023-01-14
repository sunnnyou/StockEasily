package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.GetArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.*;

@Component
public class ArticleServiceImpl implements ArticleService {

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
}
