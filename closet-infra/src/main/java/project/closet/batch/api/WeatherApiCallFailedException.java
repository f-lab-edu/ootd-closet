package project.closet.batch.api;

public class WeatherApiCallFailedException extends RuntimeException {
    public WeatherApiCallFailedException(String message) {
        super(message);
    }

    public WeatherApiCallFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
