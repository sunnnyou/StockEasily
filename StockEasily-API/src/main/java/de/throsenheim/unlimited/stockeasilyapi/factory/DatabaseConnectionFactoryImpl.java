package de.throsenheim.unlimited.stockeasilyapi.factory;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnectionFactoryImpl implements DatabaseConnectionFactory {

    private final String hostname;

    private final int port;

    private final String schema;

    private final String username;

    private final String password;

    public DatabaseConnectionFactoryImpl(@Value("${database.hostname}") String hostname,
                                         @Value("${database.port}") int port,
                                         @Value("${database.name}") String schema,
                                         @Value("${database.username}") String username,
                                         @Value("${database.password}") String password) {
        this.hostname = hostname;
        this.port = port;
        this.schema = schema;
        this.username = username;
        this.password = password;
    }

    @Override
    public SqlConnection getConnection(final Class<?> repositoryClass, final Class<?> modelClass, final Logger logger) {
        return getConnection(true, repositoryClass, modelClass, logger);
    }

    @Override
    public SqlConnection getConnection(final boolean autoCommit,
                                       final Class<?> repositoryClass,
                                       final Class<?> modelClass,
                                       final Logger logger) {
        logger.debug("Opening new database connection");
        SqlConnection result;
        try {
            final String connectionString = getConnectionString();
            logger.trace("Using connection string " + connectionString);
            Connection connection = DriverManager.getConnection(connectionString, username, password);
            result = new SqlConnection(connection, modelClass, logger);
            if (!autoCommit) {
                result.setAutoCommit(false);
            } else {
                logger.debug("Keeping non-controlled auto-commit mode on");
            }
            return result;
        } catch (SQLException e) {
            String message = "Could not initialize new database connection";
            logger.error(message);
            throw new RuntimeException(message, e);
        }
    }

    private String getConnectionString() {
        return "jdbc:mariadb://" + hostname + ":" + port + "/" + schema;
    }
}
