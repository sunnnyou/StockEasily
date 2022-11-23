package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.ArticleProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Optional;

@Component
public class ArticlePropertyRepository implements HumaneRepository<ArticleProperty, Long>, RelationRepository<ArticleProperty, Long> {

    private Connection connection;

    @Autowired
    public ArticlePropertyRepository(DatabaseConnectionFactory databaseConnectionFactory) {
        this.connection = databaseConnectionFactory.getConnection(false);
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
        for (ArticleProperty relation : relations) {
            save(relation);
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return relations;
    }

    @Override
    public ArticleProperty save(ArticleProperty relation) {
        return save(relation, false);
    }

    @Override
    public ArticleProperty save(ArticleProperty relation, boolean commit) {
        return exists(relation) ? relation : insert(relation, commit);
    }

    private ArticleProperty insert(ArticleProperty relation, boolean commit) {
        try {
            final String query = "INSERT INTO articles_properties(articleId,propertyId) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, relation.getArticleId());
            preparedStatement.setLong(2, relation.getPropertyId());

            if (preparedStatement.executeUpdate() == 1) {
                if (commit) {
                    this.connection.commit();
                }
                return relation;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(ArticleProperty relation) {
        return find(relation) != null;
    }

    @Override
    public ArticleProperty find(ArticleProperty relation) {
        try {
            final String query = "SELECT EXISTS(" +
                    "SELECT 1 " +
                    "FROM stockeasily.articles_properties " +
                    "WHERE articleId = ? " +
                    "AND propertyId = ?" +
                    ")";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, relation.getArticleId());
            preparedStatement.setLong(2, relation.getPropertyId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getBoolean(1)) {
                return relation;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
