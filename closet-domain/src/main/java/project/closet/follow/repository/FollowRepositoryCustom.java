package project.closet.follow.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import project.closet.follow.Follow;

public interface FollowRepositoryCustom {

    List<Follow> findFollowingsWithCursor(
            UUID followerId,
            Instant cursor,
            UUID idAfter,
            String nameLike,
            int limit
    );

    List<Follow> findFollowersWithCursor(
            UUID followeeId,
            Instant cursor,
            UUID idAfter,
            String nameLike,
            int limit
    );

}
