package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import lombok.Value;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

/**
 * Moving Average Convergence/Divergence indicator output
 */
@Value
public class MACDIndicatorOutput implements IndicatorOutput {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * MACD
     */
    Float[] macd;

    /**
     * MACD signal
     */
    Float[] macdSignal;

    /**
     * MACD histogram
     */
    Float[] macdHistogram;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of the {@link MACDIndicatorOutput}
     *
     * @param macd          MACD value array
     * @param macdSignal    MACD signal array
     * @param macdHistogram MACD histogram array
     */
    public MACDIndicatorOutput(Float[] macd, Float[] macdSignal, Float[] macdHistogram) {
        this.macd = macd;
        this.macdSignal = macdSignal;
        this.macdHistogram = macdHistogram;
    }
}
