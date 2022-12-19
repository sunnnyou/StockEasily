package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.dto.response.CreateArticleResponseDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

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
    public ObjectError getImageValidationError() {
        return new ObjectError("image", "Image size must be <= " + maxImageSize);
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
    public List<Article> searchAll() {
        return articleRepository.findAll();
    }

    @Override
    public boolean validateImage(byte[] data) {
        return data.length <= maxImageSize;
    }
}
