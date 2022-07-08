package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import lombok.Value;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

/**
 * Exponential Moving Average indicator output
 */
@Value
public class EMAIndicatorOutput implements IndicatorOutput {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Array of data
     */
    Float[] ema;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of the {@link EMAIndicatorOutput}
     *
     * @param ema an array of numbers
     */
    public EMAIndicatorOutput(Float[] ema) {
        this.ema = ema;
    }

}
