package io.github.ignamlrz.autotrader.binance.models.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.ignamlrz.autotrader.core.TestUtils;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorTarget;
import io.github.ignamlrz.autotrader.core.model.PairAsset;
import io.github.ignamlrz.autotrader.core.utilities.time.Timeframe;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BinanceChartTest {

    // ========================================================
    // = TEST METHODS
    // ========================================================

    @ParameterizedTest
    @MethodSource(value = "testJSONs")
    void testConversion(String errMsg, String json) throws JsonProcessingException {
        BinanceChartModel chart = TestUtils.buildJSON(json, BinanceChartModel.class, errMsg);

        if (Optional.ofNullable(chart).isPresent()) {
            // ...check JSONs
            assertEquals(json.replaceAll("\\s", ""), chart.toString());
            // ...check list and map contain same length
            assertEquals(chart.getCandlesticks().size(), chart.toMap().size());

            // ...check toArray() works fine with BinanceCandlestickUtils
            Object[] array = chart.toArray();
            for (Object obj : array) {
                BinanceCandlestick candlestick = BinanceCandlestickUtils.of((Object[]) obj);
                assertNotNull(candlestick);
            }

            // ...check can access this data
            IndicatorTarget[] targets = {
                    IndicatorTarget.OPEN,
                    IndicatorTarget.HIGH,
                    IndicatorTarget.LOW,
                    IndicatorTarget.CLOSE,
                    IndicatorTarget.VOLUME
            };
            for (IndicatorTarget target : targets) {
                List<Float> data = chart.dataByTarget(target);
                assertEquals(data.size(), chart.toMap().size());
            }

            // ...check insertion
            boolean created = chart.put(defaultKline(new Timeframe(1499039940000L, 1499039999999L)));
            assertFalse(created);
            created = chart.put(defaultKline(new Timeframe(1499040060000L, 1499040119999L)));
            assertTrue(created);
            created = chart.put(defaultKline(new Timeframe(0L, 59999L)));
            assertTrue(created);
            created = chart.put(defaultKline(new Timeframe(6000000000000L, 6000000059999L)));
            assertTrue(created);

            // ...check deletion
            assertTrue(chart.remove(0L));
            assertTrue(chart.remove(1499040060000L));
            assertTrue(chart.remove(6000000000000L));
        }
    }

    // ========================================================
    // = PRIVATE TEST METHODS
    // ========================================================

    private static BinanceCandlestick defaultKline(Timeframe timeframe) {
        return new BinanceCandlestick(1.f, 2.f, 0.5f, 1.f, 1.f,
                timeframe, 1, 1.f, 1.f, 1.f);
    }

    /**
     * Static method for generate JSONs of this class
     *
     * @return stream of arguments (errorMessage: string, JSON: string)
     */
    private static Stream<Arguments> testJSONs() {
        PairAsset pairAsset = new PairAsset("BTC", "EUR");
        Arguments[] args = {
                Arguments.of(null, "{\n" +
                        "  \"id\":\"binance/BTCEUR/MINUTE_1\",\n" +
                        "  \"type\": \"binance\",\n" +
                        "  \"pairAsset\": " + pairAsset + ",\n" +
                        "  \"interval\": \"MINUTE_1\",\n" +
                        "  \"candlesticks\": [\n" +
                        "    {\n" +
                        "      \"open\": 0.003962,\n" +
                        "      \"high\": 0.003999,\n" +
                        "      \"low\": 0.003777,\n" +
                        "      \"close\": 0.0038,\n" +
                        "      \"volume\": 120.2,\n" +
                        "      \"timeframe\": {\"open\": 1499039940000, \"close\": 1499039999999},\n" +
                        "      \"trades\": 20,\n" +
                        "      \"quoteVolume\": 0.02,\n" +
                        "      \"takerBuyVolume\": 50.2,\n" +
                        "      \"takerBuyQuoteVolume\": 0.01\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"open\": 0.003862,\n" +
                        "      \"high\": 0.003862,\n" +
                        "      \"low\": 0.003862,\n" +
                        "      \"close\": 0.003862,\n" +
                        "      \"volume\": 0.47,\n" +
                        "      \"timeframe\": {\"open\": 1499040000000, \"close\": 1499040059999},\n" +
                        "      \"trades\": 1,\n" +
                        "      \"quoteVolume\": 0.00181514,\n" +
                        "      \"takerBuyVolume\": 0.47,\n" +
                        "      \"takerBuyQuoteVolume\": 0.00181514\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"open\": 0.003962,\n" +
                        "      \"high\": 0.003999,\n" +
                        "      \"low\": 0.003777,\n" +
                        "      \"close\": 0.0038,\n" +
                        "      \"volume\": 120.2,\n" +
                        "      \"timeframe\": {\"open\": 1499040120000, \"close\": 1499040179999},\n" +
                        "      \"trades\": 20,\n" +
                        "      \"quoteVolume\": 0.02,\n" +
                        "      \"takerBuyVolume\": 50.2,\n" +
                        "      \"takerBuyQuoteVolume\": 0.01\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"),
                Arguments.of("candlestick interval not match with chart interval", "{\n" +
                        "  \"type\": \"binance\",\n" +
                        "  \"pairAsset\": " + pairAsset + ",\n" +
                        "  \"interval\": \"MINUTE_1\",\n" +
                        "  \"candlesticks\": [\n" +
                        "    {\n" +
                        "      \"open\": 0.003962,\n" +
                        "      \"high\": 0.003999,\n" +
                        "      \"low\": 0.003777,\n" +
                        "      \"close\": 0.0038,\n" +
                        "      \"volume\": 120.2,\n" +
                        "      \"timeframe\": {\"open\": 1499039940001, \"close\": 1499039940002},\n" +
                        "      \"trades\": 20,\n" +
                        "      \"quoteVolume\": 0.02,\n" +
                        "      \"takerBuyVolume\": 50.2,\n" +
                        "      \"takerBuyQuoteVolume\": 0.01\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}"),
                Arguments.of("candlesticks timestamp not match with openings of chart interval", "{\n" +
                        "  \"type\": \"binance\",\n" +
                        "  \"pairAsset\": " + pairAsset + ",\n" +
                        "  \"interval\": \"MINUTE_1\",\n" +
                        "  \"candlesticks\": [\n" +
                        "    {\n" +
                        "      \"open\": 0.003962,\n" +
                        "      \"high\": 0.003999,\n" +
                        "      \"low\": 0.003777,\n" +
                        "      \"close\": 0.0038,\n" +
                        "      \"volume\": 120.2,\n" +
                        "      \"timeframe\": {\"open\": 1499039940001, \"close\": 1499040000000},\n" +
                        "      \"trades\": 20,\n" +
                        "      \"quoteVolume\": 0.02,\n" +
                        "      \"takerBuyVolume\": 50.2,\n" +
                        "      \"takerBuyQuoteVolume\": 0.01\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
        };
        return Stream.of(args);
    }
}