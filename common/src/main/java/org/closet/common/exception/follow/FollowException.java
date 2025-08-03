package org.closet.common.exception.follow;


import org.closet.common.exception.ClosetException;
import org.closet.common.exception.ErrorCode;

public class FollowException extends ClosetException {

    public FollowException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FollowException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
