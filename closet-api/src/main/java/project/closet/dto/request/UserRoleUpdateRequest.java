package project.closet.dto.request;

import jakarta.validation.constraints.NotNull;
import project.closet.entity.user.Role;

public record UserRoleUpdateRequest(
    @NotNull
    Role role
) {

}
