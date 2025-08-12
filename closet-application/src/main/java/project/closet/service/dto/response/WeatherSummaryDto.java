package project.closet.service.dto.response;

import java.util.UUID;
import project.closet.service.feed.SkyStatusCode;
import project.closet.service.mapper.SkyStatusMapper;
import project.closet.weather.entity.SkyStatus;
import project.closet.weather.entity.Weather;

public record WeatherSummaryDto(
    UUID weatherId,
    SkyStatusCode skyStatus,
    PrecipitationDto precipitation,
    TemperatureDto temperature
) {

    public static WeatherSummaryDto from(Weather weather) {
        return new WeatherSummaryDto(
            weather.getId(),
            SkyStatusMapper.toCode(weather.getSkyStatus()),
            PrecipitationDto.from(weather),
            new TemperatureDto(
                weather.getCurrentTemperature(),
                0.0,
                weather.getMinTemperature(),
                weather.getMaxTemperature()
            )
        );
    }
}
