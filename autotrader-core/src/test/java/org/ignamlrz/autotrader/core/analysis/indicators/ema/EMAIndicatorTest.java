package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.lang.Nullable;

import java.util.stream.Stream;

import static org.ignamlrz.autotrader.core.analysis.indicators.IndicatorTests.expectedEma;
import static org.ignamlrz.autotrader.core.analysis.indicators.IndicatorTests.predefinedInput;
import static org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions.MIN_PERIOD;
import static org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions.MIN_SMOTHERING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EMAIndicatorTest {

    @ParameterizedTest
    @MethodSource(value = "validArgs")
    void testIsCreatedCorrectly(Integer period, Integer smothering, @Nullable Indicator.Target target) {
        // ...given
        int periodValue = (period != null) ? period : 0;
        int smotheringValue = (smothering != null) ? smothering : 0;
        EMAIndicator indicator = createNewEMAIndicator(periodValue, smotheringValue, target);

        // ...check generic properties of this instance
        assertEquals(EMAIndicator.IDENTIFIER, indicator.getIdentifier());
        assertEquals(EMAIndicator.NAME, indicator.getName());
        assertEquals(EMAIndicator.TYPE, indicator.getType());

        // ...check options
        assertEquals(period, indicator.getOptions().getPeriod());
        assertEquals(smothering, indicator.getOptions().getSmothering());
        assertEquals(IndicatorUtils.ofNullable(target), indicator.getOptions().getTarget());
    }

    @ParameterizedTest
    @MethodSource(value = "invalidArgs")
    void testShouldNotBeCreated() {
        // ...try build incorrect indicators
        assertThrows(IllegalArgumentException.class, () ->
                createNewEMAIndicator(MIN_PERIOD - 1, MIN_SMOTHERING, null)
        );
        assertThrows(IllegalArgumentException.class, () ->
                createNewEMAIndicator(MIN_PERIOD, MIN_SMOTHERING - 1, null)
        );
    }

    @Test
    void testRunningResult() {
        // ...given
        int period = 5;
        int smothering = 2;
        float allowedDelta = 0.3f;

        // ...build indicator
        Indicator indicator = createNewEMAIndicator(period, smothering, null);

        // ...use known IndicatorInput and IndicatorOutput
        EMAIndicatorInput input = new EMAIndicatorInput(predefinedInput);

        // ...run indicator
        EMAIndicatorOutput output = (EMAIndicatorOutput) indicator.run(input);

        // ...check obtain same results as known output
        assertEquals(expectedEma.length, output.getEma().length);
        for (int i = 0; i < expectedEma.length; i++) {
            // ...check each output data
            assertEquals(expectedEma[i], output.getEma()[i], allowedDelta);
        }
    }

    /**
     * Method for create a new EMA Indicator
     *
     * @param period     Period
     * @param smothering Smothering
     * @param target     Target
     * @return a new EMA indicator
     */
    private EMAIndicator createNewEMAIndicator(int period, int smothering, @Nullable Indicator.Target target) {
        // ...build EMA options
        EMAIndicatorOptions options = EMAIndicatorOptions.builder()
                .period(period)
                .smothering(smothering)
                .target(target)
                .build();

        // ...create the indicator
        return new EMAIndicator(options);
    }

    /**
     * Static method arguments created correctly
     *
     * @return stream of arguments
     */
    private static Stream<Arguments> validArgs() {
        return Stream.of(
                Arguments.of(MIN_PERIOD, MIN_SMOTHERING, Indicator.Target.OPEN),
                Arguments.of(MIN_PERIOD, MIN_SMOTHERING, Indicator.Target.HIGH),
                Arguments.of(MIN_PERIOD, MIN_SMOTHERING, Indicator.Target.LOW),
                Arguments.of(MIN_PERIOD, MIN_SMOTHERING, Indicator.Target.CLOSE),
                Arguments.of(MIN_PERIOD, MIN_SMOTHERING, null)
        );
    }

    /**
     * Static method arguments created correctly
     *
     * @return stream of arguments
     */
    private static Stream<Arguments> invalidArgs() {
        return Stream.of(
                Arguments.of(MIN_PERIOD - 1, MIN_SMOTHERING, null),
                Arguments.of(MIN_PERIOD, MIN_SMOTHERING - 1, null)
        );
    }
}

