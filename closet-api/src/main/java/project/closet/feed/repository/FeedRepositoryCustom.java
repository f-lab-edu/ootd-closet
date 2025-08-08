package project.closet.feed.repository;

import java.util.List;
import java.util.UUID;
import org.hibernate.query.SortDirection;
import project.closet.entity.feed.Feed;
import project.closet.entity.weather.PrecipitationType;
import project.closet.entity.weather.SkyStatus;

public interface FeedRepositoryCustom {

    List<Feed> findAllWithCursorAndFilters(
        String cursor,
        UUID idAfter,
        int limit,
        String sortBy,
        SortDirection sortDirection,
        String keywordLike,
        SkyStatus skyStatusEqual,
        PrecipitationType precipitationType,
        UUID authorIdEqual
    );

    long countByFilters(
        String keywordLike,
        SkyStatus skyStatusEqual,
        PrecipitationType precipitationType,
        UUID authorIdEqual
    );
}
