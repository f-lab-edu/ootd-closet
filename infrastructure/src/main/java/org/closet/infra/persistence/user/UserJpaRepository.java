package org.closet.infra.persistence.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.closet.domain.user.entity.User;
import org.closet.domain.user.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends UserRepository, JpaRepository<User, UUID>, UserRepositoryCustom {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByName(String name);

    @Query("SELECT u FROM User u JOIN FETCH u.profile WHERE u.id = :userId")
    Optional<User> findByIdWithProfile(UUID userId);

    @Query("SELECT u.id FROM User u")
    List<UUID> findAllIds();
}
