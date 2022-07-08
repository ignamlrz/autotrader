package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import lombok.Value;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;

/**
 * Exponential Moving Average indicator input
 */
@Value
public class EMAIndicatorInput implements IndicatorInput {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Array of numbers
     */
    Float[] reals;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of the {@link EMAIndicatorInput}
     *
     * @param reals an array of numbers
     */
    public EMAIndicatorInput(Float[] reals) {
        this.reals = reals;
    }

}
