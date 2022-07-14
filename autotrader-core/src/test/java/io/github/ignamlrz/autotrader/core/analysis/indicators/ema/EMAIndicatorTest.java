package io.github.ignamlrz.autotrader.core.analysis.indicators.ema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import io.github.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorCategory;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorTests;
import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EMAIndicatorTest {

    @ParameterizedTest
    @MethodSource(value = "validArgs")
    void testIsCreatedCorrectly(String json) throws JsonProcessingException {
        // ...given
        EMAIndicator indicator = (EMAIndicator) ConversionUtils.fromJson(json, Indicator.class);

        // ...check generic properties of this instance
        Assertions.assertEquals("ema", indicator.metadata().identifier());
        Assertions.assertEquals("Exponential Moving Average", indicator.metadata().name());
        Assertions.assertEquals(IndicatorCategory.OVERLAY, indicator.metadata().type());
    }

    @ParameterizedTest
    @MethodSource(value = "invalidArgs")
    void testShouldNotBeCreated(String json) {
        // ...build indicator
        assertThrows(ValueInstantiationException.class, () -> ConversionUtils.fromJson(json, Indicator.class));
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
        EMAIndicatorInput input = new EMAIndicatorInput(IndicatorTests.predefinedInput);

        // ...run indicator
        EMAIndicatorOutput output = (EMAIndicatorOutput) indicator.run(input);

        // ...check obtain same results with expected output
        Assertions.assertEquals(IndicatorTests.expectedEma.length, output.getEma().length);
        for (int i = 0; i < IndicatorTests.expectedEma.length; i++) {
            // ...check each output data
            Assertions.assertEquals(IndicatorTests.expectedEma[i], output.getEma()[i], allowedDelta);
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
