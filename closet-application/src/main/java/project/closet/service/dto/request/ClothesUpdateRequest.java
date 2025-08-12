package project.closet.service.dto.request;

import jakarta.validation.Valid;
import java.util.List;
import project.closet.clothes.entity.ClothesType;
import project.closet.service.dto.response.ClothesAttributeDto;

public record ClothesUpdateRequest(
    String name,

    ClothesType type,

    @Valid
    List<ClothesAttributeDto> attributes
) {

}
