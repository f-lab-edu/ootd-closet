package project.closet.service.event;

import java.util.UUID;

public record FeedLikeCreateEvent(
    UUID feedAuthorId,
    String likerUsername,
    String feedContent
) {

}
