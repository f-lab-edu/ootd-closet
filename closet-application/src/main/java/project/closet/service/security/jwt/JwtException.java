package project.closet.service.security.jwt;

import java.util.Map;
import project.closet.service.exception.ClosetException;
import project.closet.service.exception.ErrorCode;

public class JwtException extends ClosetException {

    public JwtException(ErrorCode errorCode) {
        super(errorCode);
    }

    public JwtException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public JwtException(ErrorCode errorCode, Map<String, Object> details) {
        super(errorCode);
        this.getDetails().putAll(details);
    }

    public JwtException(ErrorCode errorCode, Map<String, Object> details, Throwable cause) {
        super(errorCode, cause);
        this.getDetails().putAll(details);
    }
}
