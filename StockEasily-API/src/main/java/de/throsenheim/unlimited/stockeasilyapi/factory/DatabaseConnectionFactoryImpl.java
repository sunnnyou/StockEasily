package de.throsenheim.unlimited.stockeasilyapi.factory;

import de.throsenheim.unlimited.stockeasilyapi.service.config.ConfigService;
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
    public Connection getConnection() {
        return getConnection(true);
    }

    @Override
    public Connection getConnection(boolean autoCommit) {
        return getConnection(this.environment, autoCommit);
    }

    @Override
    public Connection getConnection(Environment environment) {
        return getConnection(environment, true);
    }

    @Override
    public Connection getConnection(Environment environment, boolean autoCommit) {
        final String username = configService.getDbConfig("username");
        final String password = configService.getDbConfig("password");
        Connection result = null;
        try {
            result = DriverManager.getConnection(getConnectionString(environment, configService), username, password);
            if (!autoCommit) {
                disableAutoCommit(result);
            }
            return result;
        } catch (SQLException e) {
            String message = "Could not ";
            message += result != null ? "disable auto commit in database" : "initialize a new database connection";
            throw new RuntimeException(message, e);
        }
    }

    private static String getConnectionString(Environment environment, ConfigService configService) {
        final String host = configService.getDbConfig("hostname", environment);
        final String port = configService.getDbConfig("port", environment);
        final String database = configService.getDbConfig("name", environment);
        return "jdbc:mariadb://" + host + ":" + port + "/" + database;
    }

    private void disableAutoCommit(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
    }
}
