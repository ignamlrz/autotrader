package org.ignamlrz.autotrader.core.time;

import java.util.Date;

/**
 * Utility class for Timeframe
 */
public final class TimeframeUtils {
    /**
     * Method for get current timeframe given an interval
     *
     * @param interval Target interval
     * @return a timeframe
     */
    public static Timeframe of(Interval interval) {
        return of(new Date(), interval);
    }

    /**
     * Method for get current timeframe from a specified date given an interval
     *
     * @param date     Specified date
     * @param interval Target interval
     * @return a timeframe
     */
    public static Timeframe of(Date date, Interval interval) {
        return of(date.getTime(), interval);
    }

    /**
     * Method for get current timeframe from a specified date given an interval
     *
     * @param date     Specified date
     * @param interval Target interval
     * @return a timeframe
     */
    public static Timeframe of(long date, Interval interval) {
        long millis = interval.toMillis();
        long openDate = date - date % millis;
        long closeDate = openDate + (millis - 1);
        return new Timeframe(openDate, closeDate);
    }
}
