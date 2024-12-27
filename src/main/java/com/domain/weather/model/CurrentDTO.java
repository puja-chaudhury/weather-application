package com.domain.weather.model;

import java.util.List;

/**
 * Represents the current weather conditions.
 * Contains information such as observation time, temperature, weather code,
 * weather icons, weather descriptions, wind speed, and other related data.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public class CurrentDTO {
    private String observation_time;
    private int temperature;
    private int weather_code;
    private List<String> weather_icons;
    private List<String> weather_descriptions;
    private int wind_speed;
    private int wind_degree;
    private String wind_dir;
    private int pressure;
    private int precip;
    private int humidity;
    private int cloudcover;
    private int feelslike;
    private int uv_index;
    private int visibility;
    private String is_day;

    // Getters
    public String getObservation_time() {
        return observation_time;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public List<String> getWeather_icons() {
        return weather_icons;
    }

    public List<String> getWeather_descriptions() {
        return weather_descriptions;
    }

    public int getWind_speed() {
        return wind_speed;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public int getPressure() {
        return pressure;
    }

    public int getPrecip() {
        return precip;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloudcover() {
        return cloudcover;
    }

    public int getFeelslike() {
        return feelslike;
    }

    public int getUv_index() {
        return uv_index;
    }

    public int getVisibility() {
        return visibility;
    }

    public String getIs_day() {
        return is_day;
    }

    // Setters
    public void setObservation_time(String observation_time) { this.observation_time = observation_time; }
    public void setTemperature(int temperature) { this.temperature = temperature; }
    public void setWeather_code(int weather_code) { this.weather_code = weather_code; }
    public void setWeather_icons(List<String> weather_icons) { this.weather_icons = weather_icons; }
    public void setWeather_descriptions(List<String> weather_descriptions) { this.weather_descriptions = weather_descriptions; }
    public void setWind_speed(int wind_speed) { this.wind_speed = wind_speed; }
    public void setWind_degree(int wind_degree) { this.wind_degree = wind_degree; }
    public void setWind_dir(String wind_dir) { this.wind_dir = wind_dir; }
    public void setPressure(int pressure) { this.pressure = pressure; }
    public void setPrecip(int precip) { this.precip = precip; }
    public void setHumidity(int humidity) { this.humidity = humidity; }
    public void setCloudcover(int cloudcover) { this.cloudcover = cloudcover; }
    public void setFeelslike(int feelslike) { this.feelslike = feelslike; }
    public void setUv_index(int uv_index) { this.uv_index = uv_index; }
    public void setVisibility(int visibility) { this.visibility = visibility; }
    public void setIs_day(String is_day) { this.is_day = is_day; }

    // Builder pattern for setting all values in one place
    public static class Builder {
        private CurrentDTO currentDTO = new CurrentDTO();

        public Builder observation_time(String observation_time) {
            currentDTO.setObservation_time(observation_time);
            return this;
        }

        public Builder temperature(int temperature) {
            currentDTO.setTemperature(temperature);
            return this;
        }

        public Builder weather_code(int weather_code) {
            currentDTO.setWeather_code(weather_code);
            return this;
        }

        public Builder weather_icons(List<String> weather_icons) {
            currentDTO.setWeather_icons(weather_icons);
            return this;
        }

        public Builder weather_descriptions(List<String> weather_descriptions) {
            currentDTO.setWeather_descriptions(weather_descriptions);
            return this;
        }

        public Builder wind_speed(int wind_speed) {
            currentDTO.setWind_speed(wind_speed);
            return this;
        }

        public Builder wind_degree(int wind_degree) {
            currentDTO.setWind_degree(wind_degree);
            return this;
        }

        public Builder wind_dir(String wind_dir) {
            currentDTO.setWind_dir(wind_dir);
            return this;
        }

        public Builder pressure(int pressure) {
            currentDTO.setPressure(pressure);
            return this;
        }

        public Builder precip(int precip) {
            currentDTO.setPrecip(precip);
            return this;
        }

        public Builder humidity(int humidity) {
            currentDTO.setHumidity(humidity);
            return this;
        }

        public Builder cloudcover(int cloudcover) {
            currentDTO.setCloudcover(cloudcover);
            return this;
        }

        public Builder feelslike(int feelslike) {
            currentDTO.setFeelslike(feelslike);
            return this;
        }

        public Builder uv_index(int uv_index) {
            currentDTO.setUv_index(uv_index);
            return this;
        }

        public Builder visibility(int visibility) {
            currentDTO.setVisibility(visibility);
            return this;
        }

        public Builder is_day(String is_day) {
            currentDTO.setIs_day(is_day);
            return this;
        }

        public CurrentDTO build() {
            return currentDTO;
        }
    }
}
