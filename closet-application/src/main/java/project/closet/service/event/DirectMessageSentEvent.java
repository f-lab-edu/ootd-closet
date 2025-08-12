package project.closet.service.event;

import java.util.UUID;

public record DirectMessageSentEvent(
    UUID receiverId,
    String senderUsername,
    String messageContent
) {

}
