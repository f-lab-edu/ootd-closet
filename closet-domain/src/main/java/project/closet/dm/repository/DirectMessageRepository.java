package project.closet.dm.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import project.closet.dm.DirectMessage;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, UUID>, DirectMessageRepositoryCustom {

}
