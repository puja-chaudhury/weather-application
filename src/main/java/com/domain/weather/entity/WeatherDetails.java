package com.domain.weather.entity;

import javax.persistence.*;

/**
 * Represents the details of the weather.
 * Contains information about the location, weather conditions, and other related data.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@Entity
@Table(name = "weather_details")
public class WeatherDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "precipitation")
    private String precipitation;

    // Location Info
    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "region")
    private String region;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "timezone_id")
    private String timezoneId;

    @Column(name = "local_time")
    private String localtime;

    // WeatherRequestDTO Conditions
    @Column(name = "temperature")
    private Integer temperature;

    @Column(name = "feels_like")
    private Integer feelsLike;

    @Column(name = "weather_description")
    private String weatherDescription;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "cloud_cover")
    private Integer cloudCover;

    @Column(name = "pressure")
    private Integer pressure;

    @Column(name = "visibility")
    private Integer visibility;

    @Column(name = "wind_speed")
    private Integer windSpeed;

    @Column(name = "wind_degree")
    private Integer windDegree;

    @Column(name = "wind_direction")
    private String windDirection;

    @Column(name = "uv_index")
    private Integer uvIndex;

    @Column(name = "is_day")
    private String isDay;

    @Column(name = "weather_icon_url")
    private String weatherIconUrl;

    // WeatherResponseDTO
    @Column(name = "observation_time")
    private String observation_time;

    @Column(name = "utc_offset")
    private String utc_offset;

    @Column(name = "weather_code")
    private int weather_code;

    @Column(name = "localtime_epoch")
    private long localtime_epoch;

    // Default Constructor
    public WeatherDetails() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(String precipitation) {
        this.precipitation = precipitation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Integer feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(Integer cloudCover) {
        this.cloudCover = cloudCover;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Integer windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public Integer getUvIndex() {
        return uvIndex;
    }

    public void setUvIndex(Integer uvIndex) {
        this.uvIndex = uvIndex;
    }

    public String getIsDay() {
        return isDay;
    }

    public void setIsDay(String isDay) {
        this.isDay = isDay;
    }

    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }

    public void setWeatherIconUrl(String weatherIconUrl) {
        this.weatherIconUrl = weatherIconUrl;
    }

    public String getObservation_time() {
        return observation_time;
    }

    public void setObservation_time(String observation_time) {
        this.observation_time = observation_time;
    }

    public String getUtc_offset() {
        return utc_offset;
    }

    public void setUtc_offset(String utc_offset) {
        this.utc_offset = utc_offset;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(int weather_code) {
        this.weather_code = weather_code;
    }

    public long getLocaltime_epoch() {
        return localtime_epoch;
    }

    public void setLocaltime_epoch(long localtime_epoch) {
        this.localtime_epoch = localtime_epoch;
    }
}

