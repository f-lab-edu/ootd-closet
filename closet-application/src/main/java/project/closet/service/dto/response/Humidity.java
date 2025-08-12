package project.closet.service.dto.response;

public record Humidity(
    Double current,
    Double comparedToDayBefore
) {

}
