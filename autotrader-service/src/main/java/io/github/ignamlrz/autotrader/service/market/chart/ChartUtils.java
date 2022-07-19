package io.github.ignamlrz.autotrader.service.market.chart;

import io.github.ignamlrz.autotrader.core.repository.candlestick.Candlestick;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;
import io.github.ignamlrz.autotrader.core.utilities.time.Timeframe;

import javax.validation.constraints.NotNull;

public final class ChartUtils {

    // ========================================================
    // = STATIC METHODS
    // ========================================================

    /**
     * Method for obtain a {@link Candlestick} from an array of numbers
     *
     * @param numbers  Array of numbers
     * @param interval Interval associated to this array of numbers
     * @return a candlestick
     */
    public static Candlestick from(Number[] numbers, Interval interval, long timestamp) {
        if (numbers.length != ChartData.Order.values().length) {
            throw new IllegalArgumentException("numbers length must be equal to Order.values() length");
        }

        Timeframe timeframe = new Timeframe(timestamp, timestamp + interval.toMillis() - 1);
        Float open = (Float) numbers[ChartData.Order.OPEN.ordinal()];
        Float high = (Float) numbers[ChartData.Order.HIGH.ordinal()];
        Float low = (Float) numbers[ChartData.Order.LOW.ordinal()];
        Float close = (Float) numbers[ChartData.Order.CLOSE.ordinal()];
        Float volume = (Float) numbers[ChartData.Order.VOLUME.ordinal()];
        return new Candlestick(open, high, low, close, volume, 1, 5f);
    }

    /**
     * Method for convert a {@link Candlestick} to an array of numbers
     *
     * @param candlestick Target candlestick
     * @return an array of numbers
     */
    public static Number[] toArray(@NotNull Candlestick candlestick) {
        Number[] array = new Number[ChartData.Order.values().length];
        for (int i = 0; i < ChartData.Order.values().length; i++) {
            array[i] = extractDataFromCandlestick(candlestick, ChartData.Order.values()[i]);
        }
        return array;
    }

    // ========================================================
    // = PRIVATE STATIC METHODS
    // ========================================================

    /**
     * Extract data from a candlestick given an order
     *
     * @param candlestick from which will extract data
     * @param order       an order
     * @return data extracted from candlestick
     */
    private static Number extractDataFromCandlestick(Candlestick candlestick, ChartData.Order order) {
        switch (order) {
            case OPEN:
                return candlestick.getOpen();
            case HIGH:
                return candlestick.getHigh();
            case LOW:
                return candlestick.getLow();
            case CLOSE:
                return candlestick.getClose();
            case VOLUME:
                return candlestick.getVolume();
            default:
                throw new RuntimeException(order + " not expected");
        }
    }
}
