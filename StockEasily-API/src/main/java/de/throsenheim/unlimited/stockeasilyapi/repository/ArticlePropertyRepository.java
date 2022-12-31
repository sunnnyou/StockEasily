package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.LogUtil;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.CommittedSqlCommand;
import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.ArticleProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ArticlePropertyRepository implements HumaneRepository<ArticleProperty, Long>, RelationRepository<ArticleProperty, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticlePropertyRepository.class);

    private final SqlConnection connection;

    @Autowired
    public ArticlePropertyRepository(DatabaseConnectionFactory databaseConnectionFactory) {
        this.connection = databaseConnectionFactory.getConnection(false, ArticlePropertyRepository.class, ArticleProperty.class, LOGGER);
    }

    @Override
    public Iterable<ArticleProperty> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<ArticleProperty> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public ArticleProperty findByName(String name) {
        return null;
    }

    @Override
    public Iterable<ArticleProperty> saveAll(Iterable<ArticleProperty> relations) {
        boolean shouldCommit = false;
        for (ArticleProperty relation : relations) {
            if (save(relation) != null && !shouldCommit) {
                shouldCommit = true;
            }
        }
        if (shouldCommit) {
            connection.commit(CommittedSqlCommand.INSERT);
        }
        return relations;
    }

    @Override
    public ArticleProperty save(ArticleProperty relation) {
        return save(relation, false);
    }

    @Override
    public ArticleProperty save(ArticleProperty relation, boolean commit) {
        final String className = ArticleProperty.class.getSimpleName();
        if (exists(relation)) {
            LOGGER.debug("Using existing " + className + " relation instead of saving: " + relation.toString());
            return relation;
        }

        LOGGER.debug("Saving new " + className + " relation: " + relation.toString());
        final ArticleProperty result = insert(relation, commit);
        if (result != null) {
            LOGGER.info("Saved " + className + " relation");
            return result;
        }
        LOGGER.error("Could not save " + className + " relation " + relation);
        return null;
    }

    private ArticleProperty insert(ArticleProperty relation, boolean commit) {
        PreparedStatement preparedStatement = null;
        final String query = "INSERT INTO articles_properties(articleId,propertyId) VALUES (?,?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, relation.getArticleId());
            preparedStatement.setLong(2, relation.getPropertyId());

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
    public boolean exists(ArticleProperty relation) {
        return find(relation) != null;
    }

    @Override
    public ArticleProperty find(ArticleProperty relation) {
        PreparedStatement preparedStatement = null;
        final String query = "SELECT EXISTS(" +
                "SELECT 1 " +
                "FROM articles_properties " +
                "WHERE articleId = ? " +
                "AND propertyId = ?" +
                ") AS 'exists'";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, relation.getArticleId());
            preparedStatement.setLong(2, relation.getPropertyId());

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getLong("exists") == 1) {
                return relation;
            }
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    public List<Long> findAllByArticleId(long articleId) {
        final String query = "SELECT propertyId FROM articles_properties WHERE articleId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, articleId);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Long> propertyIdList = new ArrayList<>(resultSet.getFetchSize());
            while (resultSet.next()) {
                long propertyId = resultSet.getLong("propertyId");
                propertyIdList.add(propertyId);
            }
            LOGGER.info("Retrieved articleProperty list from data bank with size: {}", propertyIdList.size());
            return propertyIdList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
