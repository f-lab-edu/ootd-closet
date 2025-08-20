package project.closet.api.response;

public record WeatherApiResponse(Response response) {

    public static WeatherApiResponse empty() {
        return new WeatherApiResponse(null);
    }
}
