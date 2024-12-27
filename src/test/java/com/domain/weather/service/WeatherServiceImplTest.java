package com.domain.weather.service;

import com.domain.weather.entity.WeatherEntity;
import com.domain.weather.exception.WeatherException;
import com.domain.weather.jpa.WeatherRepository;
import com.domain.weather.model.*;
import com.domain.weather.utility.WeatherUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Represents a test class for the `WeatherServiceImpl`.
 * Contains test methods to verify the functionality of the `WeatherServiceImpl`.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@ExtendWith(MockitoExtension.class)
public class WeatherServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    private WeatherRequestDTO weatherRequestDTO;
    private WeatherResponseDTO weatherResponseDTO;
    private WeatherEntity weatherEntity;

    @BeforeEach
    void setUp() {
        weatherRequestDTO = new WeatherRequestDTO();
        weatherRequestDTO.setUser("testUser");
        weatherRequestDTO.setPostalCode("12345");

        weatherResponseDTO = new WeatherResponseDTO();
        WeatherResponseDTO.RequestDTO request = new WeatherResponseDTO.RequestDTO();
        request.setType("city");
        request.setQuery("New York");
        request.setLanguage("en");
        request.setUnit("m");
        weatherResponseDTO.setRequest(request);
        CurrentDTO currentWeather = new CurrentDTO.Builder()
                .observation_time("2024-12-26 10:00")
                .temperature(22)
                .weather_code(1000)
                .weather_icons(Arrays.asList("clear-sky.png"))
                .weather_descriptions(Arrays.asList("Clear sky"))
                .wind_speed(10)
                .wind_degree(180)
                .wind_dir("South")
                .pressure(1015)
                .precip(0)
                .humidity(65)
                .cloudcover(10)
                .feelslike(21)
                .uv_index(5)
                .visibility(10)
                .is_day("yes")
                .build();
        LocationDTO location = new LocationDTO.Builder()
                .country("USA")
                .name("New York")
                .lat("40.7128")
                .lon("-74.0060")
                .region("New York")
                .localtime("2024-12-26 12:00:00")
                .timezone_id("America/New_York")
                .utc_offset("-05:00")
                .localtime_epoch(1703592000L)
                .build();
        weatherResponseDTO.setLocation(location);
        weatherResponseDTO.setCurrent(currentWeather);
        weatherEntity = new WeatherEntity();
        weatherEntity.setUser("testUser");
        weatherEntity.setPostalCode("12345");
    }

    @Test
    void testGetWeather_ValidRequest() {
        WeatherRequestDTO request = new WeatherRequestDTO();
        request.setPostalCode("12345");
        request.setUser("testUser");

        try (MockedStatic<WeatherUtility> mockedUtil = mockStatic(WeatherUtility.class)) {
            mockedUtil.when(() -> WeatherUtility.fetchWeatherFromAPI(request))
                    .thenReturn(weatherResponseDTO);

            WeatherSummaryDTO result = weatherService.getWeather("testUser", "12345");

            // Assertions
            assertNull(result);
        }
    }

    @Test
    void testGetWeather_ValidRequestOnlyUser() {
        WeatherRequestDTO request = new WeatherRequestDTO();
        request.setUser("testUser");

        try (MockedStatic<WeatherUtility> mockedUtil = mockStatic(WeatherUtility.class)) {
            mockedUtil.when(() -> WeatherUtility.fetchWeatherFromAPI(request))
                    .thenReturn(weatherResponseDTO);

            WeatherSummaryDTO result = weatherService.getWeather("testUser", "");

            // Assertions
            assertNull(result);
        }
    }

    @Test
    void testGetWeather_ValidRequestOnlyPostalCode() {
        WeatherRequestDTO request = new WeatherRequestDTO();
        request.setPostalCode("12345");

        try (MockedStatic<WeatherUtility> mockedUtil = mockStatic(WeatherUtility.class)) {
            mockedUtil.when(() -> WeatherUtility.fetchWeatherFromAPI(request))
                    .thenReturn(weatherResponseDTO);

            WeatherSummaryDTO result = weatherService.getWeather("", "12345");

            // Assertions
            assertNull(result);
        }
    }


    @Test
    void testSaveWeather_Success() throws WeatherException {
        // Arrange
        try (var mockedUtil = mockStatic(WeatherUtility.class)) {
            mockedUtil.when(() -> WeatherUtility.fetchWeatherFromAPI(weatherRequestDTO)).thenReturn(weatherResponseDTO);
            mockedUtil.when(() -> WeatherUtility.convertWeatherResponseToWeatherEntity(weatherResponseDTO)).thenReturn(weatherEntity);
            mockedUtil.when(() -> WeatherUtility.convertWeatherEntityToWeatherHistory(weatherEntity)).thenReturn(new WeatherSummaryDTO.WeatherHistory());

            // Act
            WeatherSummaryDTO.WeatherHistory result = weatherService.saveWeather(weatherRequestDTO);

            // Assert
            assertNotNull(result);
            verify(weatherRepository).save(any(WeatherEntity.class));
        }
    }

    @Test
    void testSaveWeather_ThrowsWeatherException() {
        // Arrange
        try (var mockedUtil = mockStatic(WeatherUtility.class)) {
            mockedUtil.when(() -> WeatherUtility.fetchWeatherFromAPI(weatherRequestDTO)).thenThrow(new WeatherException("API_ERROR", "API call failed", null));

            // Act & Assert
            assertThrows(WeatherException.class, () -> weatherService.saveWeather(weatherRequestDTO));
            verify(weatherRepository, never()).save(any(WeatherEntity.class));
        }
    }
}
