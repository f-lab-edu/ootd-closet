package org.closet.common.exception.clothes.attribute;


import org.closet.common.exception.ClosetException;
import org.closet.common.exception.ErrorCode;

public class AttributeException extends ClosetException {

    protected AttributeException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected AttributeException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}
