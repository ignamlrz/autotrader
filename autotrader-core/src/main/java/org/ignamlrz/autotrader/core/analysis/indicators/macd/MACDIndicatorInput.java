package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;

/**
 * Moving Average Convergence/Divergence indicator input
 */
public final class MACDIndicatorInput implements IndicatorInput {

    /**
     * Array of numbers
     */
    @Getter
    private final Float[] reals;

    /**
     * Constructor of the {@link MACDIndicatorInput}
     *
     * @param reals an array of numbers
     */
    public MACDIndicatorInput(Float[] reals) {
        this.reals = reals;
    }
}
