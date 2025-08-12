package project.closet.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.closet.service.feed.PrecipitationTypeCode;
import project.closet.weather.entity.PrecipitationType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrecipitationTypeMapper {

    public static PrecipitationType toDomain(PrecipitationTypeCode code) {
        return (code == null) ? null : PrecipitationType.valueOf(code.name());
    }
    
    public static PrecipitationTypeCode toCode(PrecipitationType type) {
        return (type == null) ? null : PrecipitationTypeCode.valueOf(type.name());
    }
}
