package org.closet.domain.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.closet.domain.user.entity.Role;
import org.closet.domain.user.entity.User;
import org.hibernate.query.SortDirection;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    Optional<User> findByIdWithProfile(UUID userId);

    List<UUID> findAllIds();

    List<User> findUsersWithCursor(String cursor, UUID idAfter, int limit, String sortBy,
                                   SortDirection direction, String emailLike, Role roleEqual, Boolean locked);

    long countAllUsers(String emailLike, Role roleEqual, Boolean locked);
}
