package project.closet.dto.response;

import java.util.List;
import java.util.UUID;

public record DirectMessageDtoCursorResponse(
    List<DirectMessageDto> data,
    String nextCursor,
    UUID nextIdAfter,
    boolean hasNext,
    long totalCount,
    String sortBy,
    project.closet.SortDirection sortDirection
) {

}
