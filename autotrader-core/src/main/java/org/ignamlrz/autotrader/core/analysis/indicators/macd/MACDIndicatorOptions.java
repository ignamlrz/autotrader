package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import lombok.Builder;
import lombok.Value;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOptions;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.springframework.lang.Nullable;

/**
 * Moving Average Convergence/Divergence indicator options
 */
@Value
public final class MACDIndicatorOptions implements IndicatorOptions {

    // Static fields
    static final int MIN_SHORT_PERIOD = 1;
    static final int MIN_SIGNAL_PERIOD = 1;
    static final int DEFAULT_SMOTHERING = 2;
    static final int MIN_SMOTHERING = 1;

    /**
     * Short period
     */
    int shortPeriod;

    /**
     * Long period
     */
    int longPeriod;

    /**
     * Signal period
     */
    int signalPeriod;

    /**
     * Indicator target
     */
    int smothering;

    /**
     * Indicator target
     */
    Indicator.Target target;

    /**
     * Constructor of a MACD Indicator Options
     *
     * @param shortPeriod  Short period
     * @param longPeriod   Long period
     * @param signalPeriod Signal period
     * @param smothering   Smothering applied on all EMAs
     * @param target       Target of this indicator
     */
    @Builder
    public MACDIndicatorOptions(int shortPeriod, int longPeriod, int signalPeriod, int smothering, @Nullable Indicator.Target target) {
        if (shortPeriod < MIN_SHORT_PERIOD) {
            throw new IllegalArgumentException("Short period can not be lower than 0");
        }
        if (longPeriod <= shortPeriod) {
            throw new IllegalArgumentException("Long period can not be lower or equal than short period");
        }
        if (signalPeriod < MIN_SIGNAL_PERIOD) {
            throw new IllegalArgumentException("Short period can not be lower than 0");
        }
        if (smothering < MIN_SMOTHERING) {
            throw new IllegalArgumentException("Smothering can not be lower than 0");
        }
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.signalPeriod = signalPeriod;
        this.smothering = smothering;
        this.target = IndicatorUtils.ofNullable(target);
    }
}
