package project.closet.service.exception.clothes;

import project.closet.service.exception.ErrorCode;

public class UnsupportedShopException extends ClothesException {
    public UnsupportedShopException(String url) {
        super(ErrorCode.UNSUPPORTED_SHOP);
        addDetail("url", url);
    }
}
