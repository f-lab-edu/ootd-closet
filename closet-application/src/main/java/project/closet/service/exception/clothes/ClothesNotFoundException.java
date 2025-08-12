package project.closet.service.exception.clothes;

import java.util.UUID;
import project.closet.service.exception.ErrorCode;

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
