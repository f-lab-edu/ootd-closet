package org.closet.common.exception.clothes;

import java.util.UUID;
import org.closet.common.exception.ErrorCode;

public class ClothesNotFoundException extends ClothesException {

    protected ClothesNotFoundException() {
        super(ErrorCode.CLOTHES_NOT_FOUND);
    }

    public static ClothesNotFoundException withId(UUID clothesId) {
        ClothesNotFoundException exception = new ClothesNotFoundException();
        exception.addDetail("clothesId", clothesId);
        return exception;
    }
}
