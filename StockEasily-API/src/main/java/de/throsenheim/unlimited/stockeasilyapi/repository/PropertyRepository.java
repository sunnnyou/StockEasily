package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.factory.IDatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class PropertyRepository implements HumaneRepository<Property, Long> {

    private final Connection connection;

    @Autowired
    public PropertyRepository(IDatabaseConnectionFactory databaseConnectionFactory) {
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
    public Iterable<Property> saveAll(Iterable<Property> entities) {
        return null;
    }

    @Override
    public Property save(Property entity) {
        return null;
    }

}
