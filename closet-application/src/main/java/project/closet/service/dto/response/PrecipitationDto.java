package project.closet.service.dto.response;

import project.closet.service.feed.PrecipitationTypeCode;
import project.closet.service.mapper.PrecipitationTypeMapper;
import project.closet.weather.entity.PrecipitationType;
import project.closet.weather.entity.Weather;

public record PrecipitationDto(
    PrecipitationTypeCode type,
    Double amount,
    Double probability
) {

    public static PrecipitationDto from(Weather weather) {
        return new PrecipitationDto(
            PrecipitationTypeMapper.toCode(weather.getPrecipitationType()),
            weather.getAmount(),
            weather.getProbability() / 100.0
        );
    }
}
