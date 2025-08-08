package project.closet.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import project.closet.entity.user.Role;
import project.closet.entity.user.User;

public record UserDto(
    UUID id,
    Instant createdAt,
    String email,
    String name,
    Role role,
    List<String> linkedOAuthProviders,
    boolean locked
) {

    public static UserDto from(User user) {
        return new UserDto(
            user.getId(),
            user.getCreatedAt(),
            user.getEmail(),
            user.getName(),
            user.getRole(),
            List.of(),
            user.isLocked()
        );
    }
}
