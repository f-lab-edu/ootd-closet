package org.closet.common.exception.follow;

import java.util.UUID;
import org.closet.common.exception.ErrorCode;

public class FollowNotFoundException extends FollowException {

    public FollowNotFoundException() {
        super(ErrorCode.FOLLOW_NOT_FOUND);
    }

    public static FollowNotFoundException withId(UUID followId) {
        FollowNotFoundException exception = new FollowNotFoundException();
        exception.addDetail("followId", followId);
        return exception;
    }
}
