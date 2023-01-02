package de.throsenheim.unlimited.stockeasilyapi.exception;

import org.springframework.validation.BindingResult;

public class InvalidBodyException extends RuntimeException {

    private BindingResult result;

    public InvalidBodyException(BindingResult result) {
        super();
        this.setResult(result);
    }

    public InvalidBodyException() {
        this(null);
    }

    public BindingResult getResult() {
        return result;
    }

    public void setResult(BindingResult result) {
        this.result = result;
    }

}
