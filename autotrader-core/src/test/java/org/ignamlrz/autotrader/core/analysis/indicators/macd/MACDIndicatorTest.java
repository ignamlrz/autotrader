package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.ignamlrz.autotrader.core.analysis.indicators.IndicatorTests.*;
import static org.ignamlrz.autotrader.core.analysis.indicators.macd.MACDIndicatorOptions.*;
import static org.junit.jupiter.api.Assertions.*;

class MACDIndicatorTest {

    @ParameterizedTest
    @MethodSource(value = "validArgs")
    void testIsCreatedCorrectly(MACDIndicatorOptions options) {
        // ...build indicator
        MACDIndicator indicator = new MACDIndicator(options);

        // ...check generic properties of this instance
        assertEquals(MACDIndicator.IDENTIFIER, indicator.getIdentifier());
        assertEquals(MACDIndicator.NAME, indicator.getName());
        assertEquals(MACDIndicator.TYPE, indicator.getType());

        // ...check options
        assertEquals(options.getShortPeriod(), indicator.getOptions().getShortPeriod());
        assertEquals(options.getLongPeriod(), indicator.getOptions().getLongPeriod());
        assertEquals(options.getSignalPeriod(), indicator.getOptions().getSignalPeriod());
        assertEquals(options.getSmothering(), indicator.getOptions().getSmothering());
        assertEquals(options.getTarget(), indicator.getOptions().getTarget());
    }

    @ParameterizedTest
    @MethodSource(value = "invalidArgs")
    void testShouldNotBeCreated(int shortPeriod, int longPeriod, int signalPeriod, int smothering, String msg) {
        // ...build indicator
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                createNewMACDIndicator(shortPeriod, longPeriod, signalPeriod, smothering)
        );

        assertTrue(ex.getMessage().contains(msg));
    }

    @Test
    void testRunningResult() {
        // ...given
        int shortPeriod = 2;
        int longPeriod = 5;
        int signalPeriod = 9;
        float allowedDelta = 0.03f;

        // ...build indicator
        MACDIndicator indicator = createNewMACDIndicator(shortPeriod, longPeriod, signalPeriod, DEFAULT_SMOTHERING);

        // ...use known IndicatorInput and IndicatorOutput
        MACDIndicatorInput input = new MACDIndicatorInput(predefinedInput);

        // ...run indicator
        MACDIndicatorOutput output = (MACDIndicatorOutput) indicator.run(input);

        // ...check obtain same results as known output
        assertEquals(expectedMacd.length, output.getMacd().length);
        assertEquals(expectedMacdSignal.length, output.getMacdSignal().length);
        assertEquals(expectedMacdHistogram.length, output.getMacdHistogram().length);
        for (int i = 0; i < predefinedInput.length; i++) {
            // ...check each output data
            if (expectedMacd[i] == null) assertNull(output.getMacd()[i]);
            else assertEquals(expectedMacd[i], output.getMacd()[i], allowedDelta);

            if (expectedMacdSignal[i] == null) assertNull(output.getMacdSignal()[i]);
            else assertEquals(expectedMacdSignal[i], output.getMacdSignal()[i], allowedDelta);

            if (expectedMacdHistogram[i] == null) assertNull(output.getMacdHistogram()[i]);
            else assertEquals(expectedMacdHistogram[i], output.getMacdHistogram()[i], allowedDelta);
        }
    }

    /**
     * Method for create a new MACD Indicator
     *
     * @param shortPeriod  Short period
     * @param longPeriod   Long period
     * @param signalPeriod Signal period
     * @param smothering   Smothering
     * @return a new MACD indicator
     */
    private MACDIndicator createNewMACDIndicator(int shortPeriod, int longPeriod, int signalPeriod, int smothering) {
        // ...build MACD options
        MACDIndicatorOptions options = new MACDIndicatorOptions(shortPeriod, longPeriod, signalPeriod, smothering, null);

        // ...create the indicator
        return new MACDIndicator(options);
    }

    /**
     * Static method arguments created correctly
     *
     * @return stream of arguments
     */
    private static Stream<Arguments> validArgs() {
        List<Arguments> arguments = new ArrayList<>();
        for (Indicator.Target target : Indicator.Target.values()) {
            MACDIndicatorOptions options = new MACDIndicatorOptions(
                    MIN_SHORT_PERIOD,
                    MIN_SHORT_PERIOD + 1,
                    MIN_SIGNAL_PERIOD,
                    MIN_SMOTHERING,
                    target
            );
            arguments.add(Arguments.of(options));
        }
        return arguments.stream();
    }

    /**
     * Static method arguments created correctly
     *
     * @return stream of arguments
     */
    private static Stream<Arguments> invalidArgs() {
        return Stream.of(
                Arguments.of(   //  Minimum short period error
                        MIN_SHORT_PERIOD - 1,
                        MIN_SHORT_PERIOD + 1,
                        MIN_SIGNAL_PERIOD,
                        MIN_SMOTHERING,
                        MIN_SHORT_PERIOD_MSG
                ),
                Arguments.of(   // Long period must be greater than short period
                        MIN_SHORT_PERIOD,
                        MIN_SHORT_PERIOD,
                        MIN_SIGNAL_PERIOD,
                        MIN_SMOTHERING,
                        MIN_LONG_PERIOD_MSG
                ),
                Arguments.of(   //  Minimum signal period error
                        MIN_SHORT_PERIOD,
                        MIN_SHORT_PERIOD + 1,
                        MIN_SIGNAL_PERIOD - 1,
                        MIN_SMOTHERING,
                        MIN_SIGNAL_PERIOD_MSG
                ),
                Arguments.of(   //  Minimum smothering error
                        MIN_SHORT_PERIOD,
                        MIN_SHORT_PERIOD + 1,
                        MIN_SIGNAL_PERIOD,
                        MIN_SMOTHERING - 1,
                        MIN_SMOTHERING_MSG
                )
        );
    }

}