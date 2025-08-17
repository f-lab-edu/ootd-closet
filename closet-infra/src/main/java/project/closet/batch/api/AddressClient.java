package project.closet.batch.api;


import project.closet.batch.api.response.KakaoAddressResponse;

public interface AddressClient {

    KakaoAddressResponse requestAddressFromKakao(Double longitude, Double latitude);
}
