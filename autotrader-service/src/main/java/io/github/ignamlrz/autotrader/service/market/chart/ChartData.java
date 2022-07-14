package io.github.ignamlrz.autotrader.service.market.chart;

public class ChartData {

    // ========================================================
    // = ENUMS
    // ========================================================

    enum Order {
        OPEN, HIGH, LOW, CLOSE, VOLUME
    }

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    final Number[] candlestick;

    // ========================================================
    // = CONSTRUCTOR
    // ========================================================

    /**
     * Constructor of a chart data
     * @param candlestick array of candlestick data
     */
    public ChartData(Number[] candlestick) {
        this.candlestick = candlestick;
    }

    public Number[] getCandlestick() {
        return candlestick;
    }
}
