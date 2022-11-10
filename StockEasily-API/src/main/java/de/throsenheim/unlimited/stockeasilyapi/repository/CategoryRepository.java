package de.throsenheim.unlimited.stockeasilyapi.repository;

import de.throsenheim.unlimited.stockeasilyapi.factory.DatabaseConnectionFactory;
import de.throsenheim.unlimited.stockeasilyapi.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Optional;

@Component
public class CategoryRepository implements HumaneRepository<Category, Long> {

    private final Connection connection;

    @Autowired
    public CategoryRepository(DatabaseConnectionFactory databaseConnectionFactory) {
        this.connection = databaseConnectionFactory.getConnection(false);
    }

    @Override
    public Iterable<Category> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public Optional<Category> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Category findByName(String name) {
        final String query = "SELECT id FROM categories WHERE name = ? LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            Category result = new Category();
            result.setId(resultSet.getInt("id"));
            result.setName(name);
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Category> saveAll(Iterable<Category> entities) {
        return null;
    }

    @Override
    public Category save(Category category) {
        try {
            final String query = "INSERT INTO categories(name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, category.getName());

            if (preparedStatement.executeUpdate() == 1) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    category.setId(resultSet.getLong("insert_id"));
                    return category;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
