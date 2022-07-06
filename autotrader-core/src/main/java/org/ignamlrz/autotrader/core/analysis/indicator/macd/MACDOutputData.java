package org.ignamlrz.autotrader.core.analysis.indicator.macd;

import lombok.Getter;

/**
 * Moving Average Convergence/Divergence indicator output data
 */
public final class MACDOutputData {
    /**
     * MACD
     */
    @Getter
    private final Float macd;

    /**
     * MACD signal
     */
    @Getter
    private final Float macdSignal;

    /**
     * MACD histogram
     */
    @Getter
    private final Float macdHistogram;

    public MACDOutputData(Float macd, Float macdSignal, Float macdHistogram) {
        this.macd = macd;
        this.macdSignal = macdSignal;
        this.macdHistogram = macdHistogram;
    }
}
