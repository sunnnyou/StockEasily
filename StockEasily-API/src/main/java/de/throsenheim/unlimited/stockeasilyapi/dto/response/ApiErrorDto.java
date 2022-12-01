package de.throsenheim.unlimited.stockeasilyapi.dto.response;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

import java.util.*;

public class ApiErrorDto {
    private final Date timestamp;

    @ApiModelProperty(notes = "HTTP status code", example = "500")
    private HttpStatus status;

    @ApiModelProperty(notes = "Internal Server error", example = "Internal Server Error")
    private String message;

    private Map<String, Object> errors;

    public ApiErrorDto() {
        timestamp = new Date();
    }

    public ApiErrorDto(HttpStatus httpStatus, String message) {
        this();
        this.status = httpStatus;
        this.message = message;
    }

    public ApiErrorDto(HttpStatus httpStatus, String message, Map<String, Object> errors) {
        this(httpStatus, message);
        this.errors = errors;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, Object> errors) {
        this.errors = errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
