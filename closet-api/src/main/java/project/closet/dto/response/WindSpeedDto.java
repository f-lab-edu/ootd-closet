package project.closet.dto.response;

import project.closet.entity.weather.AsWord;

public record WindSpeedDto(
    Double speed,
    AsWord asWord
) {

}
