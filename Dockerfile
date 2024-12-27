# Use an OpenJDK base image
FROM openjdk:11-jdk-slim

# Set environment variables
ENV APP_HOME=/usr/app/

# Create app directory
WORKDIR $APP_HOME

# Copy the Spring Boot jar to the container
COPY target/weather-app-0.0.1-SNAPSHOT.jar weather.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the jar
ENTRYPOINT ["java","-jar","weather.jar"]
