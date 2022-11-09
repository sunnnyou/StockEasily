package de.throsenheim.unlimited.stockeasilyapi.service.article;

import de.throsenheim.unlimited.stockeasilyapi.dto.ArticleCreationDto;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Component
public class ArticleService implements IArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }


    @Override
    public Article create(ArticleCreationDto inputDto) {
        return this.articleRepository.save(toModel(inputDto));
    }

    private Article toModel(ArticleCreationDto inputDto) {
        Article result = new Article();
        result.setId(1); // TODO get from DB

        MultipartFile image = inputDto.getImage();
        Blob imageBlob = null;
        if (image != null) {
            try {
                imageBlob = new SerialBlob(image.getBytes());
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        result.setImage(imageBlob);
        result.setName(inputDto.getName());
        result.setProperties(inputDto.getProperties());
        result.setCategory(inputDto.getCategory());
        result.setQuantity(inputDto.getQuantity());
        return result;
    }
}
