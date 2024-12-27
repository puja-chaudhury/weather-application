package com.domain.weather.model;

import java.util.List;

/**
 * Represents the summary of weather conditions.
 * Contains information such as observation time, temperature, weather code,
 * weather icons, weather descriptions, and other related data.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public class WeatherSummaryDTO {

    private List<WeatherHistory> history;

    public static class WeatherHistory {

        private LocationDTO location;

        private CurrentDTO  weather;

        public LocationDTO getLocation() {
            return location;
        }

        public void setLocation(LocationDTO location) {
            this.location = location;
        }

        public CurrentDTO getWeather() {
            return weather;
        }

        public void setWeather(CurrentDTO weather) {
            this.weather = weather;
        }
    }

    public List<WeatherHistory> getHistory() {
        return history;
    }

    public void setHistory(List<WeatherHistory> history) {
        this.history = history;
    }
}
