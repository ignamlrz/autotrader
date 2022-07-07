package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.junit.jupiter.api.Test;

import static org.ignamlrz.autotrader.core.analysis.indicators.macd.MACDIndicatorOptions.*;
import static org.junit.jupiter.api.Assertions.*;

class MACDIndicatorTest {

    // Static fields
    private static final Float[] predefinedInput = {
            81.59f, 81.06f, 82.87f, 83.00f, 83.61f,
            83.15f, 82.84f, 83.99f, 84.55f, 84.36f,
            85.53f, 86.54f, 86.89f, 87.77f, 87.29f
    };


    private static final Float[] expectedMacd = {
            null, null, null, null, 0.62f,
            0.35f, 0.11f, 0.42f, 0.58f, 0.42f,
            0.68f, 0.93f, 0.89f, 0.98f, 0.62f
    };

    private static final Float[] expectedSignal = {
            null, null, null, null, 0.62f,
            0.56f, 0.47f, 0.46f, 0.49f, 0.47f,
            0.52f, 0.60f, 0.66f, 0.72f, 0.70f
    };

    private static final Float[] expectedHistogram = {
            null, null, null, null, 0.00f,
            -0.21f, -0.36f, -0.05f, 0.09f, -0.05f,
            0.17f, 0.33f, 0.24f, 0.26f, -0.08f
    };


    @Test
    void testIsCreatedCorrectly() {
        // ...given
        int shortPeriod = MIN_SHORT_PERIOD;
        int longPeriod = MIN_SHORT_PERIOD + 1;
        int signalPeriod = MIN_SIGNAL_PERIOD;
        int smothering = MIN_SMOTHERING;

        // ...build indicator
        MACDIndicator indicator = createNewMACDIndicator(shortPeriod, longPeriod, signalPeriod, smothering);

        // ...check generic properties of this instance
        assertEquals(MACDIndicator.IDENTIFIER, indicator.getIdentifier());
        assertEquals(MACDIndicator.NAME, indicator.getName());
        assertEquals(MACDIndicator.TYPE, indicator.getType());

        // ...check options
        assertEquals(shortPeriod, indicator.getOptions().getShortPeriod());
        assertEquals(longPeriod, indicator.getOptions().getLongPeriod());
        assertEquals(signalPeriod, indicator.getOptions().getSignalPeriod());
        assertEquals(smothering, indicator.getOptions().getSmothering());
        assertEquals(IndicatorUtils.ofNullable(null), indicator.getOptions().getTarget());
    }

    @Test
    void testShouldNotBeCreated() {
        // ...given
        int invalidShort = MIN_SHORT_PERIOD - 1;
        int invalidSignal = MIN_SIGNAL_PERIOD - 1;
        int invalidSmothering = MIN_SMOTHERING - 1;

        // ...build indicator
        assertThrows(IllegalArgumentException.class, () ->
                createNewMACDIndicator(invalidShort, MIN_SHORT_PERIOD + 1, MIN_SIGNAL_PERIOD, MIN_SMOTHERING));
        assertThrows(IllegalArgumentException.class, () ->
                createNewMACDIndicator(MIN_SHORT_PERIOD, MIN_SHORT_PERIOD, MIN_SIGNAL_PERIOD, MIN_SMOTHERING));
        assertThrows(IllegalArgumentException.class, () ->
                createNewMACDIndicator(MIN_SHORT_PERIOD, MIN_SHORT_PERIOD + 1, invalidSignal, MIN_SMOTHERING));
        assertThrows(IllegalArgumentException.class, () ->
                createNewMACDIndicator(MIN_SHORT_PERIOD, MIN_SHORT_PERIOD + 1, MIN_SIGNAL_PERIOD, invalidSmothering));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void testRunningResult() {
        // ...given
        int shortPeriod = 2;
        int longPeriod = 5;
        int signalPeriod = 9;
        int smothering = DEFAULT_SMOTHERING;
        float allowedDelta = 0.03f;

        // ...build indicator
        MACDIndicator indicator = createNewMACDIndicator(shortPeriod, longPeriod, signalPeriod, smothering);

        // ...use known IndicatorInput and IndicatorOutput
        MACDIndicatorInput input = new MACDIndicatorInput(MACDIndicatorTest.predefinedInput);

        // ...run indicator
        MACDIndicatorOutput obtainedOutput = (MACDIndicatorOutput) indicator.run(input);

        // ...check obtain same results as known output
        assertEquals(expectedMacd.length, obtainedOutput.getMacd().length);
        assertEquals(expectedSignal.length, obtainedOutput.getMacdSignal().length);
        assertEquals(expectedHistogram.length, obtainedOutput.getMacdHistogram().length);
        for (int i = 0; i < predefinedInput.length; i++) {
            // ...check each output data
            if(expectedMacd[i] == null) assertNull(obtainedOutput.getMacd()[i]);
            else assertEquals(expectedMacd[i], obtainedOutput.getMacd()[i], allowedDelta);

            if(expectedSignal[i] == null) assertNull(obtainedOutput.getMacdSignal()[i]);
            else assertEquals(expectedSignal[i], obtainedOutput.getMacdSignal()[i], allowedDelta);

            if(expectedHistogram[i] == null) assertNull(obtainedOutput.getMacdHistogram()[i]);
            else assertEquals(expectedHistogram[i], obtainedOutput.getMacdHistogram()[i], allowedDelta);
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

}