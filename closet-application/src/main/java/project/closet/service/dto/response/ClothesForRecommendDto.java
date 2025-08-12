package project.closet.service.dto.response;

import java.util.List;
import java.util.UUID;
import project.closet.clothes.entity.Clothes;
import project.closet.clothes.entity.ClothesType;

public record ClothesForRecommendDto(
        UUID clothesId,
        String name,
        String imageUrl,
        ClothesType type,
        List<ClothesAttributeWithDefDto> attributes
) {

    public ClothesForRecommendDto(Clothes clothes, String imageUrl) {
        this(
                clothes.getId(),
                clothes.getName(),
                imageUrl,
                clothes.getType(),
                ClothesAttributeWithDefDto.fromClothes(clothes)
        );
    }
}
