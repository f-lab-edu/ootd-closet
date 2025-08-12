package project.closet.service.exception.weather;

import project.closet.service.exception.ClosetException;
import project.closet.service.exception.ErrorCode;

public class WeatherException extends ClosetException {

    public WeatherException(ErrorCode errorCode) {
        super(errorCode);
    }

    public WeatherException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
