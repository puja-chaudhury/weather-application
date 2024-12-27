package com.domain.weather.utility;

import com.domain.weather.exception.WeatherException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * WeatherWebClient is a utility class for making HTTP requests to the Weather API.
 * It provides a method to call the Weather API and retrieve weather details for a given postal code.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@Component
public class WeatherWebClient {

    private static final Logger logger = LoggerFactory.getLogger(WeatherWebClient.class);

    @Value("${apiKey}")
    private String apiKey;

    private static String staticApiKey;

    @PostConstruct
    public void init() {
        staticApiKey = apiKey;
    }

    /**
     * Calls the Weather API and retrieves weather details for a given postal code.
     *
     * @param postalCode the postal code to retrieve weather details for
     * @return the weather details as a JSON string
     * @throws WeatherException if there is an error with the request or retrieving the weather data
     */
    public static String CallWeatherAPI(String postalCode) throws WeatherException {
        StringBuilder response = new StringBuilder();

        // Step 1: Define the API endpoint URL
        String urlString = "https://api.weatherstack.com/current?access_key=" + staticApiKey + "&query=" + postalCode;

        try {
            // Step 1: Create a URL object
            URL url = new URL(urlString);

            // Step 2: Open the HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Step 3: Set the request method to GET
            connection.setRequestMethod("GET");

            // Step 4: Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Step 5: Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;

                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                if (response.toString().contains("error")) {
                    logger.error("Error while calling Weather API: {}", response.toString());
                    throw new WeatherException(
                            WeatherException.INVALID_ACCESS_KEY,
                            WeatherException.INVALID_ACCESS_KEY_MSG,
                            LocalDateTime.now()
                    );
                }
                logger.info("Weather API Response: {}", response.toString());

            } else {
                logger.error("GET request failed. HTTP Error Code: {}", responseCode);
            }

        } catch (Exception e) {
            logger.error("Error while calling Weather API: {}", e.getMessage(), e);
            throw new WeatherException(
                    WeatherException.INTERNAL_SERVER_ERROR,
                    WeatherException.INTERNAL_SERVER_ERROR_MSG,
                    LocalDateTime.now()
            );
        }
        return response.toString();
    }
}