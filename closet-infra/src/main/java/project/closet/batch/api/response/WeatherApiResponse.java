package project.closet.batch.api.response;

public record WeatherApiResponse(Response response) {

    public static WeatherApiResponse empty() {
        return new WeatherApiResponse(null);
    }
}
