package project.closet.service.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import project.closet.service.feed.SkyStatusCode;
import project.closet.weather.entity.SkyStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkyStatusMapper {

    public static SkyStatusCode toCode(SkyStatus domain) {
        return (domain == null) ? null : SkyStatusCode.valueOf(domain.name());
    }

    public static SkyStatus toDomain(SkyStatusCode code) {
        return (code == null) ? null : SkyStatus.valueOf(code.name());
    }
}
