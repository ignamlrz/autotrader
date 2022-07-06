package org.ignamlrz.autotrader.core.analysis.indicator.ema;

import lombok.Getter;

/**
 * Exponential Moving Average indicator output data
 */
public final class EMAOutputData {

    /**
     * EMA
     */
    @Getter
    private final Float ema;

    public EMAOutputData(Float ema) {
        this.ema = ema;
    }
}
