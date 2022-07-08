package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import lombok.Builder;
import lombok.Value;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOptions;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.springframework.lang.Nullable;

/**
 * Exponential Moving Average indicator options
 */
@Value
public class EMAIndicatorOptions implements IndicatorOptions {

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    static final int MIN_PERIOD = 1;
    static final int DEFAULT_SMOTHERING = 2;
    static final int MIN_SMOTHERING = 1;

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Period
     */
    int period;

    /**
     * Smothering
     */
    int smothering;

    /**
     * Indicator target
     */
    Indicator.Target target;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of EMA Indicator Options
     *
     * @param period     Period
     * @param smothering Smothering
     * @param target     Target
     */
    @Builder
    private EMAIndicatorOptions(int period, int smothering, @Nullable Indicator.Target target) {
        if (period < MIN_PERIOD) {
            throw new IllegalArgumentException("Period can not be lower than " + MIN_PERIOD);
        }
        if (smothering < MIN_SMOTHERING) {
            throw new IllegalArgumentException("Smothering can not be lower than " + MIN_SMOTHERING);
        }
        this.period = period;
        this.smothering = smothering;
        this.target = IndicatorUtils.ofNullable(target);
    }
}
