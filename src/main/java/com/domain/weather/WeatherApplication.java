package com.domain.weather;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Represents the main application for fetching and displaying weather information.
 * Handles the user input, retrieves weather data from the API, and displays the results.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Weather API Documentation",
				version = "1.0",
				description = "Spring Boot Application API Documentation"
		)
)
public class WeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

}
