package org.closet.common.exception.clothes;


import org.closet.common.exception.ClosetException;
import org.closet.common.exception.ErrorCode;

public class ClothesException extends ClosetException {

    protected ClothesException(ErrorCode errorCode) {
        super(errorCode);
    }

    protected ClothesException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
