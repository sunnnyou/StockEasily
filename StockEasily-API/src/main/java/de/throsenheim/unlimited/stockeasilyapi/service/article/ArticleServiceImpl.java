package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.SearchArticleResponse;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.repository.ArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Component
public class ArticleServiceImpl implements ArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    private byte[] decodeImage(final String image) {
        if (image == null) {
            return null;
        }
        String rawImage = image.substring(image.indexOf(',') + 1);

        try {
            return Base64.getDecoder().decode(rawImage);
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Could not decode image via base64, argument is invalid");
            return null;
        }
    }

    @Override
    public CreateArticleResponseDto create(CreateArticleRequestDto request) {
        final Article article = new Article(request, decodeImage(request.getImage()));
        Article saveResult = this.articleRepository.save(article);
        return saveResult == null ? null : new CreateArticleResponseDto(article);
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
