package com.domain.weather.service;

import com.domain.weather.exception.WeatherException;
import com.domain.weather.model.WeatherRequestDTO;
import com.domain.weather.model.WeatherSummaryDTO;

/**
 * Represents a service interface for weather-related operations.
 * Provides methods to retrieve and save weather information.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public interface WeatherService{

    WeatherSummaryDTO getWeather(String user, String postalCode);

    WeatherSummaryDTO.WeatherHistory saveWeather(WeatherRequestDTO weatherRequestDTO) throws WeatherException;
}
