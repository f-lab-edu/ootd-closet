package org.closet.common.exception.feed;


import org.closet.common.exception.ClosetException;
import org.closet.common.exception.ErrorCode;

public class FeedException extends ClosetException {

    public FeedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FeedException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
