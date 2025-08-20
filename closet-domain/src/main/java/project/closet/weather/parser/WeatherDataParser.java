package project.closet.weather.parser;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import project.closet.weather.entity.AsWord;
import project.closet.weather.entity.PrecipitationType;
import project.closet.weather.entity.SkyStatus;
import project.closet.weather.entity.Weather;

@Slf4j
public class WeatherDataParser implements WeatherParser<Map<LocalDate, List<WeatherData2>>> {

    public static final ZoneId KST = ZoneId.of("Asia/Seoul");

    @Override
    public List<Weather> parseToWeatherEntities(Map<LocalDate, List<WeatherData2>> byDate) {

        if (byDate == null || byDate.isEmpty()) {
            log.info("기상청 데이터가 존재하지 않습니다.");
            return List.of();
        }

        List<Weather> weathers = new ArrayList<>();

        for (Map.Entry<LocalDate, List<WeatherData2>> entry : byDate.entrySet()) {

            List<WeatherData2> dayList = entry.getValue();
            if (dayList.isEmpty()) {
                continue;
            }

            WeatherData2 weatherDataFirst = dayList.get(0);
            int nx = weatherDataFirst.nx();
            int ny = weatherDataFirst.ny();

            Instant forecastedAt = LocalDateTime.of(weatherDataFirst.baseDate(), weatherDataFirst.baseTime())
                .atZone(KST)
                .toInstant();

            double maxTemperature = dayList.stream()
                .filter(weatherData -> filterByCategoryAndTime(weatherData, "TMX", LocalTime.of(15, 0)))
                .mapToDouble(weatherData -> Double.parseDouble(weatherData.fcstValue()))
                .findFirst()
                .orElse(0.0);

            double minTemperature = dayList.stream()
                .filter(weatherData -> filterByCategoryAndTime(weatherData, "TMN", LocalTime.of(6, 0)))
                .mapToDouble(weatherData -> Double.parseDouble(weatherData.fcstValue()))
                .findFirst()
                .orElse(0.0);

            Map<LocalTime, List<WeatherData2>> byTime = dayList.stream()
                .collect(Collectors.groupingBy(WeatherData2::fcstTime));

            LocalDate fcstDate = weatherDataFirst.fcstDate();

            for (Map.Entry<LocalTime, List<WeatherData2>> timeEntry : byTime.entrySet()) {
                LocalTime fcstTime = timeEntry.getKey();
                Instant forecastAt = LocalDateTime.of(fcstDate, fcstTime).atZone(KST).toInstant();

                List<WeatherData2> bucket = timeEntry.getValue();
                double pcp = findNumeric(bucket, "PCP");
                String skyCode = findRaw(bucket, "SKY");
                String ptyCode = findRaw(bucket, "PTY");
                double pop = findNumeric(bucket, "POP");
                double wsd = findNumeric(bucket, "WSD");
                double tmp =  findNumeric(bucket, "TMP");
                double reh =  findNumeric(bucket, "REH");

                if (skyCode == null || ptyCode == null) {
                    log.warn("누락된 값으로 인해 시간대 엔티티 스킵: date={}, time={}", fcstDate, fcstTime);
                    continue;
                }

                Weather weather = Weather.builder()
                    .forecastedAt(forecastAt)
                    .forecastAt(forecastAt)
                    .skyStatus(SkyStatus.fromCode(skyCode))
                    .amount(pcp)
                    .probability(pop)
                    .precipitationType(PrecipitationType.fromCode(ptyCode))
                    .windSpeed(wsd)
                    .asWord(AsWord.fromWindSpeed(wsd))
                    .humidity(reh)
                    .maxTemperature(maxTemperature)
                    .minTemperature(minTemperature)
                    .currentTemperature(tmp)
                    .x(nx)
                    .y(ny)
                    .build();

                weathers.add(weather);
            }
        }

        return weathers;
    }

    private boolean filterByCategoryAndTime(WeatherData2 weatherData, String category, LocalTime fcstTime) {
        return category.equals(weatherData.category()) && fcstTime.equals(weatherData.fcstTime());
    }

    private double findNumeric(List<WeatherData2> list, String category) {
        return list.stream()
            .filter(weatherData -> category.equals(weatherData.category()))
            .map(WeatherData2::fcstValue)
            .mapToDouble(Double::parseDouble)
            .findFirst()
            .orElse(0.0);
    }

    private String findRaw(List<WeatherData2> list, String category) {
        return list.stream()
            .filter(weatherData -> category.equals(weatherData.category()))
            .map(WeatherData2::fcstValue)
            .findFirst()
            .orElse(null);
    }
}
