package project.closet.service.dto.response;

import java.util.List;

public record WeatherAPILocation(
    Double latitude,
    Double longitude,
    Integer x,
    Integer y,
    List<String> locationNames
) {

}
