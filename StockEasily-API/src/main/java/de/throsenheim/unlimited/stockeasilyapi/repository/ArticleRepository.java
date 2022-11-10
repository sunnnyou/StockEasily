package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.Category;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository implements HumaneRepository<Article, Long> {

    private final Connection connection;
    private final PropertyRepository propertyRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ArticleRepository(DatabaseConnectionFactory databaseConnectionFactory,
                             PropertyRepository propertyRepository,
                             CategoryRepository categoryRepository) {
        this.connection = databaseConnectionFactory.getConnection(false);
        this.propertyRepository = propertyRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Iterable<Article> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<Article> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Article findByName(String name) {
        return null;
    }

    @Override
    public Article save(Article article) {
        return save(article, true);
    }

    @Override
    public Article save(Article article, boolean commit) {
        Category category = article.getCategory();
        if (category != null) {
            category = categoryRepository.save(category);
            article.setCategory(category);
        }

        List<Property> properties = article.getProperties();
        if (properties != null) {
            if (properties.size() > 0) {
                Iterable<Property> resultProperties = propertyRepository.saveAll(properties);
                properties = new LinkedList<>();
                resultProperties.forEach(properties::add);
            }
            article.setProperties(properties);
        }

        return insert(article, commit);
    }

    @Override
    public Iterable<Article> saveAll(Iterable<Article> articles) {
        for (Article article : articles) {
            save(article);
        }
        return null;
    }

    private Article insert(Article article, boolean commit) {
        try {
            final Long categoryId = article.getCategory() == null ? null : article.getCategory().getId();
            final String query = "INSERT INTO articles(name,quantity,image" + (categoryId == null ? "" : ",categoryId")
                    + ") VALUES (?,?,?" + (categoryId == null ? "" : ",?") + ")";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, article.getName());
            preparedStatement.setInt(2, article.getQuantity());
            preparedStatement.setBlob(3, article.getImage()); // TODO implement image upload
            if (categoryId != null) {
                preparedStatement.setLong(4, categoryId);
            }
            if (preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    if (commit) {
                        this.connection.commit();
                    }
                    article.setId(resultSet.getLong("insert_id"));
                    return article;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
