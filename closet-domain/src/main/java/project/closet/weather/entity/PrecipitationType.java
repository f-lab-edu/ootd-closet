package project.closet.weather.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PrecipitationType {
    // 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
    NONE("0"),
    RAIN("1"),
    RAIN_SNOW("2"),
    SNOW("3"),
    SHOWER("4"),
    UNKNOWN(null);


    private final String code;

    PrecipitationType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static final Map<String, PrecipitationType> BY_CODE =
        Arrays.stream(PrecipitationType.values())
            .filter(v -> v.code != null)
            .collect(Collectors.toUnmodifiableMap(PrecipitationType::getCode, Function.identity()));

    public static PrecipitationType fromCode(String code) {
        if (code == null) return PrecipitationType.UNKNOWN;
        return BY_CODE.get(code);
    }

}
