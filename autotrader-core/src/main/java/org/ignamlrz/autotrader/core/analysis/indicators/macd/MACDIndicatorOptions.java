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
public class MACDIndicatorOptions implements IndicatorOptions {

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    static final int MIN_SHORT_PERIOD = 1;
    static final int MIN_SIGNAL_PERIOD = 1;
    static final int DEFAULT_SMOTHERING = 2;
    static final int MIN_SMOTHERING = 1;

    // Error messages
    static final String MIN_SHORT_PERIOD_MSG = "Short period can not be lower than " + MIN_SHORT_PERIOD;
    static final String MIN_LONG_PERIOD_MSG = "Long period can not be lower or equal to a short period";
    static final String MIN_SIGNAL_PERIOD_MSG = "Signal period can not be lower than " + MIN_SIGNAL_PERIOD;
    static final String MIN_SMOTHERING_MSG = "Smothering can not be lower than " + MIN_SMOTHERING;

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

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

    // ========================================================
    // = CONSTRUCTOR
    // ========================================================

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
            throw new IllegalArgumentException(MIN_SHORT_PERIOD_MSG + String.format(" (%d)", shortPeriod));
        }
        if (longPeriod <= shortPeriod) {
            throw new IllegalArgumentException(MIN_LONG_PERIOD_MSG + String.format(" (%d) <= (%d)", longPeriod, shortPeriod));
        }
        if (signalPeriod < MIN_SIGNAL_PERIOD) {
            throw new IllegalArgumentException(MIN_SIGNAL_PERIOD_MSG + String.format(" (%d)", signalPeriod));
        }
        if (smothering < MIN_SMOTHERING) {
            throw new IllegalArgumentException(MIN_SMOTHERING_MSG + String.format(" (%d)", smothering));
        }
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.signalPeriod = signalPeriod;
        this.smothering = smothering;
        this.target = IndicatorUtils.ofNullable(target);
    }
}
