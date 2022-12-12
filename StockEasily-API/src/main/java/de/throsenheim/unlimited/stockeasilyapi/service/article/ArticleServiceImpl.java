package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.request.CreateArticleRequestDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @Override
    public Article create(CreateArticleRequestDto request) {
        return this.articleRepository.save(new Article(request));
    }

    @Override
    public Optional<Article> search(long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article searchByName(String name) {
        return articleRepository.findByName(name);
    }

    @Override
    public List<Article> searchAllByName(String name) {
        return articleRepository.findAllByName(name);
    }
}
