package project.closet.service.exception.feed;

import project.closet.service.exception.ClosetException;
import project.closet.service.exception.ErrorCode;

public class FeedException extends ClosetException {

    public FeedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FeedException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
