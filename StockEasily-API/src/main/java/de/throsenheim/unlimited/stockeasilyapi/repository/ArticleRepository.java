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
        Category category = article.getCategory();
        if (category != null) {
            category = categoryRepository.save(category);
            article.setCategory(category);
        }

        List<Property> properties = article.getProperties();
        if (properties != null) {
            if (properties.size() > 0) {
                properties = (ArrayList<Property>) propertyRepository.saveAll(properties);
            }
            article.setProperties(properties);
        }

        return insertArticle(article);
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
            final Long categoryId = article.getCategory() == null ? null : article.getCategory().getId();
            final StringBuilder queryBuilder = new StringBuilder("INSERT INTO articles(name,quantity,image");
            queryBuilder.append(categoryId == null ? "" : ",categoryId");
            queryBuilder.append(") VALUES (?,?,?");
            queryBuilder.append(categoryId == null ? "" : ",?");
            queryBuilder.append(")");
            PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, article.getName());
            preparedStatement.setInt(2, article.getQuantity());
            preparedStatement.setBlob(3, article.getImage()); // TODO implement image upload
            if (categoryId != null) {
                preparedStatement.setLong(4, categoryId);
            }
            if (preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
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
