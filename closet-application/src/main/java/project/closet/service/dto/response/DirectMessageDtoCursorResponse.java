package project.closet.service.dto.response;

import java.util.List;
import java.util.UUID;
import project.closet.service.common.SortDirection;

public record DirectMessageDtoCursorResponse(
    List<DirectMessageDto> data,
    String nextCursor,
    UUID nextIdAfter,
    boolean hasNext,
    long totalCount,
    String sortBy,
    SortDirection sortDirection
) {

}
