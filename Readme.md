# Weather Service API 🌦️

A Spring Boot application that provides weather data and history by integrating with external APIs.

## 🚀 Features
Save weather details by postal code
Retrieve weather history for a user or postal code
Custom exception handling for API responses
Dockerized for easy deployment

## 📋 Prerequisites
Java 11
Gradle
Docker (optional for containerization)

## 🛠️ Installation & Running
Clone the Repository:
git clone https://github.com/puja-chaudhury/weather-application.git
cd weather-application

**Steps to Create an Access Key from Weatherstack:**
1. Go to [weatherstack.com](https://weatherstack.com) and sign up or log in.
2. Access your Dashboard after logging in.
3. Navigate to the API Keys or API Access section.
4. Click Generate API Key to create a new key.
5. Copy the generated key for use in your application.
6. Review pricing and usage limits based on your selected plan.

## Build the Project with Gradle:
./gradlew build

## Run the Application:
java -jar build/libs/weather-application-0.0.1-SNAPSHOT.jar

## Docker (Optional):
docker build -t weather-application .
docker run -p 8080:8080 weather-application

## 🔍 API Documentation
http://localhost:8080/swagger-ui/index.html

## 🔍 API Endpoints  
Save Weather Data

POST /app/weather  
Description:Save weather details for a given postal code.  
Responses:  
200 – Success (Returns WeatherSummaryDTO.WeatherHistory)  
400 – Invalid Request (WeatherException)  
500 – Internal Server Error (WeatherException)  

Get Weather History  

GET /app/history  
Description:Retrieve weather history details for a user and/or postal code.  
Query Parameters:  
user – (optional) User identifier  
postalCode – (optional) Postal code  
Responses:  
200 – Success (Returns WeatherSummaryDTO)  
400 – Bad Request (WeatherException)  
500 – Internal Server Error (WeatherException)  

## 🧬 Exception Handling

The application handles errors gracefully and returns structured JSON error responses:  
W-0001: Invalid postal code  
W-0002: User not found  
W-0003: Resource not found  
W-0004: Internal server error  
W-0005: Invalid request body  
W-0006: Invalid access key  

## 📝 License
MIT License