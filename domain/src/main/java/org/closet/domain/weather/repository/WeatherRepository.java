package org.closet.domain.weather.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.closet.domain.weather.entity.Weather;

public interface WeatherRepository {
    Weather save(Weather weather);

    Weather findById(UUID id);

    List<Weather> findAllByXAndYAndForecastedAtOrderByForecastAtAsc(
        Integer x,
        Integer y,
        Instant forecastedAt
    );

    boolean existsByForecastedAt(Instant forecastedAt);
}
