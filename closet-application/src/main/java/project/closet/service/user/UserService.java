package project.closet.service.user;

import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import project.closet.SortDirection;
import project.closet.service.dto.request.ChangePasswordRequest;
import project.closet.service.dto.request.ProfileUpdateRequest;
import project.closet.service.dto.request.UserCreateRequest;
import project.closet.service.dto.request.UserLockUpdateRequest;
import project.closet.service.dto.request.UserRoleUpdateRequest;
import project.closet.service.dto.response.ProfileDto;
import project.closet.service.dto.response.UserDto;
import project.closet.service.dto.response.UserDtoCursorResponse;

public interface UserService {

    // 회원 가입
    UserDto create(UserCreateRequest userCreateRequest);

    // 회원 조회
    ProfileDto getProfile(UUID userId);

    // 회원 정보 수정
    ProfileDto updateProfile(UUID userId, ProfileUpdateRequest profileUpdateRequest,
                             MultipartFile profileImage);

    // 권한 수정
    UserDto updateRole(UUID userid, UserRoleUpdateRequest userRoleUpdateRequest);

    // 회원 잠금 상태 수정
    UUID updateLockStatus(UUID userId, UserLockUpdateRequest userRoleUpdateRequest);

    // 회원 조회
    UserDtoCursorResponse findAll(
        String cursor,
        UUID idAfter,
        int limit,
        String sortBy,
        SortDirection sortDirection,
        String emailLike,
        RoleCode roleEqual,
        Boolean locked
    );

    // 유저 비밀번호 변경
    void changePassword(UUID userId, ChangePasswordRequest changePasswordRequest);
}
