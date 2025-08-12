package project.closet.service.dto.request;

import java.util.UUID;

public record FollowCreateRequest(
    UUID followeeId,
    UUID followerId
) {

}
