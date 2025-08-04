package org.closet.domain.notification.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.closet.domain.notification.entity.Notification;

public interface NotificationRepository {
    Notification save(Notification notification);

    List<Notification> saveAll(List<Notification> notifications);

    Optional<Notification> findById(UUID id);

    long countByReceiverId(UUID receiverId);

    void deleteById(UUID id);
}
