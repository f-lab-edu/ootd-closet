package org.closet.common.exception.weather;


import org.closet.common.exception.ClosetException;
import org.closet.common.exception.ErrorCode;

public class WeatherException extends ClosetException {

    public WeatherException(ErrorCode errorCode) {
        super(errorCode);
    }

    public WeatherException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
