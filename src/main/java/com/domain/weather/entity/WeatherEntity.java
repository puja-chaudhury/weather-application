package com.domain.weather.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Represents a weather entity in the weather application.
 * Contains information about the user, postal code, and timestamp of the weather request.
 * Also includes a one-to-one relationship with the `WeatherDetails` entity.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@Entity
@Table(name = "weather")
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String user;

    @Column(name = "postalcode", nullable = false)
    private String postalCode;

    @Column(name = "request_timestamp", nullable = false)
    private LocalDateTime timestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_details_id", nullable = false)
    private WeatherDetails weatherDetails;

    // Default Constructor
    public WeatherEntity() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public WeatherDetails getWeatherData() {
        return weatherDetails;
    }

    public void setWeatherData(WeatherDetails weatherDetails) {
        this.weatherDetails = weatherDetails;
    }
}

