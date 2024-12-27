package com.domain.weather.jpa;

import com.domain.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Represents a repository for weather entities.
 * Provides CRUD operations for weather entities.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {

    WeatherEntity findByUserOrPostalCode(String user, String postalCode);

    List<WeatherEntity> findByUserAndPostalCode(String user, String postalCode);

    List<WeatherEntity> findByUser(String user);

    List<WeatherEntity> findByPostalCode(String postalCode);
}
