package org.ignamlrz.autotrader.core.model.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ignamlrz.autotrader.core.TestUtils;
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
    void testConversion(String json, String errorMsg) throws JsonProcessingException {
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
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}", null
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.1,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}", null
                ),
                Arguments.of("{\n" +
                        "  \"open\": -1,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}", "opening price is lower than 0"
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.2,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": -1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}", "closing price is lower than 0"
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.2,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}", "high must be the highest value"
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.1,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.2,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}", "high must be the highest value"
                ),
                Arguments.of("{\n" +
                                "  \"open\": 0.05,\n" +
                                "  \"high\": 0.2,\n" +
                                "  \"low\": 0.1,\n" +
                                "  \"close\": 0.1,\n" +
                                "  \"volume\": 0.47,\n" +
                                "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                                "}", "low must be the lowest value"
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.1,\n" +
                        "  \"close\": 0.05,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}", "low must be the lowest value"
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.1,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": -0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999}\n" +
                        "}", "volume is lower than 0"
                )
        };
        return Stream.of(args);
    }
}