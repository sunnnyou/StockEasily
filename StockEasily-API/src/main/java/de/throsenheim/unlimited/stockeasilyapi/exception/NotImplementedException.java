package de.throsenheim.unlimited.stockeasilyapi.exception;

public class NotImplementedException extends RuntimeException {

    public NotImplementedException(String object) {
        this(object, "The function is not supported");
    }

    public NotImplementedException(String object, String message) {
        super(object + ": " + message);
    }

    public NotImplementedException() {
        super("The function is not supported");
    }

}
