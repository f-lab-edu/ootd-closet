package project.closet.service.clothes;

import java.util.UUID;
import project.closet.SortDirection;
import project.closet.service.dto.request.ClothesAttributeDefCreateRequest;
import project.closet.service.dto.request.ClothesAttributeDefUpdateRequest;
import project.closet.service.dto.response.ClothesAttributeDefDto;
import project.closet.service.dto.response.ClothesAttributeDefDtoCursorResponse;

public interface AttributeService {

    ClothesAttributeDefDto create(
            ClothesAttributeDefCreateRequest req
    );

    ClothesAttributeDefDto update(
            UUID id,
            ClothesAttributeDefUpdateRequest req
    );

    void delete(UUID id);

    ClothesAttributeDefDtoCursorResponse findAll(
            String cursor,
            UUID idAfter,
            int limit,
            String sortBy,
            SortDirection sortDirection,
            String keywordLike
    );
}
