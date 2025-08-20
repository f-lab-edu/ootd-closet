package project.closet.batch.api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import project.closet.batch.api.response.WeatherApiResponse;
import project.closet.batch.api.response.WeatherItem;
import project.closet.weather.WeatherData;
import project.closet.weather.parser.WeatherData2;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherApiResponseConverter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");

    public List<WeatherData> parseToWeatherDataList(WeatherApiResponse apiResponse) {
        if (apiResponse == null || apiResponse.response() == null
            || apiResponse.response().body() == null
            || apiResponse.response().body().items() == null) {
            return List.of();
        }

        List<WeatherItem> items = apiResponse.response().body().items().item();
        return items.stream()
            .map(item -> {
                return WeatherData.builder()
                    .baseDate(item.baseDate())
                    .baseTime(item.baseTime())
                    .category(item.category())
                    .fcstDate(item.fcstDate())
                    .fcstTime(item.fcstTime())
                    .fcstValue(item.fcstValue())
                    .nx(item.nx())
                    .ny(item.ny())
                    .build();
            })
            .toList();
    }

    public Map<LocalDate, List<WeatherData2>> parseToWeatherDataList2(WeatherApiResponse response) {

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
            .collect(Collectors.groupingBy(WeatherData2::fcstDate));
    }

    private WeatherData2 parseToDto(WeatherItem item) {
        return WeatherData2.builder()
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
