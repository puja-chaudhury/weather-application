package com.domain.weather.controller;

import com.domain.weather.exception.WeatherException;
import com.domain.weather.model.*;
import com.domain.weather.service.WeatherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Represents a test class for the `WeatherController`.
 * Contains test methods to verify the functionality of the `WeatherController`.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSaveWeather_ValidRequest() throws Exception {
        // Arrange
        WeatherRequestDTO weatherRequestDTO = createValidWeatherRequest();
        WeatherSummaryDTO.WeatherHistory expectedWeatherHistory = new WeatherSummaryDTO.WeatherHistory();
        when(weatherService.saveWeather(any(WeatherRequestDTO.class))).thenReturn(expectedWeatherHistory);

        // Act & Assert
        mockMvc.perform(post("/app/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(weatherRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedWeatherHistory)));
    }

    @Test
    public void testSaveWeather_InvalidRequest() throws Exception {
        // Arrange
        WeatherRequestDTO weatherRequestDTO = new WeatherRequestDTO();
        weatherRequestDTO.setUser("testUser");
        weatherRequestDTO.setPostalCode("123");
        when(weatherService.saveWeather(any(WeatherRequestDTO.class))).thenReturn(null);

        // Act
        MvcResult result = mockMvc.perform(post("/app/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(weatherRequestDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        assertTrue(result.getResponse().getContentAsString().contains(WeatherException.INVALID_REQUEST_MSG));
    }

    @Test
    public void testGetWeather_ValidRequest() throws Exception {
        // Arrange
        String user = "testUser";
        String postalCode = "12345";
        WeatherSummaryDTO expectedWeatherSummaryDTO = createValidWeatherSummaryDTO();
        when(weatherService.getWeather(user, postalCode)).thenReturn(expectedWeatherSummaryDTO);

        // Act
        MvcResult result = mockMvc.perform(get("/app/history")
                        .param("user", user)
                        .param("postalCode", postalCode)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        WeatherSummaryDTO actualWeatherSummaryDTO = objectMapper.readValue(result.getResponse().getContentAsString(), WeatherSummaryDTO.class);
        assertEquals(expectedWeatherSummaryDTO.getHistory().get(0).getLocation().getCountry(), actualWeatherSummaryDTO.getHistory().get(0).getLocation().getCountry());
    }

    @Test
    public void testGetWeather_InvalidPostalCode() throws Exception {
        // Arrange
        String user = "";
        String postalCode = "123";

        // Act & Assert
        MvcResult result = mockMvc.perform(get("/app/history")
                        .param("user", user)
                        .param("postalCode", postalCode)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(WeatherException.INVALID_POSTAL_CODE_MSG));
    }

    @Test
    public void testSaveWeather_GenericException_CatchBlock() throws Exception {
        // Arrange
        WeatherRequestDTO validRequest = new WeatherRequestDTO();
        validRequest.setPostalCode("12345");
        validRequest.setUser("testUser");

        when(weatherService.saveWeather(any(WeatherRequestDTO.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/app/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(validRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(WeatherException.INTERNAL_SERVER_ERROR_MSG));
    }

    @Test
    public void testSaveWeather_UnexpectedException_ThrowsWeatherException() throws Exception {
        // Arrange
        WeatherRequestDTO validRequest = new WeatherRequestDTO();
        validRequest.setPostalCode("12345");
        validRequest.setUser("testUser");

        when(weatherService.saveWeather(any(WeatherRequestDTO.class)))
                .thenThrow(new WeatherException(WeatherException.INTERNAL_SERVER_ERROR, WeatherException.INTERNAL_SERVER_ERROR_MSG, LocalDateTime.now()));

        // Act & Assert
        MvcResult result = mockMvc.perform(post("/app/weather")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(validRequest)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(WeatherException.INTERNAL_SERVER_ERROR_MSG));
    }


    private WeatherRequestDTO createValidWeatherRequest() {
        WeatherRequestDTO request = new WeatherRequestDTO();
        request.setUser("testUser");
        request.setPostalCode("12345");
        return request;
    }

    private WeatherSummaryDTO createValidWeatherSummaryDTO() {
        WeatherSummaryDTO weatherSummaryDTO = new WeatherSummaryDTO();
        List<WeatherSummaryDTO.WeatherHistory> weatherHistory = new ArrayList<>();
        WeatherSummaryDTO.WeatherHistory history = new WeatherSummaryDTO.WeatherHistory();
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
        history.setLocation(location);
        history.setWeather(currentWeather);
        weatherHistory.add(history);
        weatherSummaryDTO.setHistory(weatherHistory);
        return weatherSummaryDTO;
    }
}
