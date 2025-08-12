package project.closet.service.dto.response;

import java.util.List;
import java.util.UUID;
import project.closet.clothes.entity.Clothes;
import project.closet.service.clothes.ClothesTypeCode;
import project.closet.service.mapper.ClothesTypeMapper;


public record ClothesDto(
    UUID id,
    UUID ownerId,
    String name,
    String imageUrl,
    ClothesTypeCode type,
    List<ClothesAttributeWithDefDto> attributes // 의상 속성
) {
    public static ClothesDto fromEntity(Clothes c, String imageUrl) {
        List<ClothesAttributeWithDefDto> attrs =
            ClothesAttributeWithDefDto.fromClothes(c);

        return new ClothesDto(
            c.getId(),
            c.getOwner().getId(),
            c.getName(),
            imageUrl,
            ClothesTypeMapper.toCode(c.getType()),
            attrs
        );
    }

    /**
     * OG/스크래핑 전용 생성자
     */
    public static ClothesDto extraction(String name, String imageUrl) {
        return new ClothesDto(null, null, name, imageUrl, null, null);
    }
}
