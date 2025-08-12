package project.closet.service.dto.request;

import java.util.UUID;

public record DirectMessageCreateRequest(
    UUID receiverId,
    UUID senderId,
    String content
) {

}
