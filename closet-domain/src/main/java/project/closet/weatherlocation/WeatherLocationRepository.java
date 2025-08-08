package project.closet.weatherlocation;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherLocationRepository extends JpaRepository<WeatherLocation, UUID> {

    boolean existsByXAndY(int x, int y);
}
