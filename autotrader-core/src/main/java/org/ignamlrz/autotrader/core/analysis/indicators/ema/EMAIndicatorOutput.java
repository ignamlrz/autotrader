package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

/**
 * Exponential Moving Average indicator output
 */
public final class EMAIndicatorOutput implements IndicatorOutput {

    /**
     * Array of data
     */
    @Getter
    private final Float[] ema;

    /**
     * Constructor of the {@link EMAIndicatorOutput}
     *
     * @param ema an array of numbers
     */
    public EMAIndicatorOutput(Float[] ema) {
        this.ema = ema;
    }

}
