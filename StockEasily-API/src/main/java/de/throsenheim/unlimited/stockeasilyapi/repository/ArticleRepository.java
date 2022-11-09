package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.Category;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
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

////        try {
////            String table = "";
////            final String query = "SELECT * FROM articles WHERE id = ? LIMIT 1";
////            Statement statement = connection.createStatement();
////            ResultSet resultSet = statement.executeQuery(query);
////            if (resultSet.next()) {
////                Article result = new Article();
////                result.setId(id);
////                result.setName(resultSet.getString(""));
////                return
////            }
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }

    @Override
    public Article save(Article article) {
        Article result = insertArticle(article);
        // TODO use id

        Category category = article.getCategory();
        if (category != null) {
            category = categoryRepository.save(category);
            article.setCategory(category);
        }

        List<Property> properties = article.getProperties();
        if (properties != null) {
            properties = (ArrayList<Property>) propertyRepository.saveAll(article.getProperties());
            article.setProperties(properties);
        }

        return result;
    }

    @Override
    public Iterable<Article> saveAll(Iterable<Article> articles) {
        for (Article article : articles) {
            save(article);
        }
        return null;
    }

    private Article insertArticle(Article article) {
        try {
            final String query = "INSERT INTO articles(name,quantity,image) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, article.getName());
            preparedStatement.setInt(2, article.getQuantity());
            preparedStatement.setBlob(3, article.getImage()); // TODO implement image upload

            int success = preparedStatement.executeUpdate();
            if (success == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    article.setId(resultSet.getLong(1));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertCategory() {

    }

    private void insertProperties() {

    }
}
