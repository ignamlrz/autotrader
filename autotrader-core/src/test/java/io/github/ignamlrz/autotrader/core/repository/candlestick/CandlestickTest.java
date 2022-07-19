package io.github.ignamlrz.autotrader.core.repository.candlestick;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.ignamlrz.autotrader.core.TestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CandlestickTest {

    // ========================================================
    // = TEST METHODS
    // ========================================================

    @ParameterizedTest
    @MethodSource("testJSONs")
    void testConversion(String errorMsg, String json) throws JsonProcessingException {
        Candlestick candlestick = TestUtils.buildJSON(json, Candlestick.class, errorMsg);

        if(Optional.ofNullable(candlestick).isPresent()) {
            assertEquals(json.replaceAll("\\s", ""), candlestick.toString());
        }
    }

    // ========================================================
    // = PRIVATE TEST METHODS
    // ========================================================

    /**
     * Static method of JSONs to check
     *
     * @return stream of JSONs
     */
    private static Stream<Arguments> testJSONs() {
        Arguments[] args = {
                Arguments.of(null, "{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                ),
                Arguments.of(null, "{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.1,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                ),
                Arguments.of("opening price is lower than 0", "{\n" +
                        "  \"open\": -1,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                ),
                Arguments.of("closing price is lower than 0", "{\n" +
                        "  \"open\": 0.2,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": -1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                ),
                Arguments.of("low price is lower than 0", "{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": -0.1,\n" +
                        "  \"close\": 0.05,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                ),
                Arguments.of("high must be the highest value", "{\n" +
                        "  \"open\": 0.2,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                ),
                Arguments.of("high must be the highest value", "{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.2,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                ),
                Arguments.of("low must be the lowest value", "{\n" +
                                "  \"open\": 0.05,\n" +
                                "  \"high\": 0.2,\n" +
                                "  \"low\": 0.1,\n" +
                                "  \"close\": 0.1,\n" +
                                "  \"volume\": 0.47,\n" +
                                "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                                "}"
                ),
                Arguments.of("low must be the lowest value", "{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.1,\n" +
                        "  \"close\": 0.05,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                ),
                Arguments.of("volume is lower than 0", "{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.1,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": -0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}"
                )
        };
        return Stream.of(args);
    }
}