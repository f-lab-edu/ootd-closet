package project.closet.service.dto.response;

public record TemperatureDto(
    Double current,
    Double comparedToDayBefore,
    Double min,
    Double max
) {

}
