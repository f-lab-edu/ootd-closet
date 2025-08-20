package project.closet.weather.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SkyStatus {
    // 맑음, 구름 많음, 흐림
    CLEAR("1"),
    MOSTLY_CLOUDY("3"),
    CLOUDY("4"),
    UNKNOWN(null);

    private final String code;

    SkyStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final Map<String, SkyStatus> BY_CODE =
        Arrays.stream(SkyStatus.values())
            .filter(v -> v.code != null)
            .collect(Collectors.toUnmodifiableMap(SkyStatus::getCode, Function.identity()));

    public static SkyStatus fromCode(String code) {
        if (code == null) return SkyStatus.UNKNOWN;
        return BY_CODE.get(code);
    }
}
