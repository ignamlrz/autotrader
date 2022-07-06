package org.ignamlrz.autotrader.core.analysis.indicator.macd;

import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MACDIndicatorTest {

    @Test
    void testIsCreatedCorrectly() {
        // ...given
        int shortPeriod = 5;
        int longPeriod = 6;
        int signalPeriod = 6;
        Indicator.Target target = Indicator.Target.CLOSE;

        // ...build indicator
        Indicator indicator = createNewMACDIndicator(shortPeriod, longPeriod, signalPeriod, null);

        // ...check generic properties of this instance
        assertEquals(MACDIndicator.IDENTIFIER, indicator.getIdentifier());
        assertEquals(MACDIndicator.NAME, indicator.getName());
        assertEquals(MACDIndicator.TYPE, indicator.getType());

        // ...check contain all keys on map of options
        Map<String, Object> optionsMap = indicator.getOptions().toMap();
        assertTrue(optionsMap.containsKey(MACDOptions.Type.SHORT_PERIOD.name()));
        assertTrue(optionsMap.containsKey(MACDOptions.Type.LONG_PERIOD.name()));
        assertTrue(optionsMap.containsKey(MACDOptions.Type.SIGNAL_PERIOD.name()));
        assertTrue(optionsMap.containsKey(MACDOptions.Type.TARGET.name()));

        // ...check contain all values on map of options
        assertEquals(shortPeriod, optionsMap.get(MACDOptions.Type.SHORT_PERIOD.name()));
        assertEquals(longPeriod, optionsMap.get(MACDOptions.Type.LONG_PERIOD.name()));
        assertEquals(signalPeriod, optionsMap.get(MACDOptions.Type.SIGNAL_PERIOD.name()));
        assertEquals(target, optionsMap.get(MACDOptions.Type.TARGET.name()));
    }

    @Test
    void testShouldNotBeCreated() {
        // ...given
        int shortPeriod = 5;
        int longPeriod = 5;
        int signalPeriod = 5;

        // ...build indicator
        assertThrows(IllegalArgumentException.class, () -> createNewMACDIndicator(0, longPeriod, signalPeriod, null));
        assertThrows(IllegalArgumentException.class, () -> createNewMACDIndicator(shortPeriod, 0, signalPeriod, null));
        assertThrows(IllegalArgumentException.class, () -> createNewMACDIndicator(shortPeriod, longPeriod, 0, null));
    }

    /**
     * Method for create a new MACD Indicator
     *
     * @param shortPeriod  Short period
     * @param longPeriod   Long period
     * @param signalPeriod Signal period
     * @param target       Target
     * @return a new MACD indicator
     */
    private Indicator createNewMACDIndicator(int shortPeriod, int longPeriod, int signalPeriod, Indicator.Target target) {
        // ...build MACD options
        MACDOptions options = MACDOptions.builder()
                .shortPeriod(shortPeriod)
                .longPeriod(longPeriod)
                .signalPeriod(signalPeriod)
                .target(target)
                .build();

        // ...create the indicator
        return new MACDIndicator(options);
    }

}