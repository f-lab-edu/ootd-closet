package project.closet.service.event;

import java.util.UUID;

public record FollowCreateEvent(
    UUID followeeId,
    String followerName
) {

}
