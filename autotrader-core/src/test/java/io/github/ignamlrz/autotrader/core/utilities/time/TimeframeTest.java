package io.github.ignamlrz.autotrader.core.utilities.time;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import io.github.ignamlrz.autotrader.core.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils.fromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeframeTest {

    // ========================================================
    // = TEST METHODS
    // ========================================================

    @ParameterizedTest
    @MethodSource("validArgs")
    <T extends Throwable> void testConversion(Class<T> t, String json) throws Throwable {
        TestUtils.runWithPossibleThrows(t, () -> fromJson(json, Timeframe.class));
    }

    @Test
    void testIsCreatedCorrectlyGivenAnyInterval() {
        Stream.of(Interval.values()).forEach(interval -> {
            // ...get timeframe for each interval
            Timeframe timeframe = TimeframeUtils.of(interval);
            // ...check getInterval return same interval
            assertEquals(interval, timeframe.interval());
            // ...diff must be interval.toMillis - 1, because close is next.open - 1
            assertEquals(interval.toMillis() - 1, timeframe.diff());
        });
    }

    @Test
    void testNext() {
        Stream.of(Interval.values()).forEach(interval -> {
            // ...get timeframe for each interval and his next timeframe
            Timeframe timeframe = TimeframeUtils.of(interval);
            Timeframe next = timeframe.next();
            // ...check getInterval return same interval
            assertEquals(interval, next.interval());
            // ...diff must be interval.toMillis - 1, because close is next.open - 1
            assertEquals(interval.toMillis() - 1, next.diff());
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
            assertEquals(interval, previous.interval());
            // ...diff must be interval.toMillis - 1, because close is next.open - 1
            assertEquals(interval.toMillis() - 1, previous.diff());
            // ...check close and open contain a difference of 1 millisecond
            assertEquals(timeframe.getOpen() - 1, previous.getClose());
        });
    }

    // ========================================================
    // = PRIVATE TEST METHODS
    // ========================================================

    /**
     * Static method of JSONs created correctly
     *
     * @return stream of JSONs
     */
    private static Stream<Arguments> validArgs() {
        Arguments[] args = {
                Arguments.of(null, "{\"open\":1499040000000,\"close\":1499644799999}"),
                Arguments.of(null, "{\"open\":1499040000,\"close\":1499644799}"),
                Arguments.of(null, "{\"open\":1499040000000,\"close\":1499644799000}"),
                Arguments.of(null, "{\"open\":1499040000000,\"close\":1499644800001}"),
                Arguments.of(null, "{\"open\":1,\"close\":1}"),
                Arguments.of(ValueInstantiationException.class, "{\"open\":2,\"close\":1}")
        };
        return Stream.of(args);
    }

}