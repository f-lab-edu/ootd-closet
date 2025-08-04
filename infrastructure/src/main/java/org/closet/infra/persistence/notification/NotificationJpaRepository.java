package org.closet.infra.persistence.notification;

import java.util.UUID;
import org.closet.domain.notification.entity.Notification;
import org.closet.domain.notification.repository.NotificationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends NotificationRepository, JpaRepository<Notification, UUID>,
    NotificationRepositoryCustom {
}
