package project.closet.api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import project.closet.api.response.WeatherApiResponse;
import project.closet.api.response.WeatherItem;
import project.closet.weather.parser.WeatherData;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherApiResponseConverter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    public Map<LocalDate, List<WeatherData>> parseToWeatherDataList(WeatherApiResponse response) {

        if (
            response == null
                || response.response() == null
                || response.response().body() == null
                || response.response().body().items() == null
        ) {
            return Map.of();
        }

        List<WeatherItem> items = response.response().body().items().item();
        return items.stream()
            .map(this::parseToDto)
            .collect(Collectors.groupingBy(WeatherData::fcstDate));
    }

    private WeatherData parseToDto(WeatherItem item) {
        return WeatherData.builder()
            .baseDate(LocalDate.parse(item.baseDate(), DATE_FORMATTER))
            .baseTime(LocalTime.parse(item.baseTime(), TIME_FORMATTER))
            .category(item.category())
            .fcstDate(LocalDate.parse(item.fcstDate(), DATE_FORMATTER))
            .fcstTime(LocalTime.parse(item.fcstTime(), TIME_FORMATTER))
            .fcstValue(item.fcstValue())
            .nx(item.nx())
            .ny(item.ny())
            .build();
    }
}
