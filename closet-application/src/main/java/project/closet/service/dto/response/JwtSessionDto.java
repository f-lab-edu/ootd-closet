package project.closet.service.dto.response;

import project.closet.jwtsession.JwtSession;

public record JwtSessionDto(
    String accessToken,
    String refreshToken
) {

    public static JwtSessionDto of(JwtSession jwtSession) {
        return new JwtSessionDto(jwtSession.getAccessToken(), jwtSession.getRefreshToken());
    }
}
