package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.CommittedSqlCommand;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.LogUtil;
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepository implements HumaneRepository<Article, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleRepository.class);
    private static final String EMPTY_ARTICLE_LOG = "Returning empty Article model";
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
        return Optional.ofNullable(selectId(aLong));
    }

    @Override
    public Article findByName(String name) {
        return selectName(name);
    }

    public List<Article> findAllByName(String name) {
        return selectAllName(name);
    }

    public List<Article> findAllPage(int limit, int page) {
        return selectAllPage(limit, page);
    }

    @Override
    public Article save(Article article) {
        return save(article, true);
    }

    public int getSize() {
        return count();
    }

    public int getSizeQuery(String query) {
        return countQuery(query);
    }
    public List<Article> findAllByQuery(String query, int limit, int page) {
        return selectAllQuery(query, limit, page);
    }

    private int count() {
        PreparedStatement preparedStatement = null;
        final String query = "SELECT COUNT(*) as size FROM articles;";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("size");
            }
            LOGGER.debug("Error with getting table size");
            return 0;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    private int countQuery(String searchQuery) {
        PreparedStatement preparedStatement = null;
        final String query = "SELECT COUNT(*) as size FROM articles where id like ? OR name like ? OR categoryId IN (SELECT id FROM categories WHERE name LIKE ?)";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            searchQuery = "%" + searchQuery + "%";
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("size");
            }
            LOGGER.debug("Error with getting table size with query");
            return 0;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
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
            if (!properties.isEmpty()) {
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
        final Long categoryId = article.getCategory().getId();
        final String query = "INSERT INTO " +
                "articles(name,quantity,image,categoryId) " +
                "VALUES (?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, article.getName());
            preparedStatement.setInt(2, article.getQuantity());
            preparedStatement.setBlob(3, article.getImage());
            preparedStatement.setLong(4, categoryId);

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
            LOGGER.debug(EMPTY_ARTICLE_LOG);
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    public Article selectId(long id) {
        PreparedStatement preparedStatement = null;
        final String query = "select name, quantity, image, categoryId from articles where id = ?";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, id);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Article article = new Article();
                article.setId(id);
                article.setName(resultSet.getString("name"));
                setQuantityImagePropertiesCategories(article, resultSet);
                return article;
            }
            LOGGER.debug(EMPTY_ARTICLE_LOG);
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    private Article selectName(String name) {
        PreparedStatement preparedStatement = null;
        final String query = "select id, quantity, image, categoryId from articles where name = ?";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getLong("id"));
                article.setName(name);
                setQuantityImagePropertiesCategories(article, resultSet);
                return article;
            }
            LOGGER.debug(EMPTY_ARTICLE_LOG);
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    private List<Article> selectAllName(String name) {
        PreparedStatement preparedStatement = null;
        final String query = "select id, quantity, image, categoryId from articles where name = ?";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Article> articleList = new ArrayList<>(resultSet.getFetchSize());

            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getLong("id"));
                article.setName(name);
                setQuantityImagePropertiesCategories(article, resultSet);
                articleList.add(article);
            }
            LOGGER.debug("Returning article list with size {}", articleList.size());
            return articleList;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    private List<Article> selectAllPage(int limit, int page){
        page = page - 1;
        PreparedStatement preparedStatement = null;
        final String query = "select id, name, quantity, image, categoryId from articles LIMIT ?, ?";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, (page*limit));
            preparedStatement.setInt(2, limit);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Article> articleList = new ArrayList<>(resultSet.getFetchSize());

            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getLong("id"));
                article.setName(resultSet.getString("name"));
                setQuantityImagePropertiesCategories(article, resultSet);
                articleList.add(article);
            }
            LOGGER.debug("Returning article list with size {}", articleList.size());
            return articleList;
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

    public List<Article> findAll() {
        PreparedStatement preparedStatement = null;
        final String query = "select id, name, quantity, image, categoryId from articles";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Article> articleList = new ArrayList<>(resultSet.getFetchSize());

            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getLong("id"));
                article.setName(resultSet.getString("name"));
                setQuantityImagePropertiesCategories(article, resultSet);
                articleList.add(article);
            }
            LOGGER.debug("Returning article list with size {}", articleList.size());
            return articleList;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    private void setQuantityImagePropertiesCategories(Article article, ResultSet resultSet) throws SQLException {
        article.setQuantity(resultSet.getInt("quantity"));
        article.setImage(resultSet.getBlob("image"));

        // Setting category
        Optional<Category> categoryOptional = categoryRepository.findById(resultSet.getLong("categoryId"));
        categoryOptional.ifPresent(article::setCategory);

        // Setting properties
        List<Long> propertyIdList = articlePropertyRepository.findAllByArticleId(article.getId());
        List<Property> properties = new ArrayList<>(propertyIdList.size());
        for (long propertyId : propertyIdList) {
            Optional<Property> propertyOptional = propertyRepository.findById(propertyId);
            propertyOptional.ifPresent(properties::add);
        }
        article.setProperties(properties);

        LogUtil.traceFetchId(Article.class, article.getId(), LOGGER);
    }


    private List<Article> selectAllQuery(String searchQuery, int limit, int page) {
        page = page - 1;
        PreparedStatement preparedStatement = null;
        final String query = "select id, name, quantity, image, categoryId from articles where id like ? OR name like ? OR categoryId IN (SELECT id FROM categories WHERE name LIKE ?) LIMIT ?, ?";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            searchQuery = "%" + searchQuery + "%";
            preparedStatement.setString(1, searchQuery);
            preparedStatement.setString(2, searchQuery);
            preparedStatement.setString(3, searchQuery);
            preparedStatement.setInt(4, (page*limit));
            preparedStatement.setInt(5, limit);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Article> articleList = new ArrayList<>(resultSet.getFetchSize());

            while (resultSet.next()) {
                Article article = new Article();
                article.setId(resultSet.getLong("id"));
                article.setName(resultSet.getString("name"));
                setQuantityImagePropertiesCategories(article, resultSet);
                articleList.add(article);
            }
            LOGGER.debug("Returning article list with size {}", articleList.size());
            return articleList;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    public int delete(long articleId) {
        PreparedStatement preparedStatementArticlesProperties = null;
        PreparedStatement preparedStatementUsersArticles = null;
        PreparedStatement preparedStatement = null;
        final String articles_properties_query = "delete from articles_properties where articleId=?";
        final String users_articles_query = "delete from users_articles where articleId=?";
        final String query = "delete from articles where id=?";

        try {
            preparedStatementArticlesProperties = connection.prepareStatement(articles_properties_query, Statement.RETURN_GENERATED_KEYS);
            preparedStatementUsersArticles = connection.prepareStatement(users_articles_query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatementArticlesProperties.setLong(1, articleId);
            preparedStatementUsersArticles.setLong(1, articleId);
            preparedStatement.setLong(1, articleId);

            LogUtil.traceSqlStatement(preparedStatementArticlesProperties, LOGGER);
            LogUtil.traceSqlStatement(preparedStatementUsersArticles, LOGGER);
            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            LOGGER.debug("Affected Rows in delete query for articles_properties: {}", preparedStatementArticlesProperties.executeUpdate());
            LOGGER.debug("Affected Rows in delete query for users_articles: {}", preparedStatementUsersArticles.executeUpdate());
            int affectedRowsArticles = preparedStatement.executeUpdate();
            LOGGER.debug("Affected Rows in delete query for articles: {}", affectedRowsArticles);
            this.connection.commit(CommittedSqlCommand.DELETE);
            return affectedRowsArticles;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatementArticlesProperties, LOGGER, e);
            LogUtil.errorSqlStatement(preparedStatementUsersArticles, LOGGER, e);
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }
}
