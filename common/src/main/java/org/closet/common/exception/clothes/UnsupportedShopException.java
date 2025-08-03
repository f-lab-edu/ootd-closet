package org.closet.common.exception.clothes;


import org.closet.common.exception.ErrorCode;

public class UnsupportedShopException extends ClothesException {
    public UnsupportedShopException(String url) {
        super(ErrorCode.UNSUPPORTED_SHOP);
        addDetail("url", url);
    }
}
