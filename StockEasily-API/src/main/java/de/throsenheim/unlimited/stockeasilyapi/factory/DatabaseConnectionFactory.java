package de.throsenheim.unlimited.stockeasilyapi.factory;

import org.springframework.core.env.Environment;

import java.sql.Connection;

public interface DatabaseConnectionFactory {

    Connection getConnection();

    Connection getConnection(boolean autoCommit);

    Connection getConnection(Environment environment);

    Connection getConnection(Environment environment, boolean autoCommit);

}
