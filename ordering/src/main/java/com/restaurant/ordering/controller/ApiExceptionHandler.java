package com.restaurant.ordering.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Central error handling for every controller. Instead of each endpoint
 * catching exceptions, the service just throws, and this class turns the
 * exception into a clean JSON error: {"error": "..."}.
 *
 *  - EntityNotFoundException (unknown order, unknown item, item not in
 *    order) -> 404 Not Found
 *  - MethodArgumentNotValidException (request body failed @Valid, e.g. a
 *    blank itemName) -> 400 Bad Request
 */
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> onNotFound(EntityNotFoundException exception) {
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> onValidation(MethodArgumentNotValidException exception) {
        return Map.of("error", "Invalid request");
    }
}
