package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.SearchArticleResponse;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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
    public Optional<Article> search(long id) {
        return articleRepository.findById(id);
    }

    @Override
    public List<Article> searchAllByName(String name) {
        return articleRepository.findAllByName(name);
    }

    @Override
    public List<SearchArticleResponse> searchAll() {
        final List<Article> articleList = articleRepository.findAll();
        final List<SearchArticleResponse> articleResponseList = new ArrayList<>();
        for(Article article : articleList) {
            articleResponseList.add(new SearchArticleResponse(article));
        }
        return articleResponseList;
    }

    @Override
    public List<SearchArticleResponse> searchAllPage(int limit, int page) {
        final List<Article> articleList = articleRepository.findAllPage(limit, page);
        final List<SearchArticleResponse> articleResponseList = new ArrayList<>();
        for(Article article : articleList) {
            articleResponseList.add(new SearchArticleResponse(article));
        }
        return articleResponseList;
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
    public List<SearchArticleResponse> searchAllByQuery(String query, int limit, int page) {
        final List<Article> articleList = articleRepository.findAllByQuery(query, limit, page);
        final List<SearchArticleResponse> articleResponseList = new ArrayList<>();
        for(Article article : articleList) {
            articleResponseList.add(new SearchArticleResponse(article));
        }
        return articleResponseList;
    }

    @Override
    public int getArticleRepositorySizeQuery(String query) {
        return articleRepository.getSizeQuery(query);
    }
}
