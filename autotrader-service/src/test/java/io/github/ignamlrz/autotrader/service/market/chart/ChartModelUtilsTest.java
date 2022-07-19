package io.github.ignamlrz.autotrader.service.market.chart;

import io.github.ignamlrz.autotrader.core.repository.candlestick.Candlestick;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ChartModelUtilsTest {

    static final Interval INTERVAL = Interval.DAY_1;
    static final long TIMESTAMP = 200;
    static final float OPEN_PRICE = 1.f;
    static final float HIGH_PRICE = 2.f;
    static final float LOW_PRICE = 0.5f;
    static final float CLOSE_PRICE = 2.f;
    static final float VOLUME = 324.6f;

    Number[] numbers = new Number[]{OPEN_PRICE, HIGH_PRICE, LOW_PRICE, CLOSE_PRICE, VOLUME};
    Candlestick candlestick = new Candlestick(
            OPEN_PRICE,
            HIGH_PRICE,
            LOW_PRICE,
            CLOSE_PRICE,
            VOLUME,
            1,
            5f
    );

    @Test
    @Order(1)
    void test_creationOfCandlestick() {
        // ...test illegal argument exceptions
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> ChartUtils.from(new Number[0], INTERVAL, 0)
        );
        assertEquals(ex.getMessage(), "numbers length must be equal to Order.values() length");

        // ...test correct creation
        Candlestick candlestick = ChartUtils.from(numbers, INTERVAL, TIMESTAMP);

        assertEquals(OPEN_PRICE, candlestick.getOpen());
        assertEquals(HIGH_PRICE, candlestick.getHigh());
        assertEquals(LOW_PRICE, candlestick.getLow());
        assertEquals(CLOSE_PRICE, candlestick.getClose());
        assertEquals(VOLUME, candlestick.getVolume());

        this.candlestick = candlestick;
    }

    @Test
    @Order(2)
    void test_creationOfArray() {
        // ...check obtained numbers is equal to numbers
        Number[] obtainedNumbers = ChartUtils.toArray(this.candlestick);
        for (int i = 0; i < ChartData.Order.values().length; i++) {
            assertEquals(this.numbers[i], obtainedNumbers[i]);
        }
    }

}