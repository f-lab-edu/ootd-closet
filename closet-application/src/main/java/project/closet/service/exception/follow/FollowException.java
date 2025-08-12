package project.closet.service.exception.follow;

import project.closet.service.exception.ClosetException;
import project.closet.service.exception.ErrorCode;

public class FollowException extends ClosetException {

    public FollowException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FollowException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
