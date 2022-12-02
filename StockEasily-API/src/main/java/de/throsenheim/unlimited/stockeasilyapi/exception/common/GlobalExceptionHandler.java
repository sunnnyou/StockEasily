package de.throsenheim.unlimited.stockeasilyapi.exception.common;

import de.throsenheim.unlimited.stockeasilyapi.exception.InvalidBodyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final CommonErrorHandler commonErrorHandler;

    @Autowired
    public GlobalExceptionHandler(CommonErrorHandler commonErrorHandler) {
        this.commonErrorHandler = commonErrorHandler;
    }

    @ExceptionHandler(InvalidBodyException.class)
    public ResponseEntity<?> invalidBodyException(InvalidBodyException ex) {
        final List<FieldError> errors = ex.getResult().getFieldErrors();
        for (FieldError error : errors) {
            logger.error("Validation error '" + error.getField() + "', cause: " + error.getDefaultMessage());
        }
        return commonErrorHandler.getInvalidFieldsErrorResponse("Validation error", commonErrorHandler.getFieldErrorResponse(ex.getResult()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException ex) {
        return commonErrorHandler.getConstraintViolationResponse(ex.getMessage());
    }
}