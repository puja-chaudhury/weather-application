package com.domain.weather.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Represents an exception that occurs when an error occurs during the retrieval of weather information.
 * Provides a way to handle and handle weather-related exceptions in the application.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@Schema(description = "Custom exception response for weather errors")
public class WeatherException extends RuntimeException {

    public static final String INVALID_POSTAL_CODE_MSG = "The provided postal code is invalid. Please check and try again.";
    public static final String INVALID_USER_MSG = "The specified user is invalid or does not exist. Please verify the details.";
    public static final String INTERNAL_SERVER_ERROR_MSG = "An unexpected error occurred on the server. Please try again later.";
    public static final String INVALID_REQUEST_MSG = "Invalid request body. The user and postalCode fields are required and cannot be empty.";
    public static final String INVALID_ACCESS_KEY_MSG = "You have not supplied a valid API Access Key. [Technical Support: support@apilayer.com]";

    public static final String INVALID_POSTAL_CODE_ERROR = "W-0001";
    public static final String INVALID_USER_ERROR = "W-0002";
    public static final String NOT_FOUND_ERROR = "W-0003";
    public static final String INTERNAL_SERVER_ERROR = "W-0004";
    public static final String INVALID_REQUEST_ERROR = "W-0005";
    public static final String INVALID_ACCESS_KEY = "W-0005";

    @Schema(description = "Error code")
    private String code;

    @Schema(description = "Error message")
    private String message;

    @Schema(description = "Timestamp of the error")
    private LocalDateTime timestamp;

    public WeatherException(String code, String message, LocalDateTime timestamp) {
        super(message);
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
        }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Optionally, toString method to format the exception details
    @Override
    public String toString() {
        return "WeatherException{" +
                "errorCode='" + code + '\'' +
                ", errorMessage='" + message + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
    }
