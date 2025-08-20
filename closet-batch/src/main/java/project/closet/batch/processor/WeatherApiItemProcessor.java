package project.closet.batch.processor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import project.closet.api.WeatherAPIClient;
import project.closet.api.WeatherApiResponseConverter;
import project.closet.api.response.WeatherApiResponse;
import project.closet.weather.entity.Weather;
import project.closet.weather.parser.WeatherData;
import project.closet.weather.parser.WeatherDataParser;
import project.closet.weatherlocation.WeatherLocation;

@Component
@RequiredArgsConstructor
@StepScope
public class WeatherApiItemProcessor implements ItemProcessor<WeatherLocation, List<Weather>> {

    private final WeatherAPIClient weatherAPIClient;
    private final WeatherDataParser weatherDataParser;
    private final WeatherApiResponseConverter weatherApiResponseConverter;

    @Override
    public List<Weather> process(WeatherLocation weatherLocation) throws Exception {
        LocalDate baseDate = LocalDate.now().minusDays(1);
        LocalTime forecastTime = LocalTime.of(23, 0);
        Instant forecastedAt = baseDate
            .atTime(forecastTime)
            .atZone(ZoneId.of("Asia/Seoul"))
            .toInstant();

        // RestTemplate 호출
        WeatherApiResponse apiResponse = weatherAPIClient.getWeatherRawData(
            weatherLocation.getX(),
            weatherLocation.getY(),
            baseDate,
            forecastTime
        );
        // api 응답을 시스템이 가지는 스펙으로 컨버터
        Map<LocalDate, List<WeatherData>> weatherData =
            weatherApiResponseConverter.parseToWeatherDataList(apiResponse);

        // 도메인 엔티티로 변경 -> 하나의 좌표에 여러 개의 날씨 시간 데이터
        return weatherDataParser.parseToWeatherEntities(weatherData);
    }
}
