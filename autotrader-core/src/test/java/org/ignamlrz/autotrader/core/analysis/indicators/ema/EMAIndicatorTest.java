package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.junit.jupiter.api.Test;
import org.springframework.lang.Nullable;

import java.util.Map;

import static org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions.MIN_PERIOD;
import static org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions.MIN_SMOTHERING;
import static org.junit.jupiter.api.Assertions.*;

class EMAIndicatorTest {

    // Static fields
    private static final float [] predefinedInput = {
            81.59f, 81.06f, 82.87f, 83.00f, 83.61f,
            83.15f, 82.84f, 83.99f, 84.55f, 84.36f,
            85.53f, 86.54f, 86.89f, 87.77f, 87.29f
    };

    private static final float[] expectedOutput = {
            81.59f, 81.41f, 81.90f, 82.27f, 82.71f,
            82.86f, 82.85f, 83.23f, 83.67f, 83.90f,
            84.44f, 85.14f, 85.73f, 86.70f, 86.41f
    };

    @Test
    void testIsCreatedCorrectly() {
        // ...given
        int period = MIN_PERIOD;
        int smothering = MIN_SMOTHERING;
        Indicator.Target target = Indicator.Target.CLOSE;

        // ...build indicator
        Indicator indicator = createNewEMAIndicator(period, smothering, null);

        // ...check generic properties of this instance
        assertEquals(EMAIndicator.IDENTIFIER, indicator.getIdentifier());
        assertEquals(EMAIndicator.NAME, indicator.getName());
        assertEquals(EMAIndicator.TYPE, indicator.getType());

        // ...check contain all keys on map of options
        Map<String, Object> optionsMap = indicator.getOptions().toMap();
        assertTrue(optionsMap.containsKey(EMAIndicatorOptions.Type.PERIOD.name()));
        assertTrue(optionsMap.containsKey(EMAIndicatorOptions.Type.SMOTHERING.name()));
        assertTrue(optionsMap.containsKey(EMAIndicatorOptions.Type.TARGET.name()));

        // ...check contain all values on map of options
        assertEquals(period, optionsMap.get(EMAIndicatorOptions.Type.PERIOD.name()));
        assertEquals(smothering, optionsMap.get(EMAIndicatorOptions.Type.SMOTHERING.name()));
        assertEquals(target, optionsMap.get(EMAIndicatorOptions.Type.TARGET.name()));
    }

    @Test
    void testShouldNotBeCreated() {
        // ...try build incorrect indicators
        assertThrows(IllegalArgumentException.class, () ->
                createNewEMAIndicator(MIN_PERIOD - 1, MIN_SMOTHERING, Indicator.Target.OPEN)
        );
        assertThrows(IllegalArgumentException.class, () ->
                createNewEMAIndicator(MIN_PERIOD, MIN_SMOTHERING - 1, Indicator.Target.HIGH)
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
        EMAIndicatorInput predefinedInput = new EMAIndicatorInput(EMAIndicatorTest.predefinedInput);
        EMAIndicatorOutput expectedOutput = new EMAIndicatorOutput(EMAIndicatorTest.expectedOutput);

        // ...run indicator
        EMAIndicatorOutput obtainedOutput = (EMAIndicatorOutput) indicator.run(predefinedInput);

        // ...check obtain same results as known output
        assertEquals(expectedOutput.getEma().length, obtainedOutput.getEma().length);
        for(int i = 0; i < expectedOutput.getEma().length; i++) {
            // ...check each output data
            float expectedReal = expectedOutput.getEma()[i];
            float obtainedReal = obtainedOutput.getEma()[i];
            assertEquals(expectedReal, obtainedReal, allowedDelta);
        }

        // ...check input map
        Map<String, float[]> inputMap = predefinedInput.toMap();
        assertEquals(predefinedInput.getReals().length, inputMap.get(EMAIndicatorInput.Type.REAL.name()).length);

        // ...check output map
        Map<String, float[]> outputMap = obtainedOutput.toMap();
        assertEquals(expectedOutput.getEma().length, outputMap.get(EMAIndicatorOutput.Type.EMA.name()).length);
    }

    /**
     * Method for create a new EMA Indicator
     *
     * @param period  Period
     * @param target       Target
     * @return a new EMA indicator
     */
    private Indicator createNewEMAIndicator(int period, int smothering, @Nullable Indicator.Target target) {
        // ...build EMA options
        EMAIndicatorOptions options = new EMAIndicatorOptions(period, smothering, target);

        // ...create the indicator
        return new EMAIndicator(options);
    }
}

