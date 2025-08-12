package project.closet.service.notification;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import project.closet.service.dto.response.NotificationDtoCursorResponse;
import project.closet.notification.entity.NotificationLevel;

public interface NotificationService {

    NotificationDtoCursorResponse getNotifications(UUID loginUserId, Instant cursor, UUID idAfter, int limit);

    void deleteNotification(UUID notificationId);

    void create(UUID receiverId, String title, String content, NotificationLevel level);

    void createForAllUsers(String title, String content, NotificationLevel level);

    void createAll(Set<UUID> receiverIds, String title, String content, NotificationLevel level);
}
