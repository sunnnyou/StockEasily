package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.LogUtil;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.CommittedSqlCommand;
import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Optional;

@Component
public class CategoryRepository implements HumaneRepository<Category, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRepository.class);

    private final SqlConnection connection;

    @Autowired
    public CategoryRepository(DatabaseConnectionFactory databaseConnectionFactory) {
        this.connection = databaseConnectionFactory.getConnection(false, CategoryRepository.class, Category.class, LOGGER);
    }

    @Override
    public Iterable<Category> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<Category> findById(Long aLong) {
        return Optional.ofNullable(selectId(aLong));
    }

    @Override
    public Category findByName(String name) {
        PreparedStatement preparedStatement = null;
        final String query = "SELECT id FROM categories WHERE name = ? LIMIT 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            Category result = null;
            if (resultSet.next()) {
                result = new Category();
                result.setId(resultSet.getInt(1));
                result.setName(name);
            } else {
                LOGGER.debug("Could not find category with name " + name);
            }
            return result;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Category> saveAll(Iterable<Category> entities) {
        return null;
    }

    @Override
    public Category save(Category category) {
        return save(category, true);
    }

    @Override
    public Category save(Category category, boolean commit) {
        final Category resultFound = findByName(category.getName());
        if (resultFound == null) {
            LOGGER.debug("Inserting new category");
            return insert(category, commit);
        }
        LOGGER.debug("Using existing category with ID " + resultFound.getId() + " instead of saving");
        return resultFound;
    }

    private Category insert(Category category, boolean commit) {
        PreparedStatement preparedStatement = null;
        final String query = "INSERT INTO categories(name) VALUES (?)";
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, category.getName());

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            if (preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    if (commit) {
                        this.connection.commit(CommittedSqlCommand.INSERT);
                    }
                    category.setId(resultSet.getLong("insert_id"));
                    LogUtil.traceFetchId(Category.class, category.getId(), LOGGER);
                    return category;
                }
            }
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    public Category selectId(long id) {
        PreparedStatement preparedStatement = null;
        final String query = "SELECT name FROM categories WHERE id = ? LIMIT 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            Category result = null;
            if (resultSet.next()) {
                result = new Category();
                result.setId(id);
                result.setName(resultSet.getString("name"));
            } else {
                LOGGER.debug("Could not find category with id {}", id);
            }
            return result;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }
}
