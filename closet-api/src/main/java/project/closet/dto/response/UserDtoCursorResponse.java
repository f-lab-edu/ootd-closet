package project.closet.dto.response;

import java.util.List;
import java.util.UUID;
import project.closet.SortDirection;

public record UserDtoCursorResponse(
    List<UserDto> data,
    String nextCursor,
    UUID nextIdAfter,
    boolean hasNext,
    long totalCount,
    String sortBy,
    SortDirection sortDirection
) {

}
