package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.CommittedSqlCommand;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.LogUtil;
import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.UserArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class UserArticleRepository implements HumaneRepository<UserArticle, Long>, RelationRepository<UserArticle, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserArticleRepository.class);

    private final SqlConnection connection;

    @Autowired
    public UserArticleRepository(DatabaseConnectionFactory databaseConnectionFactory) {
        this.connection = databaseConnectionFactory.getConnection(false, UserArticleRepository.class, UserArticle.class, LOGGER);
    }

    @Override
    public Iterable<UserArticle> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<UserArticle> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public UserArticle findByName(String name) {
        return null;
    }

    @Override
    public Iterable<UserArticle> saveAll(Iterable<UserArticle> relations) {
        for (UserArticle relation : relations) {
            save(relation);
        }
        connection.commit(CommittedSqlCommand.INSERT);
        return relations;
    }

    @Override
    public UserArticle save(UserArticle relation) {
        return save(relation, false);
    }

    @Override
    public UserArticle save(UserArticle relation, boolean commit) {
        final String className = UserArticle.class.getSimpleName();
        if (exists(relation)) {
            LOGGER.debug("Using existing {} relation instead of saving: {}", className, relation);
            return relation;
        }

        LOGGER.debug("Saving new {} relation: {}", className, relation);
        final UserArticle result = insert(relation, commit);
        if (result != null) {
            LOGGER.info("Saved {} relation", className);
            return result;
        }
        LOGGER.error("Could not save {} relation {}", className, relation);
        return null;
    }

    private UserArticle insert(UserArticle relation, boolean commit) {
        PreparedStatement preparedStatement = null;
        final String query = "INSERT INTO users_articles(userId,articleId) VALUES (?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, relation.getUserId());
            preparedStatement.setLong(2, relation.getArticleId());

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            if (preparedStatement.executeUpdate() == 1) {
                if (commit) {
                    this.connection.commit(CommittedSqlCommand.INSERT);
                }
                return relation;
            }
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(UserArticle relation) {
        return find(relation) != null;
    }

    @Override
    public UserArticle find(UserArticle relation) {
        PreparedStatement preparedStatement = null;
        final String query = "SELECT EXISTS(" +
                "SELECT 1 " +
                "FROM stockeasily.users_articles " +
                "WHERE userId = ? " +
                "AND articleId = ?" +
                ")";
        String errorMessage = "Could not insert " + UserArticle.class.getSimpleName() + " relations";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, relation.getUserId());
            preparedStatement.setLong(2, relation.getArticleId());

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return relation;
            }
            LOGGER.error(errorMessage);
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }
}
