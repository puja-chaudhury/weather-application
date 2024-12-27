package com.domain.weather.exception;

import com.domain.weather.model.WeatherErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a global exception handler for the weather application.
 * Provides a way to handle and handle weather-related exceptions.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@RestControllerAdvice
public class WeatherGlobalException {

    @ExceptionHandler(WeatherException.class)
    public ResponseEntity<WeatherErrorResponse> handleWeatherException(WeatherException ex) {
        HttpStatus status = mapErrorToStatus(ex.getCode());
        return new ResponseEntity<>(new WeatherErrorResponse(ex.getMessage(), ex.getCode(), ex.getTimestamp()),status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WeatherErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        WeatherErrorResponse response = new WeatherErrorResponse();
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        for(String error : errors) {
            response.setCode(WeatherException.INVALID_REQUEST_ERROR);
            response.setMessage(WeatherException.INVALID_REQUEST_MSG);
            response.setTimestamp(LocalDateTime.now());
           }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private HttpStatus mapErrorToStatus(String errorCode) {
        switch (errorCode) {
            case WeatherException.INVALID_REQUEST_ERROR:
                return HttpStatus.BAD_REQUEST;
            case WeatherException.NOT_FOUND_ERROR:
                return HttpStatus.NOT_FOUND;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
