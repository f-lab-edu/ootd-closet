package project.closet.service.dto.response;

import java.util.List;
import java.util.UUID;

public record RecommendationDto(
        UUID weatherId,
        UUID userId,
        List<ClothesForRecommendDto> clothes
) {

}
