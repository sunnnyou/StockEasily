package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.LogUtil;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.CommittedSqlCommand;
import de.throsenheim.unlimited.stockeasilyapi.exception.NotImplementedException;
import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.ArticleProperty;
import de.throsenheim.unlimited.stockeasilyapi.model.Category;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository implements HumaneRepository<Article, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleRepository.class);

    private final SqlConnection connection;

    private final PropertyRepository propertyRepository;

    private final CategoryRepository categoryRepository;

    private final ArticlePropertyRepository articlePropertyRepository;

    @Autowired
    public ArticleRepository(DatabaseConnectionFactory databaseConnectionFactory,
                             PropertyRepository propertyRepository,
                             CategoryRepository categoryRepository,
                             ArticlePropertyRepository articlePropertyRepository) {
        this.connection = databaseConnectionFactory.getConnection(false, ArticleRepository.class, Article.class, LOGGER);
        this.propertyRepository = propertyRepository;
        this.categoryRepository = categoryRepository;
        this.articlePropertyRepository = articlePropertyRepository;
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
        if (category != null && category.getName() != null) {
            category = categoryRepository.save(category, true);
        }
        article.setCategory(category);

        List<Property> properties = article.getProperties();
        if (properties != null) {
            if (properties.size() > 0) {
                Iterable<Property> resultProperties = propertyRepository.saveAll(properties);
                properties = new LinkedList<>();
                resultProperties.forEach(properties::add);
            }
            article.setProperties(properties);
        }

        Article result = insert(article, commit);
        if (result == null) {
            return null;
        }
        List<ArticleProperty> articlePropertyRelations = getArticlePropertyRelations(result);
        articlePropertyRepository.saveAll(articlePropertyRelations);
        return result;
    }

    @Override
    public Iterable<Article> saveAll(Iterable<Article> articles) {
        // TODO currently unused?
//        for (Article article : articles) {
//            save(article);
//        }
//        return null;
        throw new NotImplementedException();
    }

    private Article insert(Article article, boolean commit) {
        PreparedStatement preparedStatement = null;
        final Long categoryId = article.getCategory() == null ? null : article.getCategory().getId();
        final String query = "INSERT INTO " +
                "articles(name,quantity,image" + (categoryId == null ? "" : ",categoryId") + ") " +
                "VALUES (?,?,?" + (categoryId == null ? "" : ",?") + ")";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, article.getName());
            preparedStatement.setInt(2, article.getQuantity());
            preparedStatement.setBlob(3, article.getImage()); // TODO implement image upload
            if (categoryId != null) {
                preparedStatement.setLong(4, categoryId);
            }

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            if (preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    if (commit) {
                        this.connection.commit(CommittedSqlCommand.INSERT);
                    }
                    article.setId(resultSet.getLong("insert_id"));
                    LogUtil.traceFetchId(Article.class, article.getId(), LOGGER);
                    return article;
                }
            }
            LOGGER.debug("Returning empty Article model");
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    private List<ArticleProperty> getArticlePropertyRelations(Article article) {
        List<ArticleProperty> result = new LinkedList<>();
        for (Property property : article.getProperties()) {
            ArticleProperty relation = new ArticleProperty();
            relation.setArticleId(article.getId());
            relation.setPropertyId(property.getId());
            result.add(relation);
        }
        return result;
    }
}
