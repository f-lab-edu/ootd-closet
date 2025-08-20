package project.closet.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import project.closet.user.entity.Profile;
import project.closet.user.entity.User;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@EnableJpaAuditing
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("findAllIds(): 저장된 모든 사용자 ID를 가져온다")
    void findAllIds_returnsAllUserIds() {
        // given: 두 개의 User 엔티티 저장
        User user1 = new User("test1", "test@mail.com", "password");

        User user2 = new User("test2", "test2@mail.com", "password2");
        userRepository.saveAll(List.of(user1, user2));

        // when: 모든 ID 조회
        List<UUID> ids = userRepository.findAllIds();

        // then: 저장한 두 개의 ID가 순서와 무관하게 포함되어야 한다
        assertThat(ids)
                .hasSize(2)
                .containsExactlyInAnyOrder(user1.getId(), user2.getId());
    }

    @DisplayName("findByIdWithProfile: 연관 관계 location까지 모두 가지고 와야한다.")
    @Test
    void findByIdWithProfile_returnsUserWithProfileAndLocation() {
        // given
        User test = new User("test", "test@mail.com", "1234");
        Profile.createDefault(test);
        userRepository.save(test);
        test.updateLocation(11.0,12.0,List.of("location1", "location2"));
        em.flush();
        em.clear();
        // when
        User user = userRepository.findByIdWithProfile(test.getId())
            .orElseThrow();
        // then
        assertThat(user.getProfile().getLocationNames().size())
            .isEqualTo(2);
    }
}
