package project.closet.dto.response;

import java.util.UUID;
import project.closet.entity.follow.Follow;

public record FollowDto(
    UUID id,
    UserSummary followee,
    UserSummary follower
) {

    public static FollowDto fromWithImageUrl(Follow follow, String followeeImage, String followerImage) {
        return new FollowDto(
            follow.getId(),
            new UserSummary(
                follow.getFollowee().getId(),
                follow.getFollowee().getName(),
                followeeImage
            ),
            new UserSummary(
                follow.getFollower().getId(),
                follow.getFollower().getName(),
                followerImage
            )
        );
    }
}
