package project.closet.service.exception.user;

import project.closet.service.exception.ClosetException;
import project.closet.service.exception.ErrorCode;

public class UserException extends ClosetException {

    protected UserException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected UserException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
