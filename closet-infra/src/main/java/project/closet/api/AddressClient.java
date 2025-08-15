package project.closet.api;


import project.closet.api.response.KakaoAddressResponse;

public interface AddressClient {

    KakaoAddressResponse requestAddressFromKakao(Double longitude, Double latitude);
}
