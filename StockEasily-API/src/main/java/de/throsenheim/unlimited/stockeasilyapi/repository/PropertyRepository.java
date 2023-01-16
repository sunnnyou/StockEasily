package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.LogUtil;
import de.throsenheim.unlimited.stockeasilyapi.common.logging.CommittedSqlCommand;
import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class PropertyRepository implements HumaneRepository<Property, Long> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyRepository.class);

    private final SqlConnection connection;

    @Autowired
    public PropertyRepository(DatabaseConnectionFactory databaseConnectionFactory) {
        this.connection = databaseConnectionFactory.getConnection(false, PropertyRepository.class, PropertyRepository.class, LOGGER);
    }

    @Override
    public boolean deleteAll(Iterable<Property> entities) {
        boolean result = true;
        for (Property entity : entities) {
            if (delete(entity)) {
                continue;
            }
            LOGGER.warn("Could not delete orphaned category with id {}", entity.getId());
            result = false;
        }
        return result;
    }

    @Override
    public boolean delete(Property entity) {
        return deleteById(entity.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        PreparedStatement preparedStatement = null;
        final String query = "DELETE FROM properties WHERE id = ? LIMIT 1";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);
            if (preparedStatement.executeUpdate() == 0) {
                LOGGER.error("Could not delete properties record with id {}", id);
                return false;
            }
            LOGGER.debug("Deleted properties record with id {}", id);
            this.connection.commit(CommittedSqlCommand.DELETE);
            return true;

        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Property> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<Property> findById(Long aLong) {
        return Optional.ofNullable(selectId(aLong));
    }

    private Optional<Property> find(Property property) {
        final String query = "SELECT id FROM properties WHERE name = ? and description = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, property.getName());
            preparedStatement.setString(2, property.getDescription());

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            Property result = null;
            if (resultSet.next()) {
                result = new Property();
                result.setId(resultSet.getInt("id"));
                result.setName(property.getName());
                result.setDescription(property.getDescription());
            }
            resultSet.close();
            preparedStatement.close();
            return Optional.ofNullable(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Property findByName(String name) {
        final String query = "SELECT id, description FROM properties WHERE name = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            Property result = null;
            if (resultSet.next()) {
                result = new Property();
                result.setId(resultSet.getInt("id"));
                result.setName(name);
                result.setDescription(resultSet.getString("description"));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Property> saveAll(Iterable<Property> properties) {
        List<Property> result = new LinkedList<>();
        for (Property property : properties) {
            Property resultProperty = save(property);
            if (resultProperty == null) {
                LOGGER.debug("Skipping property with ID {} on saveAll", property.getId());
                continue;
            }
            result.add(resultProperty);
        }
        if (result.size() > 0) {
            connection.commit(CommittedSqlCommand.INSERT);
        }
        return result;
    }

    @Override
    public Property save(Property property) {
        return save(property, false);
    }

    @Override
    public Property save(Property property, boolean commit) {
        Optional<Property> findResult = find(property);
        if (property.getId() > 0) {
            Optional<Property> propertyFound = findById(property.getId());
            if (propertyFound.isPresent()) {
                return propertyFound.get();
            }
        }

        if (result == null || !result.getDescription().equals(property.getDescription())) {
            result = insert(property, commit);
            LOGGER.debug("Saved new property with ID " + result.getId());
            return result;
        }
        LOGGER.debug("Using existing property with ID " + result.getId() + " instead of saving");
        return result;
    }

    private Property insert(Property property, boolean commit) {
        PreparedStatement preparedStatement = null;
        final String query = "INSERT INTO properties(name,description) VALUES (?,?)";
        try {
            preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, property.getName());
            preparedStatement.setString(2, property.getDescription());

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            if (preparedStatement.executeUpdate() != 1) {
                connection.rollback();
                return null;
            }
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                if (commit) {
                    this.connection.commit(CommittedSqlCommand.INSERT);
                }
                property.setId(resultSet.getLong("insert_id"));
                LogUtil.traceFetchId(Property.class, property.getId(), LOGGER);
                return property;
            }
            return null;
        } catch (SQLException e) {
            LogUtil.errorSqlStatement(preparedStatement, LOGGER, e);
            throw new RuntimeException(e);
        }
    }

    public Property selectId(long id) {
        final String query = "SELECT name, description FROM properties WHERE id = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, id);

            LogUtil.traceSqlStatement(preparedStatement, LOGGER);

            ResultSet resultSet = preparedStatement.executeQuery();

            Property result = null;
            if (resultSet.next()) {
                result = new Property();
                result.setId(id);
                result.setName(resultSet.getString("name"));
                result.setDescription(resultSet.getString("description"));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
