package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.ignamlrz.autotrader.core.analysis.indicators.IndicatorTests.expectedEma;
import static org.ignamlrz.autotrader.core.analysis.indicators.IndicatorTests.predefinedInput;
import static org.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils.fromJson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EMAIndicatorTest {

    @ParameterizedTest
    @MethodSource(value = "validArgs")
    void testIsCreatedCorrectly(String json) throws JsonProcessingException {
        // ...given
        EMAIndicator indicator = (EMAIndicator) fromJson(json, Indicator.class);

        // ...check generic properties of this instance
        assertEquals("ema", indicator.metadata().identifier());
        assertEquals("Exponential Moving Average", indicator.metadata().name());
        assertEquals(IndicatorCategory.OVERLAY, indicator.metadata().type());
    }

    @ParameterizedTest
    @MethodSource(value = "invalidArgs")
    void testShouldNotBeCreated(String json) {
        // ...build indicator
        assertThrows(ValueInstantiationException.class, () -> fromJson(json, Indicator.class));
    }

    @Test
    void testRunningResult() {
        // ...given
        int period = 5;
        int smothering = 2;
        float allowedDelta = 0.3f;

        // ...build indicator
        EMAIndicatorOptions options = new EMAIndicatorOptions(period, smothering, null);
        Indicator indicator = new EMAIndicator(options);

        // ...use predefined IndicatorInput
        EMAIndicatorInput input = new EMAIndicatorInput(predefinedInput);

        // ...run indicator
        EMAIndicatorOutput output = (EMAIndicatorOutput) indicator.run(input);

        // ...check obtain same results with expected output
        assertEquals(expectedEma.length, output.getEma().length);
        for (int i = 0; i < expectedEma.length; i++) {
            // ...check each output data
            assertEquals(expectedEma[i], output.getEma()[i], allowedDelta);
        }
    }

    /**
     * Static method of JSONs created correctly
     *
     * @return stream of JSONs
     */
    private static Stream<String> validArgs() {
        String[] JSONs = {
                "{\"type\":\"ema\",\"options\":{\"period\":1,\"smothering\":1,\"target\":\"OPEN\"}}",
                "{\"type\":\"ema\",\"options\":{\"period\":1,\"smothering\":1,\"target\":\"HIGH\"}}",
                "{\"type\":\"ema\",\"options\":{\"period\":1,\"smothering\":1,\"target\":\"LOW\"}}",
                "{\"type\":\"ema\",\"options\":{\"period\":1,\"smothering\":1,\"target\":\"CLOSE\"}}",
                "{\"type\":\"ema\",\"options\":{\"period\":1,\"smothering\":1}}",
                "{\"type\":\"ema\",\"options\":{\"period\":1}}"
        };
        return Stream.of(JSONs);
    }

    /**
     * Static method of JSONs created correctly
     *
     * @return stream of JSONs
     */
    private static Stream<String> invalidArgs() {
        String[] JSONs = {
                "{\"type\":\"ema\",\"options\":{\"period\":0,\"smothering\":1}}",
                "{\"type\":\"ema\",\"options\":{\"period\":1,\"smothering\":0}}"
        };
        return Stream.of(JSONs);
    }
}
