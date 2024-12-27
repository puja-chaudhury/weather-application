package com.domain.weather.model;

import java.time.LocalDateTime;

/**
 * Represents the error response for the weather application.
 * Contains information about the error message, error code, and timestamp.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public class WeatherErrorResponse {

    private String message;

    private String code;

    private LocalDateTime timestamp;

    public WeatherErrorResponse() {

    }
    public WeatherErrorResponse(String message, String code, LocalDateTime timestamp) {
        this.message = message;
        this.code = code;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
