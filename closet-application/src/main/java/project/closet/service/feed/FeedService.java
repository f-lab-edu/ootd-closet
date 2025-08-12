package project.closet.service.feed;

import java.time.Instant;
import java.util.UUID;
import project.closet.SortDirection;
import project.closet.service.dto.request.CommentCreateRequest;
import project.closet.service.dto.request.FeedCreateRequest;
import project.closet.service.dto.request.FeedUpdateRequest;
import project.closet.service.dto.response.CommentDto;
import project.closet.service.dto.response.CommentDtoCursorResponse;
import project.closet.service.dto.response.FeedDto;
import project.closet.service.dto.response.FeedDtoCursorResponse;
import project.closet.weather.entity.PrecipitationType;
import project.closet.weather.entity.SkyStatus;

public interface FeedService {

    FeedDto createFeed(FeedCreateRequest feedCreateRequest);

    void likeFeed(UUID feedId, UUID loginUserId);

    void cancelFeedLike(UUID feedId, UUID userId);

    CommentDto createComment(CommentCreateRequest commentCreateRequest);

    void deleteFeed(UUID feedId);

    FeedDto updateFeed(UUID feedId, FeedUpdateRequest feedUpdateRequest, UUID loginUserId);

    CommentDtoCursorResponse getFeedComments(UUID feedId, Instant cursor, UUID idAfter, int limit);

    FeedDtoCursorResponse getFeedList(
        String cursor,
        UUID idAfter,
        int limit,
        String sortBy,
        SortDirection sortDirection,
        String keywordLike,
        SkyStatusCode skyStatusEqual,
        PrecipitationTypeCode precipitationTypeEqual,
        UUID authorIdEqual,
        UUID loginUserId
    );
}
