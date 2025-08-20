package project.closet.weather.parser;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;

@Builder
public record WeatherData2(
    LocalDate baseDate,
    LocalTime baseTime,
    LocalDate fcstDate,
    LocalTime fcstTime,
    String category,
    String fcstValue,
    int nx,
    int ny
) {
}
