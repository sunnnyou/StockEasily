package de.throsenheim.unlimited.stockeasilyapi.factory;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;

public interface DatabaseConnectionFactory {

    SqlConnection getConnection(Class<?> repositoryClass, Class<?> modelClass, Logger logger);

    SqlConnection getConnection(boolean autoCommit, Class<?> repositoryClass, Class<?> modelClass, Logger logger);

    SqlConnection getConnection(Environment environment, Class<?> repositoryClass, Class<?> modelClass, Logger logger);

    SqlConnection getConnection(Environment environment, boolean autoCommit, Class<?> repositoryClass, Class<?> modelClass, Logger logger);

}
