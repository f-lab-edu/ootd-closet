package project.closet.service.exception.clothes.attribute;

import project.closet.service.exception.ClosetException;
import project.closet.service.exception.ErrorCode;

public class AttributeException extends ClosetException {

    protected AttributeException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected AttributeException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
