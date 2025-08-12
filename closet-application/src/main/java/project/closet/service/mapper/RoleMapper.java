package project.closet.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.closet.service.user.RoleCode;
import project.closet.user.entity.Role;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleMapper {

    public static Role toDomain(RoleCode code) {
        return (code == null) ? null : Role.valueOf(code.name());
    }

    public static RoleCode toCode(Role domain) {
        return (domain == null) ? null : RoleCode.valueOf(domain.name());
    }
}
