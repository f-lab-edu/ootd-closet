package org.closet.common.exception.clothes;


import org.closet.common.exception.ErrorCode;

public class ExtractionException extends ClothesException {
    public ExtractionException(String url, Throwable cause) {
        super(ErrorCode.EXTRACTION_FAILED, cause);
        addDetail("url", url);
    }
}
