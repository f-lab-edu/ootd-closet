package project.closet.batch.api;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.closet.batch.api.response.WeatherApiResponse;
import project.closet.batch.api.response.WeatherItem;
import project.closet.weather.WeatherData;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeatherApiResponseConverter {

    public static List<WeatherData> parseToWeatherDataList(WeatherApiResponse apiResponse) {
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
}
