package project.closet.service.exception.clothes;

import project.closet.service.exception.ErrorCode;

public class ExtractionException extends ClothesException {
    public ExtractionException(String url, Throwable cause) {
        super(ErrorCode.EXTRACTION_FAILED, cause);
        addDetail("url", url);
    }
}
