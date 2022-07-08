package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import lombok.Value;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;

/**
 * Moving Average Convergence/Divergence indicator input
 */
@Value
public class MACDIndicatorInput implements IndicatorInput {

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
     * Constructor of the {@link MACDIndicatorInput}
     *
     * @param reals an array of numbers
     */
    public MACDIndicatorInput(Float[] reals) {
        this.reals = reals;
    }
}
