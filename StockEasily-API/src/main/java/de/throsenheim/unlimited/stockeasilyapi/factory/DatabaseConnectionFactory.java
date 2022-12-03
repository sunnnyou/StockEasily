package de.throsenheim.unlimited.stockeasilyapi.factory;

import de.throsenheim.unlimited.stockeasilyapi.abstraction.SqlConnection;
import org.slf4j.Logger;

public interface DatabaseConnectionFactory {

    SqlConnection getConnection(Class<?> repositoryClass, Class<?> modelClass, Logger logger);

    SqlConnection getConnection(boolean autoCommit, Class<?> repositoryClass, Class<?> modelClass, Logger logger);

}
