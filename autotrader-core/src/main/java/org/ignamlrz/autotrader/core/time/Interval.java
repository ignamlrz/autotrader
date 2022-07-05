package org.ignamlrz.autotrader.core.time;

import java.util.stream.Stream;

import static org.ignamlrz.autotrader.core.time.TimeConstants.*;

/**
 * Enum of Intervals
 */
public enum Interval {
    MINUTE_1(MINUTE_TO_SECONDS),
    MINUTE_5(MINUTE_TO_SECONDS * 5L),
    MINUTE_15(MINUTE_TO_SECONDS * 15L),
    HOUR_1(HOUR_TO_SECONDS),
    HOUR_4(HOUR_TO_SECONDS * 4L),
    DAY_1(DAY_TO_SECONDS),
    WEEK_1(WEEK_TO_SECONDS);

    // Fields
    private final long seconds;

    Interval(long seconds) {
        this.seconds = seconds;
    }

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
