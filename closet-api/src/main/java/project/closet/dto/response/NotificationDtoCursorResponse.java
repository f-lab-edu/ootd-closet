package project.closet.dto.response;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import project.closet.SortDirection;

public record NotificationDtoCursorResponse(
    List<NotificationDto> data,
    Instant nextCursor,
    UUID nextIdAfter,
    boolean hasNext,
    long totalCount,
    String sortBy,
    SortDirection sortDirection
) {

}
