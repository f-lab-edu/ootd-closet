package project.closet.dto.response;

import project.closet.entity.weather.PrecipitationType;
import project.closet.entity.weather.Weather;

public record PrecipitationDto(
    PrecipitationType type,
    Double amount,
    Double probability
) {

    public static PrecipitationDto from(Weather weather) {
        return new PrecipitationDto(
            weather.getPrecipitationType(),
            weather.getAmount(),
            weather.getProbability() / 100.0
        );
    }
}
