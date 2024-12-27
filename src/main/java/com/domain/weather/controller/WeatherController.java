package com.domain.weather.controller;

import com.domain.weather.exception.WeatherException;
import com.domain.weather.model.WeatherRequestDTO;
import com.domain.weather.model.WeatherSummaryDTO;
import com.domain.weather.service.WeatherService;
import com.domain.weather.utility.WeatherUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * WeatherController is a REST controller responsible for handling weather-related API requests.
 * It uses the WeatherService to retrieve weather details for a given postal code.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */

@RestController
@RequestMapping("/app")
@Tag(name = "Weather Controller", description = "Operations related to Weather API")
public class WeatherController {

    private static final Logger logger = LogManager.getLogger(WeatherController.class);

    @Autowired
    private WeatherService weatherService;

    /**
     * Saves weather details for a given postal code.
     *
     * @param weatherRequestDTO the weather request DTO
     * @return the weather summary DTO
     * @throws WeatherException if there is an error with the request or retrieving the weather data
     */
    @PostMapping("/weather")
    @Operation(
            summary = "Save Weather",
            description = "Returns weather details for a given postal code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherSummaryDTO.WeatherHistory.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherException.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherException.class)))
            })
    public ResponseEntity<Object> saveWeather(@Valid @RequestBody WeatherRequestDTO weatherRequestDTO) throws WeatherException {
        logger.debug("Processing weather save request: {}", weatherRequestDTO);

        if (!WeatherUtility.validateWeather(weatherRequestDTO)) {
            logger.warn("Invalid weatherRequestDTO: {}", weatherRequestDTO);
            throw new WeatherException(
                    WeatherException.INVALID_REQUEST_ERROR,
                    WeatherException.INVALID_REQUEST_MSG,
                    LocalDateTime.now()
            );
        }

        try {
            WeatherSummaryDTO.WeatherHistory savedWeather = weatherService.saveWeather(weatherRequestDTO);
            logger.info("Successfully saved weather data: {}", savedWeather);
            return ResponseEntity.ok(savedWeather);

        } catch (WeatherException e) {
            logger.error("Error saving weather data for request: {}", weatherRequestDTO, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error saving weather data: {}", weatherRequestDTO, e);
            throw new WeatherException(
                    WeatherException.INTERNAL_SERVER_ERROR,
                    WeatherException.INTERNAL_SERVER_ERROR_MSG,
                    LocalDateTime.now()
            );
        }
    }

    /**
     * Retrieves weather details for a given postal code.
     *
     * @param postalCode the postal code
     * @return the weather summary DTO
     * @throws WeatherException if there is an error with the request or retrieving the weather data
     */
    @GetMapping("/history")
    @Operation(
            summary = "Get Weather History",
            description = "Returns weather history details for a given user or/and postal code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherSummaryDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherException.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherException.class)))
            })
    public ResponseEntity<Object> getWeather(@RequestParam(name = "user", required = false) String user,
                                             @RequestParam(name = "postalCode", required = false) String postalCode) throws WeatherException {

        logger.debug("Getting weather for user: {}, postal code: {}", user, postalCode);

        try {
            if (WeatherUtility.isValidRequest(user, postalCode)) {
                logger.debug("Retrieving weather data for user: {}, postal code: {}", user, postalCode);
                WeatherSummaryDTO weatherSummaryDTO = weatherService.getWeather(user, postalCode);
                logger.debug("Retrieved weather data: {}", weatherSummaryDTO);
                return ResponseEntity.ok(weatherSummaryDTO);
            }

            WeatherException exception = WeatherUtility.createInvalidRequestException(user, postalCode);
            logger.debug("Invalid request: {}", exception.getMessage());
            throw exception;

        } catch (WeatherException e) {
            logger.error("Error retrieving weather for user: {}, postal code: {}", user, postalCode, e);
            throw e;
        }
    }
    }
