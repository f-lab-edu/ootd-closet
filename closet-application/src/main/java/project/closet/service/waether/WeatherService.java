package project.closet.service.waether;

import java.util.List;
import project.closet.service.dto.response.WeatherAPILocation;
import project.closet.service.dto.response.WeatherDto;

public interface WeatherService {

    // 위도 경도로 행정구역 반환해주는 메소드
    WeatherAPILocation getLocation(Double longitude, Double latitude);

    // 날씨 정보 조회 요청 메소드
    List<WeatherDto> getWeatherInfo(Double longitude, Double latitude);
}
