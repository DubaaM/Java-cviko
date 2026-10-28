package sk.ukf.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import sk.ukf.demo.response.ResponseFactory;
import sk.ukf.demo.response.ResponseWrapper;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ResponseFactory response;

    public GlobalExceptionHandler(ResponseFactory response) {
        this.response = response;
    }

    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleValidation(
            MethodArgumentNotValidException ex) {

        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Map.of(
                        "field", error.getField(),
                        "message", error.getDefaultMessage() != null
                                ? error.getDefaultMessage()
                                : "Invalid value"
                ))
                .toList();

        return response.error(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                errors
        );
    }

    // Invalid path variable or request parameter
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        String message = "Parameter '" + ex.getName()
                + "' has an invalid value";

        return response.error(HttpStatus.BAD_REQUEST, message);
    }

    // Invalid JSON or invalid date format
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleUnreadableMessage(
            HttpMessageNotReadableException ex) {

        return response.error(
                HttpStatus.BAD_REQUEST,
                "Invalid request body or data format"
        );
    }

    // Object not found
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleObjectNotFound(
            ObjectNotFoundException ex) {

        return response.error(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );
    }

    // Employee or other object not found via ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleResponseStatus(
            ResponseStatusException ex) {

        HttpStatus status = HttpStatus.valueOf(
                ex.getStatusCode().value()
        );

        String message = ex.getReason() != null
                ? ex.getReason()
                : status.getReasonPhrase();

        return response.error(status, message);
    }

    // Email already exists
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ResponseWrapper<Void>> handleEmailExists(
            EmailAlreadyExistsException ex) {

        return response.error(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );
    }

    // Unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Void>> handleGeneral(
            Exception ex) {

        return response.error(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected server error occurred"
        );
    }
}