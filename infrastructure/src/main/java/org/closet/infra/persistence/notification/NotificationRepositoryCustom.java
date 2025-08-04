package org.closet.infra.persistence.notification;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.closet.domain.notification.entity.Notification;

public interface NotificationRepositoryCustom {
    List<Notification> findAllByReceiverWithCursor(UUID receiverId, Instant cursor, UUID idAfter, int limit);
}
