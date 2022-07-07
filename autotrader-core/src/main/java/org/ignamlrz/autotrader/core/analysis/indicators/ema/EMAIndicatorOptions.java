package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOptions;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.springframework.lang.Nullable;

/**
 * Exponential Moving Average indicator options
 */
public final class EMAIndicatorOptions implements IndicatorOptions {

    // Static fields
    static final int MIN_PERIOD = 1;
    static final int DEFAULT_SMOTHERING = 2;
    static final int MIN_SMOTHERING = 1;

    // Fields
    /**
     * Period
     */
    @Getter
    private final int period;

    /**
     * Smothering
     */
    @Getter
    private final int smothering;

    /**
     * Indicator target
     */
    @Getter
    private final Indicator.Target target;

    public EMAIndicatorOptions(int period, @Nullable Indicator.Target target) {
        this(period, DEFAULT_SMOTHERING, target);
    }

    public EMAIndicatorOptions(int period, int smothering, @Nullable Indicator.Target target) {
        if (period < MIN_PERIOD) {
            throw new IllegalArgumentException("Period can not be lower than " + MIN_PERIOD);
        }
        if(smothering < MIN_SMOTHERING) {
            throw new IllegalArgumentException("Smothering can not be lower than " + MIN_SMOTHERING);
        }
        this.period = period;
        this.smothering = smothering;
        this.target = IndicatorUtils.ofNullable(target);
    }
}