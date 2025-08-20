package project.closet.service.waether.basic;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.closet.api.AddressClient;
import project.closet.api.response.KakaoAddressResponse;
import project.closet.service.dto.response.WeatherAPILocation;
import project.closet.service.dto.response.WeatherDto;
import project.closet.service.waether.WeatherService;
import project.closet.weather.GeoGridConverter;
import project.closet.weather.GeoGridConverter.Grid;
import project.closet.weather.entity.Weather;
import project.closet.weather.repository.WeatherRepository;
import project.closet.weatherlocation.WeatherLocationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class BasicWeatherService implements WeatherService {
    private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

    private final AddressClient addressClient;
    private final WeatherRepository weatherRepository;
    private final WeatherLocationRepository weatherLocationRepository;

    private final GeoGridConverter geoGridConverter;

    @Transactional(readOnly = true)
    @Override
    public WeatherAPILocation getLocation(Double longitude, Double latitude) {
        log.info("위도 경도로 행정구역 반환 요청: longitude={}, latitude={}", longitude, latitude);
        KakaoAddressResponse kakaoAddressResponse =
            addressClient.requestAddressFromKakao(longitude, latitude);
        Grid grid = geoGridConverter.convert(latitude, longitude);
        return new WeatherAPILocation(
            latitude,
            longitude,
            grid.x(),
            grid.y(),
            kakaoAddressResponse.getLocationNames()
        );
    }

    // TODO 바로 전 날짜의 온도와 비교해서 온도 정보 반환
    @Transactional(readOnly = true)
    @Override
    public List<WeatherDto> getWeatherInfo(Double longitude, Double latitude) {
        // 1. 위도 경도 -> X,Y 좌표 변환
        Grid grid = geoGridConverter.convert(latitude, longitude);
        // 2. 변환된 XY 좌표로 날씨 데이터 호출
        LocalDate forecastDate = LocalDate.now().minusDays(1);
        LocalTime forecastTime = LocalTime.of(23, 0);
        Instant baseForecastedAt = LocalDateTime.of(forecastDate, forecastTime)
            .atZone(SEOUL)
            .toInstant();
        // 3. 날씨 정보 가공 후 반환
        List<Weather> weathers =
            weatherRepository.findAllByXAndYAndForecastedAtOrderByForecastAtAsc(grid.x(), grid.y(), baseForecastedAt);

        Map<LocalDate, List<Weather>> weathersByDate = weathers.stream()
            .collect(Collectors.groupingBy(weather -> weather.getForecastAt().atZone(SEOUL).toLocalDate()));

        LocalTime userTime = LocalTime.now(SEOUL); // 사용자의 현재 시간
        List<Weather> resultWeathers = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            LocalDate targetDate = LocalDate.now(SEOUL).plusDays(i);
            List<Weather> dailyWeathers = weathersByDate.get(targetDate);

            if (dailyWeathers == null || dailyWeathers.isEmpty()) {
                continue;
            }

            dailyWeathers.stream()
                .min(Comparator.comparingLong(w ->
                    Math.abs(ChronoUnit.MINUTES.between(
                        userTime, w.getForecastAt().atZone(SEOUL).toLocalTime())))
                )
                .ifPresent(resultWeathers::add);
        }

        // TODO 날씨 차이 로직 변경하기
        Map<Instant, Weather> weatherMapByForecastAt = resultWeathers.stream()
            .collect(Collectors.toMap(Weather::getForecastAt, w -> w));

        return resultWeathers.stream()
            .map(weather -> {
                Weather yesterday = weatherMapByForecastAt.get(
                    weather.getForecastAt().minus(1, ChronoUnit.DAYS));
                return WeatherDto.from(weather, yesterday);
            })
            .toList();
    }
}
