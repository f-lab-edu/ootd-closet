package project.closet.service.websocket;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import project.closet.service.exception.ErrorCode;
import project.closet.service.security.ClosetUserDetails;
import project.closet.service.security.jwt.JwtException;
import project.closet.service.security.jwt.JwtObject;
import project.closet.service.security.jwt.JwtService;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;
    private final RoleHierarchy roleHierarchy;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
                StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String accessToken = resolveAccessToken(accessor)
                    .orElseThrow(() -> new JwtException(ErrorCode.INVALID_TOKEN));
            if (jwtService.validate(accessToken)) {
                JwtObject jwtObject = jwtService.parse(accessToken);
                ClosetUserDetails userDetails = ClosetUserDetails.from(jwtObject);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        roleHierarchy.getReachableGrantedAuthorities(userDetails.getAuthorities()));

                accessor.setUser(auth);

            } else {
                jwtService.invalidateJwtSession(accessToken);
                throw new JwtException(ErrorCode.INVALID_TOKEN);
            }
        }
        return message;
    }

    private Optional<String> resolveAccessToken(StompHeaderAccessor accessor) {
        String prefix = "Bearer ";
        return Optional.ofNullable(accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION))
                .map(value -> {
                    if (value.startsWith(prefix)) {
                        return value.substring(prefix.length());
                    } else {
                        return null;
                    }
                });
    }
}
