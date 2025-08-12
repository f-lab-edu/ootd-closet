package project.closet.service.waether;

import project.closet.service.dto.response.KakaoAddressResponse;

public interface AddressClient {

    KakaoAddressResponse requestAddressFromKakao(Double longitude, Double latitude);
}
