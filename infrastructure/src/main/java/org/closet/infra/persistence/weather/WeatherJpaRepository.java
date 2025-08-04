package org.closet.infra.persistence.weather;

import java.util.UUID;
import org.closet.domain.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherJpaRepository extends JpaRepository<Weather, UUID> {
}
