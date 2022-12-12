package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.CommittedSqlCommand;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.LogUtil;
import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class UserRepository implements HumaneRepository<User, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);

    private final ArticleRepository articleRepository;
    private final UserArticleRepository userArticleRepository;
    private final SqlConnection connection;

    @Autowired
    public UserRepository(DatabaseConnectionFactory databaseConnectionFactory, ArticleRepository articleRepository, UserArticleRepository userArticleRepository) {
        this.articleRepository = articleRepository;
        this.connection = databaseConnectionFactory.getConnection(false, UserRepository.class, User.class, LOGGER);
        this.userArticleRepository = userArticleRepository;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.ofNullable(selectId(aLong, true));
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
        return save(entity, true);
    }

    public User update(User entity, long id) {
        return update(entity, id, true);
    }

    @Override
    public User save(User user, boolean commit) {
//        List<Article> articleList = user.getArticles();
//
//
//        if (articleList != null) {
//            List<Article> articleResult = new LinkedList<>();
//            if (!articleList.isEmpty()) {
//                for (Article article : articleList) {
//                    articleResult.add(articleRepository.save(article));
//                }
//            }
//            user.setArticles(articleResult);
//        }

        User result = insert(user, commit);
//        if (result == null) {
//            return null;
//        }
//        List<UserArticle> userArticleRelations = getUserArticleRelations(result);
//        userArticleRepository.saveAll(userArticleRelations);
        return result;
    }

    public User insert(User user, boolean commit) {
        PreparedStatement preparedStatement = null;
        final String query = "INSERT INTO " + "users(emailAddress,password,isNotified,loginDate) " + "VALUES (?,?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getEmailAddress());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isNotified());
            preparedStatement.setDate(4, new Date(user.getLoginDate().getTime()));

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

    public User update(User user, long id, boolean commit) {
        PreparedStatement preparedStatement = null;
        final String query = "UPDATE users SET emailAddress = ?, password = ?, isNotified = ?, loginDate = ? WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query, 0);

            preparedStatement.setString(1, user.getEmailAddress());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.isNotified());
            preparedStatement.setTimestamp(4, user.getLoginDate());
            preparedStatement.setLong(5, id);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            if (preparedStatement.executeUpdate() != 0) {
                if (commit) {
                    connection.commit(CommittedSqlCommand.INSERT);
                }
                user.setId(id);
                return user;
            }

            LOGGER.debug("Returning empty User model");
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    public User selectId(long id, boolean commit) {
        PreparedStatement preparedStatement = null;
        final String query = "select id, emailAddress, password, isNotified from users where id = " + id;

        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (commit) {
                    connection.commit(CommittedSqlCommand.SELECT);
                }
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setEmailAddress(resultSet.getString("emailAddress"));
                user.setPassword(resultSet.getString("password"));
                LogUtil.traceFetchId(User.class, user.getId(), LOGGER);
                return user;
            }
            LOGGER.debug("Returning empty User model");
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

//    private List<UserArticle> getUserArticleRelations(User user) {
//        List<UserArticle> result = new LinkedList<>();
//        for (Article article : user.getArticles()) {
//            UserArticle relation = new UserArticle();
//            relation.setUserId(user.getId());
//            relation.setArticleId(article.getId());
//            result.add(relation);
//        }
//        return result;
//    }
}
