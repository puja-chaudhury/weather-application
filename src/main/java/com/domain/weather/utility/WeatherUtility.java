package com.domain.weather.utility;

import com.domain.weather.entity.WeatherDetails;
import com.domain.weather.entity.WeatherEntity;
import com.domain.weather.exception.WeatherException;
import com.domain.weather.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.domain.weather.exception.WeatherException.INTERNAL_SERVER_ERROR;

/**
 * WeatherUtility is a utility class that provides various utility methods for weather-related operations.
 * It includes methods for validating user and postal code inputs, fetching weather data from the API,
 * converting weather entities to weather summaries, and more.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public class WeatherUtility {

    private static final String US_POSTAL_CODE_FORMAT = "^[0-9]{5}(?:-[0-9]{4})?$";

    private static final String NAME_FORMAT = "^[A-Za-z]+([ '-][A-Za-z]+)*$";

    /**
     * Validates the postal code format.
     *
     * @param postalCode the postal code to validate
     * @return true if the postal code is valid, false otherwise
     */
    public static boolean validatePostalCode(String postalCode) {
        Pattern pattern = Pattern.compile(US_POSTAL_CODE_FORMAT);
        if (Strings.isBlank(postalCode) || !pattern.matcher(postalCode).matches()) {
            return false;
        }
        return true;
    }

    /**
     * Validates the user format.
     *
     * @param user the user to validate
     * @return true if the user is valid, false otherwise
     */
    public static boolean validateUser(String user) {
        Pattern pattern = Pattern.compile(NAME_FORMAT);
        if (Strings.isBlank(user) || !pattern.matcher(user).matches()) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the request is valid.
     *
     * @param user the user associated with the request
     * @param postalCode the postal code associated with the request
     * @return true if the request is valid, false otherwise
     */
    public static boolean isValidRequest(String user, String postalCode) {
        return WeatherUtility.validateUser(user) || WeatherUtility.validatePostalCode(postalCode) || (Strings.isBlank(user) && Strings.isBlank(postalCode));
    }

    /**
     * Creates an instance of WeatherException for an invalid request.
     *
     * @param user the user associated with the request
     * @param postalCode the postal code associated with the request
     * @return the WeatherException instance
     */
    public static WeatherException createInvalidRequestException(String user, String postalCode) {
        if (Strings.isNotBlank(user)) {
            return new WeatherException(WeatherException.INVALID_USER_ERROR, WeatherException.INVALID_USER_MSG, LocalDateTime.now());
        } else if (Strings.isNotBlank(postalCode)) {
            return new WeatherException(WeatherException.INVALID_POSTAL_CODE_ERROR, WeatherException.INVALID_POSTAL_CODE_MSG, LocalDateTime.now());
        } else {
            return new WeatherException(WeatherException.INTERNAL_SERVER_ERROR, WeatherException.INTERNAL_SERVER_ERROR_MSG, LocalDateTime.now());
        }
    }

    /**
     * Validates the weather request.
     *
     * @param weatherRequestDTO the weather request to validate
     * @return true if the request is valid, false otherwise
     */
    public static boolean validateWeather(WeatherRequestDTO weatherRequestDTO) {
        if (weatherRequestDTO == null) {
            return false;
        }
        if (!validateUser(weatherRequestDTO.getUser())) {
            return false;
        }
        if (!validatePostalCode(weatherRequestDTO.getPostalCode())) {
            return false;
        }
        return true;
    }

    /**
     * Converts a weather entity to a weather summary.
     *
     * @param weatherEntity the weather entity to convert
     * @return the weather summary
     */
    public static WeatherSummaryDTO.WeatherHistory convertWeatherEntityToWeatherHistory(WeatherEntity weatherEntity) {
        WeatherSummaryDTO.WeatherHistory weather = new WeatherSummaryDTO.WeatherHistory();
        if (weatherEntity == null) {
            return weather;
        }
        return setWeatherHistory(weatherEntity);
    }

    /**
     * Converts a weather response DTO to a weather entity.
     *
     * @param weatherResponseDTO the weather response DTO to convert
     * @return the weather entity
     */
    public static WeatherEntity convertWeatherResponseToWeatherEntity(WeatherResponseDTO weatherResponseDTO) {
        WeatherEntity weatherEntity = new WeatherEntity();
        if (weatherResponseDTO == null) {
            return weatherEntity;
        }
        WeatherDetails weatherDetails = new WeatherDetails();
        weatherDetails.setWindDegree(weatherResponseDTO.getCurrent().getWind_degree());
        weatherDetails.setWindSpeed(weatherResponseDTO.getCurrent().getWind_speed());
        weatherDetails.setIsDay(weatherResponseDTO.getCurrent().getIs_day());
        weatherDetails.setTemperature(weatherResponseDTO.getCurrent().getTemperature());
        weatherDetails.setPrecipitation(String.valueOf(weatherResponseDTO.getCurrent().getPrecip()));
        weatherDetails.setHumidity(weatherResponseDTO.getCurrent().getHumidity());
        weatherDetails.setPressure(weatherResponseDTO.getCurrent().getPressure());
        weatherDetails.setWeatherIconUrl(weatherResponseDTO.getCurrent().getWeather_icons().get(0));
        weatherDetails.setWeatherDescription(weatherResponseDTO.getCurrent().getWeather_descriptions().get(0));
        weatherDetails.setVisibility(weatherResponseDTO.getCurrent().getVisibility());
        weatherDetails.setCountry(weatherResponseDTO.getLocation().getCountry());
        weatherDetails.setCity(weatherResponseDTO.getLocation().getName());
        weatherDetails.setLatitude(weatherResponseDTO.getLocation().getLat());
        weatherDetails.setLongitude(weatherResponseDTO.getLocation().getLon());
        weatherDetails.setTimezoneId(weatherResponseDTO.getLocation().getTimezone_id());
        weatherDetails.setUtc_offset(weatherResponseDTO.getLocation().getUtc_offset());
        weatherDetails.setLocaltime(weatherResponseDTO.getLocation().getLocaltime());
        weatherDetails.setRegion(weatherResponseDTO.getLocation().getRegion());
        weatherDetails.setLocaltime_epoch(weatherResponseDTO.getLocation().getLocaltime_epoch());
        weatherDetails.setFeelsLike(weatherResponseDTO.getCurrent().getFeelslike());
        weatherDetails.setCloudCover(weatherResponseDTO.getCurrent().getCloudcover());
        weatherDetails.setUvIndex(weatherResponseDTO.getCurrent().getUv_index());
        weatherDetails.setWindDirection(weatherResponseDTO.getCurrent().getWind_dir());
        weatherDetails.setObservation_time(weatherResponseDTO.getCurrent().getObservation_time());
        weatherDetails.setWeather_code(weatherResponseDTO.getCurrent().getWeather_code());
        weatherEntity.setWeatherData(weatherDetails);
        weatherEntity.setTimestamp(LocalDateTime.now());
        return weatherEntity;
    }

    /**
     * Fetches weather details from the API.
     *
     * @param weatherRequestDTO the weather request to fetch weather details for
     * @return the weather response
     * @throws WeatherException if there is an error with the request or retrieving the weather data
     */
    public static WeatherResponseDTO fetchWeatherFromAPI(WeatherRequestDTO weatherRequestDTO) throws  WeatherException {
        String weatherResponse = WeatherWebClient.CallWeatherAPI(weatherRequestDTO.getPostalCode());
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert JSON string to POJO
            return objectMapper.readValue(weatherResponse, WeatherResponseDTO.class);

        } catch (Exception  e) {
            System.err.println("Caught exception: " + e.getMessage());
            throw new WeatherException(INTERNAL_SERVER_ERROR,WeatherException.INTERNAL_SERVER_ERROR_MSG, LocalDateTime.now());
        }
    }

    /**
     * Converts a list of weather entities to a weather summary.
     *
     * @param weather the list of weather entities to convert
     * @return the weather summary
     */
    public static WeatherSummaryDTO convertWeatherEntityToWeather(List<WeatherEntity> weather) {
        WeatherSummaryDTO weatherSummaryDTO = new WeatherSummaryDTO();
        List<WeatherSummaryDTO.WeatherHistory> weatherHistoryList = new ArrayList<>();
        if (weather == null) {
            return weatherSummaryDTO;
        }
        for(WeatherEntity weatherEntity : weather) {
            weatherHistoryList.add(setWeatherHistory(weatherEntity));
        }
        weatherSummaryDTO.setHistory(weatherHistoryList);
        return weatherSummaryDTO;
    }

    /**
     * Sets the weather history for a given weather entity.
     *
     * @param weatherEntity the weather entity to set the weather history for
     * @return the weather history
     */
    public static WeatherSummaryDTO.WeatherHistory setWeatherHistory(WeatherEntity weatherEntity){

        WeatherSummaryDTO.WeatherHistory weather = new WeatherSummaryDTO.WeatherHistory();
        LocationDTO location = new LocationDTO.Builder()
                .country(weatherEntity.getWeatherData().getCountry())
                .name(weatherEntity.getWeatherData().getCity())
                .lat(weatherEntity.getWeatherData().getLatitude())
                .lon(weatherEntity.getWeatherData().getLongitude())
                .region(weatherEntity.getWeatherData().getRegion())
                .localtime(weatherEntity.getWeatherData().getLocaltime())
                .timezone_id(weatherEntity.getWeatherData().getTimezoneId())
                .utc_offset(weatherEntity.getWeatherData().getUtc_offset())
                .localtime_epoch(weatherEntity.getWeatherData().getLocaltime_epoch())
                .build();

        CurrentDTO currentDTO = new CurrentDTO.Builder()
                .cloudcover(weatherEntity.getWeatherData().getCloudCover())
                .feelslike(weatherEntity.getWeatherData().getFeelsLike())
                .humidity(weatherEntity.getWeatherData().getHumidity())
                .is_day(weatherEntity.getWeatherData().getIsDay())
                .precip(Integer.parseInt(weatherEntity.getWeatherData().getPrecipitation()))
                .pressure(weatherEntity.getWeatherData().getPressure())
                .temperature(weatherEntity.getWeatherData().getTemperature())
                .uv_index(weatherEntity.getWeatherData().getUvIndex())
                .visibility(weatherEntity.getWeatherData().getVisibility())
                .weather_descriptions(Arrays.asList(weatherEntity.getWeatherData().getWeatherDescription()))
                .weather_icons(Arrays.asList(weatherEntity.getWeatherData().getWeatherIconUrl()))
                .wind_degree(weatherEntity.getWeatherData().getWindDegree())
                .wind_speed(weatherEntity.getWeatherData().getWindSpeed())
                .wind_dir(weatherEntity.getWeatherData().getWindDirection())
                .observation_time(weatherEntity.getWeatherData().getObservation_time())
                .weather_code(weatherEntity.getWeatherData().getWeather_code())
                .build();

        WeatherSummaryDTO.WeatherHistory weatherHistory = new WeatherSummaryDTO.WeatherHistory();
        weatherHistory.setLocation(location);
        weatherHistory.setWeather(currentDTO);

        return weatherHistory;
    }
}
