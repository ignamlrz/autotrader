package org.ignamlrz.autotrader.core.utilities.time;

import org.ignamlrz.autotrader.core.utilities.time.Interval;
import org.ignamlrz.autotrader.core.utilities.time.Timeframe;
import org.ignamlrz.autotrader.core.utilities.time.TimeframeUtils;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeframeTest {

    @Test
    void testIsCreatedCorrectlyGivenAnyInterval() {
        Stream.of(Interval.values()).forEach(interval -> {
            // ...get timeframe for each interval
            Timeframe timeframe = TimeframeUtils.of(interval);
            // ...check getInterval return same interval
            assertEquals(interval, timeframe.getInterval());
            // ...diff must be interval.toMillis - 1, because close is next.open - 1
            assertEquals(interval.toMillis() - 1, timeframe.getDiff());
        });
    }

    @Test
    void testNext() {
        Stream.of(Interval.values()).forEach(interval -> {
            // ...get timeframe for each interval and his next timeframe
            Timeframe timeframe = TimeframeUtils.of(interval);
            Timeframe next = timeframe.next();
            // ...check getInterval return same interval
            assertEquals(interval, next.getInterval());
            // ...diff must be interval.toMillis - 1, because close is next.open - 1
            assertEquals(interval.toMillis() - 1, next.getDiff());
            // ...check close and open contain a difference of 1 millisecond
            assertEquals(timeframe.getClose() + 1, next.getOpen());
        });
    }

    @Test
    void testPrevious() {
        Stream.of(Interval.values()).forEach(interval -> {
            // ...get timeframe for each interval and his next timeframe
            Timeframe timeframe = TimeframeUtils.of(interval);
            Timeframe previous = timeframe.previous();
            // ...check getInterval return same interval
            assertEquals(interval, previous.getInterval());
            // ...diff must be interval.toMillis - 1, because close is next.open - 1
            assertEquals(interval.toMillis() - 1, previous.getDiff());
            // ...check close and open contain a difference of 1 millisecond
            assertEquals(timeframe.getOpen() - 1, previous.getClose());
        });
    }

}