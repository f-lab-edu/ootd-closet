package project.closet.service.event;

import java.util.UUID;

public record FeedCommentCreateEvent(
    UUID feedAuthorId,
    String commenterUsername,
    String commentText
) {

}
