package project.closet.service.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import project.closet.service.mapper.RoleMapper;
import project.closet.service.user.RoleCode;
import project.closet.user.entity.Role;
import project.closet.user.entity.User;

public record UserDto(
    UUID id,
    Instant createdAt,
    String email,
    String name,
    RoleCode role,
    List<String> linkedOAuthProviders,
    boolean locked
) {

    public static UserDto from(User user) {
        return new UserDto(
            user.getId(),
            user.getCreatedAt(),
            user.getEmail(),
            user.getName(),
            RoleMapper.toCode(user.getRole()),
            List.of(),
            user.isLocked()
        );
    }
}
