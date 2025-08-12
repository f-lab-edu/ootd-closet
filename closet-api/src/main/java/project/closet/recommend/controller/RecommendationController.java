package project.closet.recommend.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.closet.service.dto.response.RecommendationDto;
import project.closet.service.recommend.WeatherOutfitRecommendationService;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final WeatherOutfitRecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<RecommendationDto> getRecommendations(
            @RequestParam("weatherId") UUID weatherId
    ) {
        RecommendationDto dto =
                recommendationService.getRecommendationForWeather(weatherId);
        return ResponseEntity.ok(dto);
    }

}
