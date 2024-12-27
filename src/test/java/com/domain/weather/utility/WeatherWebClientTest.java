package com.domain.weather.utility;

import com.domain.weather.exception.WeatherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Represents a test class for the `WeatherWebClient`.
 * Contains test methods to verify the functionality of the `WeatherWebClient`.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public class WeatherWebClientTest {

    @BeforeEach
    void setUp() {
        WeatherWebClient weatherWebClient = new WeatherWebClient();
    }

    @Test
    void testCallWeatherAPI_ValidPostalCode() throws IOException, WeatherException {
        // Arrange
        String postalCode = "12345";
        String expectedResponse = "{\"key\":\"value\"}";

        // Mock the HTTP connection
        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        when(mockConnection.getInputStream()).thenReturn(new ByteArrayInputStream(expectedResponse.getBytes()));

        // Mock the URL connection
        URL mockUrl = Mockito.mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        // Act
        String result = WeatherWebClient.CallWeatherAPI(postalCode);

        // Assert
        assertNotNull(result);
    }

    @Test
    void testCallWeatherAPI_InvalidPostalCode() throws IOException, WeatherException {
        // Arrange
        String postalCode = "invalid";

        // Mock the HTTP connection
        HttpURLConnection mockConnection = Mockito.mock(HttpURLConnection.class);
        when(mockConnection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_BAD_REQUEST);

        // Mock the URL connection
        URL mockUrl = Mockito.mock(URL.class);
        when(mockUrl.openConnection()).thenReturn(mockConnection);

        // Act
        String result = WeatherWebClient.CallWeatherAPI(postalCode);

        // Assert
        assertNotNull("", result);
    }
}