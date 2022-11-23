package de.throsenheim.unlimited.stockeasilyapi.exception.common;

import de.throsenheim.unlimited.stockeasilyapi.dto.ApiErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonErrorHandler {

    public Map<String, Object> getFieldErrorResponse(BindingResult result) {
        final Map<String, Object> fieldError = new HashMap<>();
        final List<FieldError> errors = result.getFieldErrors();
        for (FieldError error : errors) {
            fieldError.put(error.getField(), error.getDefaultMessage());
        }
        return fieldError;
    }

    public ResponseEntity<Object> getInvalidFieldsErrorResponse(String message, Map<String, Object> fieldErrors) {
        final ApiErrorDto apiError = new ApiErrorDto(HttpStatus.BAD_REQUEST, message, fieldErrors);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}