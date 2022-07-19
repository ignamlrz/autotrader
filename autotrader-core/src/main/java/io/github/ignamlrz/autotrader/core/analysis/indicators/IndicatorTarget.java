package io.github.ignamlrz.autotrader.core.analysis.indicators;

import io.github.ignamlrz.autotrader.core.repository.candlestick.Candlestick;

public enum IndicatorTarget {
    OPEN, CLOSE, HIGH, LOW, VOLUME;

    public Float of(Candlestick candlestick) {
        switch (this) {
            case VOLUME:
                return candlestick.getVolume();
            case OPEN:
                return candlestick.getOpen();
            case HIGH:
                return candlestick.getHigh();
            case LOW:
                return candlestick.getLow();
            case CLOSE:
            default:
                return candlestick.getClose();
        }
    }
}
