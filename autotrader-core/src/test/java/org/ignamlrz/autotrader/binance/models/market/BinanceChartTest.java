package org.ignamlrz.autotrader.binance.models.market;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ignamlrz.autotrader.core.TestUtils;
import org.ignamlrz.autotrader.core.model.market.Chart;
import org.ignamlrz.autotrader.core.utilities.time.Interval;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils.fromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BinanceChartTest {

    @ParameterizedTest
    @MethodSource(value = "testJSONs")
    void testIsCreatedCorrectly(String json, String errMsg) throws JsonProcessingException {
        BinanceChart candlestick = TestUtils.buildJSON(json, BinanceChart.class, errMsg);

        if(Optional.ofNullable(candlestick).isPresent()) {
            assertEquals(json.replaceAll("\\s", ""), candlestick.toString());
        }
    }

    @Test
    void testConversion() throws JsonProcessingException {
        // ...build chart
        Object[] data0 = {1499040000000L,      // Open time
                "0.00386200",       // Open
                "0.00386200",       // High
                "0.00386200",       // Low
                "0.00386200",       // Close
                "0.47000000",       // Volume
                1499040059999L,     // Close time
                "0.00181514",       // Quote asset volume
                1,                  // Number of trades
                "0.47000000",       // Taker buy base asset volume
                "0.00181514",       // Taker buy quote asset volume
                "0"
        };
        List<BinanceCandlestick> binanceCandlesticks = new ArrayList<>();
        binanceCandlesticks.add(BinanceCandlestickUtils.of(data0));
        Chart exampleChart = new BinanceChart(Interval.MINUTE_1, binanceCandlesticks);

        // ...compare string from macd and indicator
        String json = exampleChart.toString();

        // ...create new indicator from Json
        BinanceChart chart = (BinanceChart) fromJson(json, Chart.class);
    }

    /**
     * Static method of JSONs created correctly
     *
     * @return stream of JSONs
     */
    private static Stream<Arguments> testJSONs() {
        Arguments[] args = {
                Arguments.of("{\n" +
                        "  \"type\": \"binance\",\n" +
                        "  \"interval\": \"MINUTE_1\",\n" +
                        "  \"candlesticks\": [\n" +
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
                        "    }\n" +
                        "  ]\n" +
                        "}", null)
        };
        return Stream.of(args);
    }
}