package project.closet.service.recommend;

import java.util.UUID;
import project.closet.service.dto.response.RecommendationDto;

public interface WeatherOutfitRecommendationService {

    RecommendationDto getRecommendationForWeather(UUID weatherId);

}
