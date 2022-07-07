package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.junit.jupiter.api.Test;

import static org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions.MIN_PERIOD;
import static org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions.MIN_SMOTHERING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EMAIndicatorTest {

    // Static fields
    private static final Float[] predefinedInput = {
            81.59f, 81.06f, 82.87f, 83.00f, 83.61f,
            83.15f, 82.84f, 83.99f, 84.55f, 84.36f,
            85.53f, 86.54f, 86.89f, 87.77f, 87.29f
    };

    private static final Float[] expectedEma = {
            81.59f, 81.41f, 81.90f, 82.27f, 82.71f,
            82.86f, 82.85f, 83.23f, 83.67f, 83.90f,
            84.44f, 85.14f, 85.73f, 86.70f, 86.41f
    };

    @Test
    void testIsCreatedCorrectly() {
        // ...given
        int period = MIN_PERIOD;
        int smothering = MIN_SMOTHERING;

        // ...build indicator
        EMAIndicator indicator = createNewEMAIndicator(period, smothering);

        // ...check generic properties of this instance
        assertEquals(EMAIndicator.IDENTIFIER, indicator.getIdentifier());
        assertEquals(EMAIndicator.NAME, indicator.getName());
        assertEquals(EMAIndicator.TYPE, indicator.getType());

        // ...check options
        assertEquals(period, indicator.getOptions().getPeriod());
        assertEquals(smothering, indicator.getOptions().getSmothering());
        assertEquals(IndicatorUtils.ofNullable(null), indicator.getOptions().getTarget());
    }

    @Test
    void testShouldNotBeCreated() {
        // ...try build incorrect indicators
        assertThrows(IllegalArgumentException.class, () ->
                createNewEMAIndicator(MIN_PERIOD - 1, MIN_SMOTHERING)
        );
        assertThrows(IllegalArgumentException.class, () ->
                createNewEMAIndicator(MIN_PERIOD, MIN_SMOTHERING - 1)
        );
    }

    @Test
    void testRunningResult() {
        // ...given
        int period = 5;
        int smothering = 2;
        float allowedDelta = 0.3f;

        // ...build indicator
        Indicator indicator = createNewEMAIndicator(period, smothering);

        // ...use known IndicatorInput and IndicatorOutput
        EMAIndicatorInput input = new EMAIndicatorInput(EMAIndicatorTest.predefinedInput);

        // ...run indicator
        EMAIndicatorOutput obtainedOutput = (EMAIndicatorOutput) indicator.run(input);

        // ...check obtain same results as known output
        assertEquals(expectedEma.length, obtainedOutput.getEma().length);
        for (int i = 0; i < expectedEma.length; i++) {
            // ...check each output data
            assertEquals(expectedEma[i], obtainedOutput.getEma()[i], allowedDelta);
        }
    }

    /**
     * Method for create a new EMA Indicator
     *
     * @param period     Period
     * @param smothering Smothering
     * @return a new EMA indicator
     */
    private EMAIndicator createNewEMAIndicator(int period, int smothering) {
        // ...build EMA options
        EMAIndicatorOptions options = new EMAIndicatorOptions(period, smothering, null);

        // ...create the indicator
        return new EMAIndicator(options);
    }
}

