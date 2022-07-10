package org.ignamlrz.autotrader.binance.models.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ignamlrz.autotrader.core.TestUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinanceCandlestickTest {

    // ========================================================
    // = TEST METHODS
    // ========================================================

    @ParameterizedTest
    @MethodSource("testJSONs")
    void testConversion(String json, String errorMsg) throws JsonProcessingException {
        BinanceCandlestick candlestick = TestUtils.buildJSON(json, BinanceCandlestick.class, errorMsg);

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
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999},\n" +
                        "  \"trades\": 10,\n" +
                        "  \"quoteVolume\": 0.47,\n" +
                        "  \"takerBuyVolume\": 0.47,\n" +
                        "  \"takerBuyQuoteVolume\": 0.47\n" +
                        "}", null
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999},\n" +
                        "  \"trades\": -1,\n" +
                        "  \"quoteVolume\": 0.47,\n" +
                        "  \"takerBuyVolume\": 0.47,\n" +
                        "  \"takerBuyQuoteVolume\": 0.47\n" +
                        "}", "num trades is lower than 0"
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999},\n" +
                        "  \"trades\": 5,\n" +
                        "  \"quoteVolume\": -0.47,\n" +
                        "  \"takerBuyVolume\": 0.47,\n" +
                        "  \"takerBuyQuoteVolume\": 0.47\n" +
                        "}", "quote asset volume is lower than 0"
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999},\n" +
                        "  \"trades\": 5,\n" +
                        "  \"quoteVolume\": 0.47,\n" +
                        "  \"takerBuyVolume\": -0.47,\n" +
                        "  \"takerBuyQuoteVolume\": 0.47\n" +
                        "}", "taker buy base asset volume is lower than 0"
                ),
                Arguments.of("{\n" +
                        "  \"open\": 0.1,\n" +
                        "  \"high\": 0.2,\n" +
                        "  \"low\": 0.05,\n" +
                        "  \"close\": 0.1,\n" +
                        "  \"volume\": 0.47,\n" +
                        "  \"timeframe\": {\"open\": 1499040000000, \"close\": 1499644799999},\n" +
                        "  \"trades\": 4,\n" +
                        "  \"quoteVolume\": 0.47,\n" +
                        "  \"takerBuyVolume\": 0.47,\n" +
                        "  \"takerBuyQuoteVolume\": -0.47\n" +
                        "}", "taker buy quote asset volume is lower than 0"
                ),
        };
        return Stream.of(args);
    }
}