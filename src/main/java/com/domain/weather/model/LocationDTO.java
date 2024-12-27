package com.domain.weather.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Represents the location information.
 * Contains information about the name, country, region, latitude, longitude, timezone ID,
 * local time, and UTC offset.
 *
 * @author Puja Chaudhury
 * @version 1.0
 * @since December 2024
 */
public final class LocationDTO {
    private final String name;
    private final String country;
    private final String region;
    private final String lat;
    private final String lon;
    private final String timezone_id;
    private final String localtime;
    private final long localtime_epoch;
    private final String utc_offset;

    // Private constructor to prevent direct instantiation
    private LocationDTO(Builder builder) {
        this.name = builder.name;
        this.country = builder.country;
        this.region = builder.region;
        this.lat = builder.lat;
        this.lon = builder.lon;
        this.timezone_id = builder.timezone_id;
        this.localtime = builder.localtime;
        this.localtime_epoch = builder.localtime_epoch;
        this.utc_offset = builder.utc_offset;
    }

    // Static factory method for Jackson deserialization
    @JsonCreator
    public static LocationDTO fromJson(
            @JsonProperty("name") String name,
            @JsonProperty("country") String country,
            @JsonProperty("region") String region,
            @JsonProperty("lat") String lat,
            @JsonProperty("lon") String lon,
            @JsonProperty("timezone_id") String timezone_id,
            @JsonProperty("localtime") String localtime,
            @JsonProperty("localtime_epoch") long localtime_epoch,
            @JsonProperty("utc_offset") String utc_offset) {

        return new Builder()
                .name(name)
                .country(country)
                .region(region)
                .lat(lat)
                .lon(lon)
                .timezone_id(timezone_id)
                .localtime(localtime)
                .localtime_epoch(localtime_epoch)
                .utc_offset(utc_offset)
                .build();
    }

    // Getters for all fields
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getTimezone_id() {
        return timezone_id;
    }

    public String getLocaltime() {
        return localtime;
    }

    public long getLocaltime_epoch() {
        return localtime_epoch;
    }

    public String getUtc_offset() {
        return utc_offset;
    }

    // Builder pattern class
    public static class Builder {
        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
        private String timezone_id;
        private String localtime;
        private long localtime_epoch;
        private String utc_offset;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder lat(String lat) {
            this.lat = lat;
            return this;
        }

        public Builder lon(String lon) {
            this.lon = lon;
            return this;
        }

        public Builder timezone_id(String timezone_id) {
            this.timezone_id = timezone_id;
            return this;
        }

        public Builder localtime(String localtime) {
            this.localtime = localtime;
            return this;
        }

        public Builder localtime_epoch(long localtime_epoch) {
            this.localtime_epoch = localtime_epoch;
            return this;
        }

        public Builder utc_offset(String utc_offset) {
            this.utc_offset = utc_offset;
            return this;
        }

        public LocationDTO build() {
            return new LocationDTO(this);
        }
    }

    // Override equals and hashcode for proper comparison and hashing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationDTO that = (LocationDTO) o;
        return localtime_epoch == that.localtime_epoch &&
                Objects.equals(name, that.name) &&
                Objects.equals(country, that.country) &&
                Objects.equals(region, that.region) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(lon, that.lon) &&
                Objects.equals(timezone_id, that.timezone_id) &&
                Objects.equals(localtime, that.localtime) &&
                Objects.equals(utc_offset, that.utc_offset);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country, region, lat, lon, timezone_id, localtime, localtime_epoch, utc_offset);
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                ", timezone_id='" + timezone_id + '\'' +
                ", localtime='" + localtime + '\'' +
                ", localtime_epoch=" + localtime_epoch +
                ", utc_offset='" + utc_offset + '\'' +
                '}';
    }
}
