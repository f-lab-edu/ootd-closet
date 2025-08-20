package project.closet.weather.entity;

public enum AsWord {
    WEAK(0.0, 4.0),
    MODERATE(4.0, 9.0),
    STRONG(9.0, Double.POSITIVE_INFINITY);

    private final double minInclusive;
    private final double maxExclusive;

    AsWord(double minInclusive, double maxExclusive) {
        this.minInclusive = minInclusive;
        this.maxExclusive = maxExclusive;
    }

    public static AsWord fromWindSpeed(Double speed) {
        if (speed == null || speed.isNaN() || speed < 0) {
            return WEAK;
        }
        for (AsWord w : values()) {
            if (speed >= w.minInclusive && speed < w.maxExclusive) {
                return w;
            }
        }
        return STRONG;
    }
}
