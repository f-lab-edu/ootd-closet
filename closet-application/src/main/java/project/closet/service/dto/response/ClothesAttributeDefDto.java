package project.closet.service.dto.response;

import java.util.List;
import java.util.UUID;
import project.closet.attributes.entity.Attribute;
import project.closet.attributes.entity.AttributeSelectableValue;


public record ClothesAttributeDefDto(
    UUID id,
    String name,
    List<String> selectableValues
) {
    public static ClothesAttributeDefDto of(Attribute e) {
        List<String> values = e.getSelectableValues().stream()
            .map(AttributeSelectableValue::getValue)
            .toList();

        return new ClothesAttributeDefDto(
            e.getId(),
            e.getDefinitionName(),
            values
        );
    }
}
