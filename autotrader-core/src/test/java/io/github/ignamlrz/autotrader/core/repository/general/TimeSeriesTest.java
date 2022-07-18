package io.github.ignamlrz.autotrader.core.repository.general;

import io.github.ignamlrz.autotrader.core.collection.LinkedUnrepeatableEnumMap;
import io.github.ignamlrz.autotrader.core.repository.candlestick.Candlestick;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TimeSeriesTest {

    LinkedUnrepeatableEnumMap<Candlestick.TypeData> binanceData = new LinkedUnrepeatableEnumMap<Candlestick.TypeData>(
            new Candlestick.TypeData[]{Candlestick.TypeData.OPEN, Candlestick.TypeData.CLOSE}
    );
    Candlestick c0 = new Candlestick(new Number[]{0 ,1}, binanceData);
    Candlestick c1 = new Candlestick(new Number[]{1 ,2}, binanceData);
    Candlestick c2 = new Candlestick(new Number[]{2 ,3}, binanceData);
    Candlestick c3 = new Candlestick(new Number[]{3 ,4}, binanceData);
    Candlestick c4 = new Candlestick(new Number[]{4 ,5}, binanceData);


    @Test
    void test_removalAndInsertions() {
        // ...do insertions
        TimeSeries<Candlestick> candlesticks = new TimeSeries<>();
        doInsertions(candlesticks);

        // ...check insertions
        assertEquals(c1, c0.next());
        assertEquals(c2, c1.next());
        assertEquals(c3, c2.next());
        assertEquals(c4, c3.next());
        assertNull(c4.next());
        assertNull(c0.previous());
        assertEquals(c0, c1.previous());
        assertEquals(c1, c2.previous());
        assertEquals(c2, c3.previous());
        assertEquals(c3, c4.previous());
    }

    private void doInsertions(TimeSeries<Candlestick> candlesticks) {
        candlesticks.insert(2L, c2);
        candlesticks.insert(0L, c0);
        candlesticks.insert(4L, c4);
        candlesticks.insert(1L, c1);
        candlesticks.insert(3L, c3);
    }

}