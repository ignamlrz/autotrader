package io.github.ignamlrz.autotrader.core.analysis.indicators.macd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import io.github.ignamlrz.autotrader.core.analysis.indicators.*;
import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;
import org.hamcrest.CoreMatchers;
import io.github.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorCategory;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorTarget;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MACDIndicatorTest {

    @ParameterizedTest
    @MethodSource(value = "validArgs")
    void testsIsCreatedCorrectly(String json) throws JsonProcessingException {
        // ...build indicator
        MACDIndicator indicator = (MACDIndicator) ConversionUtils.fromJson(json, Indicator.class);

        // ...check generic properties of this instance
        Assertions.assertEquals(IndicatorType.MACD, indicator.metadata().identifier());
        Assertions.assertEquals("Moving Average Convergence/Divergence", indicator.metadata().name());
        Assertions.assertEquals(IndicatorCategory.INDICATOR, indicator.metadata().type());
    }

    @ParameterizedTest
    @MethodSource(value = "invalidArgs")
    void testShouldNotBeCreated(String json) {
        // ...build indicator
        assertThrows(ValueInstantiationException.class, () -> ConversionUtils.fromJson(json, Indicator.class));
    }

    @Test
    void testConversion() throws JsonProcessingException {
        // ...build indicator
        Indicator exampleIndicator = new MACDIndicator(minimumOptions());

        // ...compare string from macd and indicator
        String json = exampleIndicator.toString();

        // ...create new indicator from Json
        MACDIndicator indicator = (MACDIndicator) ConversionUtils.fromJson(json, Indicator.class);

        // ...check generic properties of this instance
        Assertions.assertEquals("macd", indicator.metadata().identifier());
        Assertions.assertEquals("Moving Average Convergence/Divergence", indicator.metadata().name());
        Assertions.assertEquals(IndicatorCategory.INDICATOR, indicator.metadata().type());

        // ...check options
        assertEquals(minimumOptions().getShortPeriod(), indicator.getOptions().getShortPeriod());
        assertEquals(minimumOptions().getLongPeriod(), indicator.getOptions().getLongPeriod());
        assertEquals(minimumOptions().getSignalPeriod(), indicator.getOptions().getSignalPeriod());
        assertEquals(minimumOptions().getSmothering(), indicator.getOptions().getSmothering());
        Assertions.assertEquals(minimumOptions().getTarget(), indicator.getOptions().getTarget());
    }

    @Test
    void testShouldNotBeCreatedForAnotherInput() {
        // ...given
        MACDIndicator indicator = new MACDIndicator(minimumOptions());

        // ...build indicator
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> indicator.run(IndicatorTests.emaInputs));

        assertThat(ex.getMessage(), CoreMatchers.containsString(MACDIndicator.INPUT_ERROR_MSG));
    }

    @Test
    void testRunningResult() {
        // ...given
        int shortPeriod = 2;
        int longPeriod = 5;
        int signalPeriod = 9;
        float allowedDelta = 0.03f;

        // ...build indicator
        MACDIndicatorOptions options = new MACDIndicatorOptions(shortPeriod, longPeriod, signalPeriod, null, null);

        Indicator indicator = new MACDIndicator(options);

        // ...use known IndicatorInput and IndicatorOutput
        MACDIndicatorInput input = new MACDIndicatorInput(IndicatorTests.predefinedInput);

        // ...run indicator
        MACDIndicatorOutput output = (MACDIndicatorOutput) indicator.run(input);

        // ...check obtain same results as known output
        Assertions.assertEquals(IndicatorTests.expectedMacd.length, output.getMacd().length);
        Assertions.assertEquals(IndicatorTests.expectedMacdSignal.length, output.getSignal().length);
        Assertions.assertEquals(IndicatorTests.expectedMacdHistogram.length, output.getHistogram().length);
        for (int i = 0; i < IndicatorTests.predefinedInput.length; i++) {
            // ...check each output data
            if (IndicatorTests.expectedMacd[i] == null) assertNull(output.getMacd()[i]);
            else Assertions.assertEquals(IndicatorTests.expectedMacd[i], output.getMacd()[i], allowedDelta);

            if (IndicatorTests.expectedMacdSignal[i] == null) assertNull(output.getSignal()[i]);
            else Assertions.assertEquals(IndicatorTests.expectedMacdSignal[i], output.getSignal()[i], allowedDelta);

            if (IndicatorTests.expectedMacdHistogram[i] == null) assertNull(output.getHistogram()[i]);
            else Assertions.assertEquals(IndicatorTests.expectedMacdHistogram[i], output.getHistogram()[i], allowedDelta);
        }
    }

    private static MACDIndicatorOptions minimumOptions() {
        return new MACDIndicatorOptions(
                9,
                16 + 1,
                20,
                3,
                IndicatorTarget.HIGH
        );
    }

    /**
     * Static method arguments created correctly
     *
     * @return stream of arguments
     */
    private static Stream<String> validArgs() {
        String[] JSONs = {
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":1,\"longPeriod\":2,\"signalPeriod\":1,\"smothering\":1,\"target\":\"CLOSE\"}}",
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":40,\"longPeriod\":78,\"signalPeriod\":43,\"smothering\":2,\"target\":\"HIGH\"}}",
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":7,\"longPeriod\":23,\"signalPeriod\":89,\"smothering\":5,\"target\":\"OPEN\"}}",
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":2,\"longPeriod\":47,\"signalPeriod\":4,\"target\":\"LOW\"}}",
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":3,\"longPeriod\":4,\"signalPeriod\":5,\"smothering\":8}}"
        };
        return Stream.of(JSONs);
    }

    /**
     * Static method arguments created correctly
     *
     * @return stream of arguments
     */
    private static Stream<String> invalidArgs() {
        String[] JSONs = {
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":0,\"longPeriod\":2,\"signalPeriod\":1}}",
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":10,\"longPeriod\":10,\"signalPeriod\":43}}",
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":7,\"longPeriod\":23,\"signalPeriod\":0}}",
                "{\"type\":\"macd\",\"options\":{\"shortPeriod\":2,\"longPeriod\":47,\"signalPeriod\":2,\"smothering\":0}}"
        };
        return Stream.of(JSONs);
    }
}
