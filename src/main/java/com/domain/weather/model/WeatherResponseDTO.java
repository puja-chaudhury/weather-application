package com.domain.weather.model;

/**
 * Represents the response data transfer object (DTO) for the weather application.
 * Contains information about the weather data, including the current weather conditions,
 * location information, and other related data.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public class WeatherResponseDTO {

    private RequestDTO request;
    private LocationDTO location;
    private CurrentDTO current;

    public WeatherResponseDTO() {
    }

    // Getters and Setters

    public static class RequestDTO {
        private String type;
        private String query;
        private String language;
        private String unit;

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }

    // Getters and setters for the main class
    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public CurrentDTO getCurrent() {
        return current;
    }

    public void setCurrent(CurrentDTO current) {
        this.current = current;
    }
}
