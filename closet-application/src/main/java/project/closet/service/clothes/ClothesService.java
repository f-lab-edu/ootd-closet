package project.closet.service.clothes;

import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import project.closet.service.dto.request.ClothesCreateRequest;
import project.closet.service.dto.request.ClothesUpdateRequest;
import project.closet.service.dto.response.ClothesDto;
import project.closet.service.dto.response.ClothesDtoCursorResponse;
import project.closet.clothes.entity.ClothesType;

public interface ClothesService {

    ClothesDto createClothes(
            ClothesCreateRequest request,
            MultipartFile image
    );

    ClothesDtoCursorResponse findAll(
            String cursor,
            UUID idAfter,
            int limit,
            ClothesType typeEqual,
            UUID ownerId
    );

    void deleteClothesById(
            UUID clothesId
    );

    ClothesDto updateClothes(
            UUID clothesId,
            ClothesUpdateRequest request,
            MultipartFile image
    );
}
