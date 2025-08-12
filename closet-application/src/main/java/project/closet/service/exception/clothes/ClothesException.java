package project.closet.service.exception.clothes;

import project.closet.service.exception.ClosetException;
import project.closet.service.exception.ErrorCode;

public class ClothesException extends ClosetException {

    protected ClothesException(ErrorCode errorCode) {
        super(errorCode);
    }
    protected ClothesException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
