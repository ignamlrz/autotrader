package org.ignamlrz.autotrader.core.analysis.indicator.ema;

import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EMAIndicatorTest {

    @Test
    void testIsCreatedCorrectly() {
        // ...given
        int period = 5;
        Indicator.Target target = Indicator.Target.CLOSE;

        // ...build indicator
        Indicator indicator = createNewEMAIndicator(period, null);

        // ...check generic properties of this instance
        assertEquals(EMAIndicator.IDENTIFIER, indicator.getIdentifier());
        assertEquals(EMAIndicator.NAME, indicator.getName());
        assertEquals(EMAIndicator.TYPE, indicator.getType());

        // ...check contain all keys on map of options
        Map<String, Object> optionsMap = indicator.getOptions().toMap();
        assertTrue(optionsMap.containsKey(EMAOptions.Type.PERIOD.name()));
        assertTrue(optionsMap.containsKey(EMAOptions.Type.TARGET.name()));

        // ...check contain all values on map of options
        assertEquals(period, optionsMap.get(EMAOptions.Type.PERIOD.name()));
        assertEquals(target, optionsMap.get(EMAOptions.Type.TARGET.name()));
    }

    @Test
    void testShouldNotBeCreated() {
        // ...given
        int period = 3;

        // ...build indicator
        assertThrows(IllegalArgumentException.class, () -> createNewEMAIndicator(-period, null));
        assertThrows(IllegalArgumentException.class, () -> createNewEMAIndicator(0, null));
    }

    /**
     * Method for create a new EMA Indicator
     *
     * @param period  Period
     * @param target       Target
     * @return a new EMA indicator
     */
    private Indicator createNewEMAIndicator(int period, Indicator.Target target) {
        // ...build EMA options
        EMAOptions options = EMAOptions.builder()
                .period(period)
                .target(target)
                .build();

        // ...create the indicator
        return new EMAIndicator(options);
    }

}