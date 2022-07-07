package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;

/**
 * Exponential Moving Average indicator input
 */
public final class EMAIndicatorInput implements IndicatorInput {

    /**
     * Array of numbers
     */
    @Getter
    private final Float[] reals;

    /**
     * Constructor of the {@link EMAIndicatorInput}
     *
     * @param reals an array of numbers
     */
    public EMAIndicatorInput(Float[] reals) {
        this.reals = reals;
    }

}
