package project.closet.service.event;

import java.time.Instant;
import project.closet.service.dto.response.NotificationDto;

public record NotificationCreatedEvent(
    Instant createdAt,
    NotificationDto notificationDto
) {

    public NotificationCreatedEvent(NotificationDto notificationDto) {
        this(Instant.now(), notificationDto);
    }
}
