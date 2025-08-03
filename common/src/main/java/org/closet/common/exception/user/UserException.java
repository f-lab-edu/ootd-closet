package org.closet.common.exception.user;


import org.closet.common.exception.ClosetException;
import org.closet.common.exception.ErrorCode;

public class UserException extends ClosetException {

    protected UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected UserException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
