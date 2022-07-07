package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

/**
 * Moving Average Convergence/Divergence indicator output
 */
public final class MACDIndicatorOutput implements IndicatorOutput {

    /**
     * MACD
     */
    @Getter
    private final Float[] macd;

    /**
     * MACD signal
     */
    @Getter
    private final Float[] macdSignal;

    /**
     * MACD histogram
     */
    @Getter
    private final Float[] macdHistogram;

    /**
     * Constructor of the {@link MACDIndicatorOutput}
     *
     * @param macd MACD value array
     * @param macdSignal MACD signal array
     * @param macdHistogram MACD histogram array
     */
    public MACDIndicatorOutput(Float[] macd, Float[] macdSignal, Float[] macdHistogram) {
        this.macd = macd;
        this.macdSignal = macdSignal;
        this.macdHistogram = macdHistogram;
    }
}
