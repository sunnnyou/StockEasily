package de.throsenheim.unlimited.stockeasilyapi.common.logging;

public enum CommittedSqlCommand {
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    SELECT("SELECT");

    private final String command;

    CommittedSqlCommand(final String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }
}
