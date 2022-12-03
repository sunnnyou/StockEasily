package de.throsenheim.unlimited.stockeasilyapi.factory;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import de.throsenheim.unlimited.stockeasilyapi.service.config.ConfigService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnectionFactoryImpl implements DatabaseConnectionFactory {

    private final Environment environment;

    private final ConfigService configService;

    @Autowired
    public DatabaseConnectionFactoryImpl(Environment environment, ConfigService configService) {
        this.environment = environment;
        this.configService = configService;
    }

    @Override
    public SqlConnection getConnection(final Class<?> repositoryClass, final Class<?> modelClass, final Logger logger) {
        return getConnection(true, repositoryClass, modelClass, logger);
    }

    @Override
    public SqlConnection getConnection(final boolean autoCommit, final Class<?> repositoryClass, final Class<?> modelClass, final Logger logger) {
        return getConnection(this.environment, autoCommit, repositoryClass, modelClass, logger);
    }

    @Override
    public SqlConnection getConnection(final Environment environment,
                                       final Class<?> repositoryClass,
                                       final Class<?> modelClass,
                                       final Logger logger) {
        return getConnection(environment, true, repositoryClass, modelClass, logger);
    }

    @Override
    public SqlConnection getConnection(final Environment environment,
                                       final boolean autoCommit,
                                       final Class<?> repositoryClass,
                                       final Class<?> modelClass,
                                       final Logger logger) {
        logger.debug("Opening new database connection");
        final String username = configService.getDbConfig("username");
        final String password = configService.getDbConfig("password");
        SqlConnection result;
        try {
            final String connectionString = getConnectionString(environment, configService);
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

    private static String getConnectionString(Environment environment, ConfigService configService) {
        final String host = configService.getDbConfig("hostname", environment);
        final String port = configService.getDbConfig("port", environment);
        final String database = configService.getDbConfig("name", environment);
        return "jdbc:mariadb://" + host + ":" + port + "/" + database;
    }
}
