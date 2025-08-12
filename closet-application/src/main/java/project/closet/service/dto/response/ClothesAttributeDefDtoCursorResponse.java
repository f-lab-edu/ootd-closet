package project.closet.service.dto.response;

import java.util.List;
import java.util.UUID;

public record ClothesAttributeDefDtoCursorResponse(
    List<ClothesAttributeDefDto> data,
    String nextCursor,
    UUID nextIdAfter,
    boolean hasNext,
    long totalCount,
    String sortBy,
    String sortDirection
) {
}
