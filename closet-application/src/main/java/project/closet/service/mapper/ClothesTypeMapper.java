package project.closet.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.closet.clothes.entity.ClothesType;
import project.closet.service.clothes.ClothesTypeCode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClothesTypeMapper {

    public static ClothesType toDomain(ClothesTypeCode code) {
        return (code == null) ? null : ClothesType.valueOf(code.name());
    }

    public static ClothesTypeCode toCode(ClothesType domain) {
        return (domain == null) ? null : ClothesTypeCode.valueOf(domain.name());
    }
}
