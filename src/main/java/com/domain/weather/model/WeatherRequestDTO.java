package com.domain.weather.model;

import javax.validation.constraints.NotNull;

/**
 * Represents the request data transfer object (DTO) for the weather application.
 * Contains information about the user ID, postal code, and timestamp.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public class WeatherRequestDTO {

    @NotNull(message = "User is required.")
    private String user;

    @NotNull(message = "Postal code is required.")
    private String postalCode;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public WeatherRequestDTO() {
    }
}
