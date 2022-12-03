package de.throsenheim.unlimited.stockeasilyapi.common.logging;

import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogUtil {

    public static void traceSqlStatement(final PreparedStatement preparedStatement, final Logger logger) {
        logger.trace("Executing SQL query: " + preparedStatement.toString());
    }

    public static void errorSqlStatement(final PreparedStatement preparedStatement, final Logger logger, SQLException e) {
        logger.error("Could not execute SQL query: " + preparedStatement.toString() + " - Error: " + e.getMessage());
    }

    public static <ID> void traceFetchId(final Class<?> modelClass, ID fetchId, final Logger logger) {
        logger.trace("Retrieved " + modelClass.getSimpleName() + " fetch_id " + fetchId);
    }
}
