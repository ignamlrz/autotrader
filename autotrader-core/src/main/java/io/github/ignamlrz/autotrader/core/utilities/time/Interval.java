package io.github.ignamlrz.autotrader.core.utilities.time;

import java.util.stream.Stream;

/**
 * Enum of Intervals
 */
public enum Interval {

    // ========================================================
    // = PREDEFINED ENUMS
    // ========================================================

    MINUTE_1(TimeConstants.MINUTE_TO_SECONDS),
    MINUTE_5(TimeConstants.MINUTE_TO_SECONDS * 5L),
    MINUTE_15(TimeConstants.MINUTE_TO_SECONDS * 15L),
    HOUR_1(TimeConstants.HOUR_TO_SECONDS),
    HOUR_4(TimeConstants.HOUR_TO_SECONDS * 4L),
    DAY_1(TimeConstants.DAY_TO_SECONDS),
    WEEK_1(TimeConstants.WEEK_TO_SECONDS);

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final long seconds;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    Interval(long seconds) {
        this.seconds = seconds;
    }

    // ========================================================
    // = STATIC METHODS
    // ========================================================

    /**
     * Method for find a predefined interval given his milliseconds
     *
     * @param milliseconds Milliseconds that must contain the predefined interval
     * @return an Interval
     * @throws RuntimeException if predefined interval is not found
     */
    public static Interval of(long milliseconds) {
        return Stream.of(Interval.values())
                // test milliseconds
                .filter(interval -> interval.testMillis(milliseconds))
                .findFirst().orElseThrow(() -> new RuntimeException("Interval of " + milliseconds + " ms not found"));
    }

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Method for get next timestamp
     *
     * @param timestamp Current timestamp
     * @return next timestamp
     */
    public long next(long timestamp) {
        return this.toMillis() + timestamp;
    }

    /**
     * Method for get previous timestamp
     *
     * @param timestamp Current timestamp
     * @return previous timestamp
     */
    public long previous(long timestamp) {
        return timestamp - this.toMillis();
    }

    /**
     * Method for get milliseconds of this interval
     *
     * @return milliseconds
     */
    public long toMillis() {
        return this.seconds * 1000L;
    }

    /**
     * Method for check if the passed milliseconds is same that this interval
     *
     * @param millis Milliseconds to check
     * @return true if contain same milliseconds that this interval, false otherwise
     */
    public boolean testMillis(long millis) {
        return testMillis(millis, 0);
    }

    /**
     * Method for check if the passed milliseconds is same that this interval, given a delta
     *
     * @param millis Milliseconds to check
     * @param delta  Delta that must comply
     * @return true if contain same milliseconds that this interval, false otherwise
     */
    public boolean testMillis(long millis, long delta) {
        return Math.abs(this.toMillis() - millis) <= delta;
    }
}
