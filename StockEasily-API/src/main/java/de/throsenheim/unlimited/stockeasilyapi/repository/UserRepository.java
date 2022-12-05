package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.CommittedSqlCommand;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.LogUtil;
import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Article;
import de.throsenheim.unlimited.stockeasilyapi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository implements HumaneRepository<User, Long>{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private final ArticleRepository articleRepository;
    private final SqlConnection connection;

    @Autowired
    public UserRepository(DatabaseConnectionFactory databaseConnectionFactory, PropertyRepository propertyRepository, CategoryRepository categoryRepository, ArticlePropertyRepository articlePropertyRepository, ArticleRepository articleRepository) {
        this.articleRepository = new ArticleRepository(databaseConnectionFactory, propertyRepository, categoryRepository, articlePropertyRepository);
        this.connection = databaseConnectionFactory.getConnection(false, UserRepository.class, User.class, LOGGER);
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public User findByName(String name) {
        return null;
    }

    @Override
    public Iterable<User> saveAll(Iterable<User> entities) {
        return null;
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User save(User user, boolean commit) {
        List<Article> articleList = user.getArticles();
        List<Article> articleResult = new ArrayList<>();
        for(Article article: articleList) {
            articleResult.add(articleRepository.save(article));
        }
        User result = insert(user, commit);
        result.setArticles(articleResult);
        return result;
    }

    public User insert(User user, boolean commit) {
        PreparedStatement preparedStatement = null;
        final String query = "INSERT INTO " +
                "articles(emailAddress,password,isNotified,loginDate"  + ") " +
                "VALUES (?,?,?,?" + ")";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getEmailAddress());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isNotified());

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            if (preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    if (commit) {
                        connection.commit(CommittedSqlCommand.INSERT);
                    }
                    user.setId(resultSet.getLong("insert_id"));
                    LogUtil.traceFetchId(User.class, user.getId(), LOGGER);
                    return user;
                }
            }
            LOGGER.debug("Returning empty User model");
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }
}
