package project.closet.dto.request;

import java.util.UUID;
import project.closet.entity.user.Role;

public record RoleUpdateRequest(
    UUID userId,
    Role newRole
) {

}
