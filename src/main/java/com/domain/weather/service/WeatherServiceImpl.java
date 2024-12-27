package com.domain.weather.service;

import com.domain.weather.entity.WeatherEntity;
import com.domain.weather.exception.WeatherException;
import com.domain.weather.jpa.WeatherRepository;
import com.domain.weather.model.WeatherRequestDTO;
import com.domain.weather.model.WeatherResponseDTO;
import com.domain.weather.model.WeatherSummaryDTO;
import com.domain.weather.utility.WeatherUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

/**
 * WeatherServiceImpl is a service class that handles weather-related operations.
 * It uses the WeatherRepository to interact with the weather data in the database.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LogManager.getLogger(WeatherServiceImpl.class);

    @Autowired
    private WeatherRepository weatherRepository;

    /**
     * Retrieves weather details for a given postal code.
     *
     * @param user          the user associated with the weather request
     * @param postalCode    the postal code associated with the weather request
     * @return the weather summary DTO
     * @throws WeatherException if there is an error with the request or retrieving the weather data
     */
    @Override
    public WeatherSummaryDTO getWeather(String user, String postalCode) {
        logger.debug("Retrieving weather for user: {}, postal code: {}", user, postalCode);
        boolean validPostalCode = WeatherUtility.validatePostalCode(postalCode);
        boolean validUser = WeatherUtility.validateUser(user);
        List<WeatherEntity> weather;
        if (validUser && validPostalCode) {
            weather = weatherRepository.findByUserAndPostalCode(user, postalCode);
        } else if (validUser) {
            weather = weatherRepository.findByUser(user);
        } else if (validPostalCode) {
            weather = weatherRepository.findByPostalCode(postalCode);
        } else {
            weather = weatherRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }
        return WeatherUtility.convertWeatherEntityToWeather(weather);
    }

    /**
     * Saves weather details for a given postal code.
     *
     * @param weatherRequestDTO the weather request DTO
     * @return the weather summary DTO
     * @throws WeatherException if there is an error with the request or retrieving the weather data
     */
    @Override
    public WeatherSummaryDTO.WeatherHistory saveWeather(WeatherRequestDTO weatherRequestDTO) throws WeatherException {
        WeatherResponseDTO weatherResponseDTO = WeatherUtility.fetchWeatherFromAPI(weatherRequestDTO);

        WeatherEntity weatherEntity = buildWeatherEntity(weatherResponseDTO, weatherRequestDTO);

        weatherRepository.save(weatherEntity);

        return WeatherUtility.convertWeatherEntityToWeatherHistory(weatherEntity);
    }

    /**
     * Builds a WeatherEntity object from a WeatherResponseDTO and a WeatherRequestDTO.
     *
     * @param responseDTO the weather response DTO
     * @param requestDTO the weather request DTO
     * @return the weather entity
     */
    private WeatherEntity buildWeatherEntity(WeatherResponseDTO responseDTO, WeatherRequestDTO requestDTO) {
        WeatherEntity entity = WeatherUtility.convertWeatherResponseToWeatherEntity(responseDTO);
        entity.setUser(requestDTO.getUser());
        entity.setPostalCode(requestDTO.getPostalCode());
        return entity;
    }

}
