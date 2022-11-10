package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class PropertyRepository implements HumaneRepository<Property, Long> {

    private final Connection connection;

    @Autowired
    public PropertyRepository(DatabaseConnectionFactory databaseConnectionFactory) {
        this.connection = databaseConnectionFactory.getConnection(false);
    }

    @Override
    public Iterable<Property> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<Property> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Property findByName(String name) {
        final String query = "SELECT id, description FROM properties WHERE name = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Property result = new Property();
            result.setId(resultSet.getInt("id"));
            result.setName(name);
            result.setDescription(resultSet.getString("description"));
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Property> saveAll(Iterable<Property> properties) {
        List<Property> result = new LinkedList<>();
        for(Property property : properties) {
            Property resultProperty = save(property);
            if (resultProperty == null) {
                System.out.println("Skipping Property " + property.getId());
                continue;
            }
            result.add(resultProperty);
        }
        return result;
    }

    @Override
    public Property save(Property property) {
        try {
            final String query = "INSERT INTO properties(name,description) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, property.getName());
            preparedStatement.setString(2, property.getDescription());

            if (preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    property.setId(resultSet.getLong("insert_id"));
                    return property;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
