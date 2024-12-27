package com.domain.weather.utility;

import com.domain.weather.entity.WeatherDetails;
import com.domain.weather.entity.WeatherEntity;
import com.domain.weather.exception.WeatherException;
import com.domain.weather.model.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Represents a test class for the `WeatherUtility`.
 * Contains test methods to verify the functionality of the `WeatherUtility`.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public class WeatherUtilityTest {

    @Test
    public void testValidatePostalCode_ValidPostalCode() {
        // Test case: Valid postal code
        String postalCode = "12345";
        boolean result = WeatherUtility.validatePostalCode(postalCode);
        assertTrue(result);
    }

    @Test
    public void testValidatePostalCode_InvalidPostalCode() {
        // Test case: Invalid postal code
        String postalCode = "123";
        boolean result = WeatherUtility.validatePostalCode(postalCode);
        assertFalse(result);
    }

    @Test
    public void testValidateUser_ValidUser() {
        // Test case: Valid user
        String user = "john doe";
        boolean result = WeatherUtility.validateUser(user);
        assertTrue(result);
    }

    @Test
    public void testValidateUser_InvalidUser() {
        // Test case: Invalid user
        String user = "123";
        boolean result = WeatherUtility.validateUser(user);
        assertFalse(result);
    }

    @Test
    public void testIsValidRequest_ValidUserAndPostalCode() {
        // Test case: Valid user and postal code
        String user = "john doe";
        String postalCode = "12345";
        boolean result = WeatherUtility.isValidRequest(user, postalCode);
        assertTrue(result);
    }

    @Test
    public void testIsValidRequest_InvalidUserAndValidPostalCode() {
        // Test case: Invalid user and valid postal code
        String user = "123";
        String postalCode = "123";
        boolean result = WeatherUtility.isValidRequest(user, postalCode);
        assertFalse(result);
    }

    @Test
    public void testIsValidRequest_InvalidUserAndInvalidPostalCode() {
        // Test case: Invalid user and invalid postal code
        String user = "123";
        String postalCode = "123";
        boolean result = WeatherUtility.isValidRequest(user, postalCode);
        assertFalse(result);
    }

    @Test
    public void testIsValidRequest_ValidUserAndBlankPostalCode() {
        // Test case: Valid user and blank postal code
        String user = "john doe";
        String postalCode = "";
        boolean result = WeatherUtility.isValidRequest(user, postalCode);
        assertTrue(result);
    }

    @Test
    public void testIsValidRequest_InvalidUserAndBlankPostalCode() {
        // Test case: Invalid user and blank postal code
        String user = "123";
        String postalCode = "";
        boolean result = WeatherUtility.isValidRequest(user, postalCode);
        assertFalse(result);
    }

    @Test
    public void testIsValidRequest_BlankUserAndValidPostalCode() {
        // Test case: Blank user and valid postal code
        String user = "";
        String postalCode = "12345";
        boolean result = WeatherUtility.isValidRequest(user, postalCode);
        assertTrue(result);
    }

    @Test
    public void testIsValidRequest_BlankUserAndInvalidPostalCode() {
        // Test case: Blank user and invalid postal code
        String user = "";
        String postalCode = "123";
        boolean result = WeatherUtility.isValidRequest(user, postalCode);
        assertFalse(result);
    }

    @Test
    public void testIsValidRequest_BlankUserAndBlankPostalCode() {
        // Test case: Blank user and blank postal code
        String user = "";
        String postalCode = "";
        boolean result = WeatherUtility.isValidRequest(user, postalCode);
        assertTrue(result);
    }

    @Test
    public void testCreateInvalidRequestException_ValidUser() {
        // Test case: Valid user
        String user = "john doe";
        WeatherException result = WeatherUtility.createInvalidRequestException(user, "");
        assertEquals(WeatherException.INVALID_USER_ERROR, result.getCode());
        assertEquals(WeatherException.INVALID_USER_MSG, result.getMessage());
    }

    @Test
    public void testCreateInvalidRequestException_ValidPostalCode() {
        // Test case: Valid postal code
        String user = "";
        String postalCode = "12345";
        WeatherException result = WeatherUtility.createInvalidRequestException(user, postalCode);
        assertEquals(WeatherException.INVALID_POSTAL_CODE_ERROR, result.getCode());
        assertEquals(WeatherException.INVALID_POSTAL_CODE_MSG, result.getMessage());
    }

    @Test
    public void testCreateInvalidRequestException_InvalidUserAndInvalidPostalCode() {
        // Test case: Invalid user and invalid postal code
        String user = "123";
        String postalCode = "123";
        WeatherException result = WeatherUtility.createInvalidRequestException(user, postalCode);
        assertEquals(WeatherException.INVALID_USER_ERROR, result.getCode());
        assertEquals(WeatherException.INVALID_USER_MSG, result.getMessage());
    }

    @Test
    public void testValidateWeather_ValidRequest() {
        // Test case: Valid weather request
        WeatherRequestDTO weatherRequestDTO = new WeatherRequestDTO();
        weatherRequestDTO.setPostalCode("12345");
        weatherRequestDTO.setUser("john doe");
        boolean result = WeatherUtility.validateWeather(weatherRequestDTO);
        assertTrue(result);
    }

    @Test
    public void testValidateWeather_InvalidUser() {
        // Test case: Invalid user
        WeatherRequestDTO weatherRequestDTO = new WeatherRequestDTO();
        weatherRequestDTO.setPostalCode("12345");
        weatherRequestDTO.setUser("john_doe");
        boolean result = WeatherUtility.validateWeather(weatherRequestDTO);
        assertFalse(result);
    }

    @Test
    public void testValidateWeather_InvalidPostalCode() {
        // Test case: Invalid postal code
        WeatherRequestDTO weatherRequestDTO = new WeatherRequestDTO();
        weatherRequestDTO.setPostalCode("123");
        weatherRequestDTO.setUser("john doe");
        boolean result = WeatherUtility.validateWeather(weatherRequestDTO);
        assertFalse(result);
    }

    @Test
    public void testValidateWeather_InvalidRequest() {
        // Test case: Invalid request
        WeatherRequestDTO weatherRequestDTO = new WeatherRequestDTO();
        boolean result = WeatherUtility.validateWeather(weatherRequestDTO);
        assertFalse(result);
    }

    @Test
    public void testConvertWeatherResponseToWeatherEntity_ValidResponse() {
        // Test case: Valid weather response
        WeatherResponseDTO responseDTO = getWeatherResponseDTO();
        WeatherEntity expectedEntity = new WeatherEntity();
        expectedEntity.setWeatherData(getWeatherDetails(responseDTO));

        WeatherEntity result = WeatherUtility.convertWeatherResponseToWeatherEntity(responseDTO);

        assertNotNull(result);
        assertEquals(expectedEntity.getWeatherData().getWindDegree(), result.getWeatherData().getWindDegree());
        assertEquals(expectedEntity.getWeatherData().getWindSpeed(), result.getWeatherData().getWindSpeed());
        assertEquals(expectedEntity.getWeatherData().getIsDay(), result.getWeatherData().getIsDay());
        assertEquals(expectedEntity.getWeatherData().getTemperature(), result.getWeatherData().getTemperature());
        assertEquals(expectedEntity.getWeatherData().getPrecipitation(), result.getWeatherData().getPrecipitation());
        assertEquals(expectedEntity.getWeatherData().getHumidity(), result.getWeatherData().getHumidity());
        assertEquals(expectedEntity.getWeatherData().getPressure(), result.getWeatherData().getPressure());
        assertEquals(expectedEntity.getWeatherData().getWeatherIconUrl(), result.getWeatherData().getWeatherIconUrl());
        assertEquals(expectedEntity.getWeatherData().getWeatherDescription(), result.getWeatherData().getWeatherDescription());
        assertEquals(expectedEntity.getWeatherData().getVisibility(), result.getWeatherData().getVisibility());
        assertEquals(expectedEntity.getWeatherData().getCountry(), result.getWeatherData().getCountry());
        assertEquals(expectedEntity.getWeatherData().getCity(), result.getWeatherData().getCity());
        assertEquals(expectedEntity.getWeatherData().getLatitude(), result.getWeatherData().getLatitude());
        assertEquals(expectedEntity.getWeatherData().getLongitude(), result.getWeatherData().getLongitude());
        assertEquals(expectedEntity.getWeatherData().getTimezoneId(), result.getWeatherData().getTimezoneId());
        assertEquals(expectedEntity.getWeatherData().getUtc_offset(), result.getWeatherData().getUtc_offset());
        assertEquals(expectedEntity.getWeatherData().getLocaltime(), result.getWeatherData().getLocaltime());
        assertEquals(expectedEntity.getWeatherData().getLocaltime_epoch(), result.getWeatherData().getLocaltime_epoch());
        assertEquals(expectedEntity.getWeatherData().getFeelsLike(), result.getWeatherData().getFeelsLike());
        assertEquals(expectedEntity.getWeatherData().getCloudCover(), result.getWeatherData().getCloudCover());
        assertEquals(expectedEntity.getWeatherData().getUvIndex(), result.getWeatherData().getUvIndex());
        assertEquals(expectedEntity.getWeatherData().getWindDirection(), result.getWeatherData().getWindDirection());
        assertEquals(expectedEntity.getWeatherData().getObservation_time(), result.getWeatherData().getObservation_time());
        assertEquals(expectedEntity.getWeatherData().getWeather_code(), result.getWeatherData().getWeather_code());
    }

    private WeatherResponseDTO getWeatherResponseDTO() {
        WeatherResponseDTO responseDTO = new WeatherResponseDTO();
        CurrentDTO currentDTO = new CurrentDTO.Builder()
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
        responseDTO.setCurrent(currentDTO);
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
        responseDTO.setLocation(location);
        return responseDTO;
    }

    private WeatherDetails getWeatherDetails(WeatherResponseDTO responseDTO) {
        WeatherDetails weatherDetails = new WeatherDetails();
        weatherDetails.setWindDegree(responseDTO.getCurrent().getWind_degree());
        weatherDetails.setWindSpeed(responseDTO.getCurrent().getWind_speed());
        weatherDetails.setIsDay(responseDTO.getCurrent().getIs_day());
        weatherDetails.setTemperature(responseDTO.getCurrent().getTemperature());
        weatherDetails.setPrecipitation(String.valueOf(responseDTO.getCurrent().getPrecip()));
        weatherDetails.setHumidity(responseDTO.getCurrent().getHumidity());
        weatherDetails.setPressure(responseDTO.getCurrent().getPressure());
        weatherDetails.setWeatherIconUrl(responseDTO.getCurrent().getWeather_icons().get(0));
        weatherDetails.setWeatherDescription(responseDTO.getCurrent().getWeather_descriptions().get(0));
        weatherDetails.setVisibility(responseDTO.getCurrent().getVisibility());
        weatherDetails.setCountry(responseDTO.getLocation().getCountry());
        weatherDetails.setCity(responseDTO.getLocation().getName());
        weatherDetails.setLatitude(responseDTO.getLocation().getLat());
        weatherDetails.setLongitude(responseDTO.getLocation().getLon());
        weatherDetails.setTimezoneId(responseDTO.getLocation().getTimezone_id());
        weatherDetails.setUtc_offset(responseDTO.getLocation().getUtc_offset());
        weatherDetails.setLocaltime(responseDTO.getLocation().getLocaltime());
        weatherDetails.setRegion(responseDTO.getLocation().getRegion());
        weatherDetails.setLocaltime_epoch(responseDTO.getLocation().getLocaltime_epoch());
        weatherDetails.setFeelsLike(responseDTO.getCurrent().getFeelslike());
        weatherDetails.setCloudCover(responseDTO.getCurrent().getCloudcover());
        weatherDetails.setUvIndex(responseDTO.getCurrent().getUv_index());
        weatherDetails.setWindDirection(responseDTO.getCurrent().getWind_dir());
        weatherDetails.setObservation_time(responseDTO.getCurrent().getObservation_time());
        weatherDetails.setWeather_code(responseDTO.getCurrent().getWeather_code());
    return weatherDetails;
}


    @Test
    public void testFetchWeatherFromAPI_Success() throws WeatherException {
        // Arrange
        WeatherRequestDTO requestDTO = new WeatherRequestDTO();
        requestDTO.setPostalCode("12345");

        // Mock the response from the API
        String mockResponse = "{\"current\":{\"temperature\":22,\"weather_code\":1000,\"weather_icons\":[\"clear-sky.png\"],\"weather_descriptions\":[\"Clear sky\"],\"wind_speed\":10,\"wind_degree\":180,\"wind_dir\":\"South\",\"pressure\":1015,\"precip\":0,\"humidity\":65,\"cloudcover\":10,\"feelslike\":21,\"uv_index\":5,\"visibility\":10,\"is_day\":\"yes\"},\"location\":{\"country\":\"USA\",\"name\":\"New York\",\"lat\":\"40.7128\",\"lon\":\"-74.0060\",\"region\":\"New York\",\"localtime\":\"2024-12-26 12:00:00\",\"timezone_id\":\"America/New_York\",\"utc_offset\":\"-05:00\",\"localtime_epoch\":1703592000}}";

        // Mock the static method call
        Mockito.mockStatic(WeatherWebClient.class);
        when(WeatherWebClient.CallWeatherAPI(requestDTO.getPostalCode())).thenReturn(mockResponse);

        // Act
        WeatherResponseDTO result = WeatherUtility.fetchWeatherFromAPI(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(22, result.getCurrent().getTemperature());
        assertEquals("USA", result.getLocation().getCountry());
        assertEquals("New York", result.getLocation().getName());
        // Add more assertions for other fields as needed
    }

    @Test
    public void testConvertWeatherEntityToWeather_Success() {
        // Arrange
        List<WeatherEntity> weatherEntities = new ArrayList<>();
        WeatherEntity weatherEntity = new WeatherEntity();
        WeatherDetails weatherDetails = new WeatherDetails();
        weatherDetails.setCity("New York");
        weatherDetails.setCountry("USA");
        weatherDetails.setLatitude("40.7128");
        weatherDetails.setLongitude("-74.0060");
        weatherDetails.setRegion("New York");
        weatherDetails.setTimezoneId("America/New_York");
        weatherDetails.setUtc_offset("-05:00");
        weatherDetails.setLocaltime_epoch(1703592000L);
        weatherDetails.setLocaltime("2024-12-26 12:00:00");
        weatherDetails.setObservation_time("2024-12-26 12:00:00");
        weatherDetails.setWeatherDescription("Clear sky");
        weatherDetails.setWeatherIconUrl("clear-sky.png");
        weatherDetails.setWeather_code(1000);
        weatherDetails.setWeatherIconUrl("clear-sky.png");
        weatherDetails.setWeatherDescription("Clear sky");
        weatherDetails.setTemperature(22);
        weatherDetails.setPrecipitation("0");
        weatherDetails.setHumidity(65);
        weatherDetails.setPressure(1015);
        weatherDetails.setCloudCover(10);
        weatherDetails.setUvIndex(5);
        weatherDetails.setWindDirection("South");
        weatherDetails.setWindDegree(180);
        weatherDetails.setWindSpeed(10);
        weatherDetails.setIsDay("yes");
        weatherDetails.setVisibility(10);
        weatherDetails.setFeelsLike(21);
        weatherEntity.setUser("john doe");
        weatherEntity.setPostalCode("12345");
        weatherEntity.setTimestamp(LocalDateTime.now());
        weatherEntity.setWeatherData(weatherDetails);
        weatherEntities.add(weatherEntity);

        // Act
        WeatherSummaryDTO result = WeatherUtility.convertWeatherEntityToWeather(weatherEntities);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getHistory().size());
    }

    @Test
    public void testConvertWeatherEntityToWeatherHistory_ValidEntity() {
        // Arrange
        WeatherEntity weatherEntity = new WeatherEntity();
        WeatherDetails weatherDetails = new WeatherDetails();
        weatherDetails.setCity("New York");
        weatherDetails.setCountry("USA");
        weatherDetails.setLatitude("40.7128");
        weatherDetails.setLongitude("-74.0060");
        weatherDetails.setRegion("New York");
        weatherDetails.setTimezoneId("America/New_York");
        weatherDetails.setUtc_offset("-05:00");
        weatherDetails.setLocaltime_epoch(1703592000L);
        weatherDetails.setLocaltime("2024-12-26 12:00:00");
        weatherDetails.setObservation_time("2024-12-26 12:00:00");
        weatherDetails.setWeatherDescription("Clear sky");
        weatherDetails.setWeatherIconUrl("clear-sky.png");
        weatherDetails.setWeather_code(1000);
        weatherDetails.setTemperature(22);
        weatherDetails.setPrecipitation("0");
        weatherDetails.setHumidity(65);
        weatherDetails.setPressure(1015);
        weatherDetails.setCloudCover(10);
        weatherDetails.setUvIndex(5);
        weatherDetails.setWindDirection("South");
        weatherDetails.setWindDegree(180);
        weatherDetails.setWindSpeed(10);
        weatherDetails.setIsDay("yes");
        weatherDetails.setVisibility(10);
        weatherDetails.setFeelsLike(21);
        weatherEntity.setUser("john_doe");
        weatherEntity.setPostalCode("12345");
        weatherEntity.setTimestamp(LocalDateTime.now());
        weatherEntity.setWeatherData(weatherDetails);

        // Act
        WeatherSummaryDTO.WeatherHistory weatherHistory = WeatherUtility.convertWeatherEntityToWeatherHistory(weatherEntity);

        // Assert
        assertNotNull(weatherHistory);
        assertEquals("New York", weatherHistory.getLocation().getName());
        assertEquals("USA", weatherHistory.getLocation().getCountry());
        assertEquals("40.7128", weatherHistory.getLocation().getLat());
        assertEquals("-74.0060", weatherHistory.getLocation().getLon());
        assertEquals("New York", weatherHistory.getLocation().getRegion());
        assertEquals("America/New_York", weatherHistory.getLocation().getTimezone_id());
        assertEquals("-05:00", weatherHistory.getLocation().getUtc_offset());
        assertEquals("2024-12-26 12:00:00", weatherHistory.getLocation().getLocaltime());
        assertEquals(1703592000L, weatherHistory.getLocation().getLocaltime_epoch());
        assertEquals("2024-12-26 12:00:00", weatherHistory.getWeather().getObservation_time());
        assertEquals("Clear sky", weatherHistory.getWeather().getWeather_descriptions().get(0));
        assertEquals("clear-sky.png", weatherHistory.getWeather().getWeather_icons().get(0));
        assertEquals(1000, weatherHistory.getWeather().getWeather_code());
        assertEquals(22, weatherHistory.getWeather().getTemperature());
        assertEquals(0, weatherHistory.getWeather().getPrecip());
        assertEquals(65, weatherHistory.getWeather().getHumidity());
        assertEquals(1015, weatherHistory.getWeather().getPressure());
        assertEquals(10, weatherHistory.getWeather().getCloudcover());
        assertEquals(5, weatherHistory.getWeather().getUv_index());
        assertEquals("South", weatherHistory.getWeather().getWind_dir());
        assertEquals(180, weatherHistory.getWeather().getWind_degree());
        assertEquals(10, weatherHistory.getWeather().getWind_speed());
        assertEquals("yes", weatherHistory.getWeather().getIs_day());
        assertEquals(10, weatherHistory.getWeather().getVisibility());
        assertEquals(21, weatherHistory.getWeather().getFeelslike());
    }
}