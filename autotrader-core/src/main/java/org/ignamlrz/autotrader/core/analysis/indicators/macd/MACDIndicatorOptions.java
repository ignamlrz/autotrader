package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOptions;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorTarget;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Moving Average Convergence/Divergence indicator options
 */
public class MACDIndicatorOptions implements IndicatorOptions {

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    // Class options
    static final int MIN_SHORT_PERIOD = 1;
    static final int MIN_SIGNAL_PERIOD = 1;
    static final int DEFAULT_SMOTHERING = 2;
    static final int MIN_SMOTHERING = 1;

    // Error messages
    static final String MIN_SHORT_PERIOD_ERROR_MSG = "Short period can not be lower than " + MIN_SHORT_PERIOD + " (%d)";
    static final String MIN_LONG_PERIOD_ERROR_MSG = "Long period can not be lower or equal to a short period" + " (%d) <= (%d)";
    static final String MIN_SIGNAL_PERIOD_ERROR_MSG = "Signal period can not be lower than " + MIN_SIGNAL_PERIOD + " (%d)";
    static final String MIN_SMOTHERING_ERROR_MSG = "Smothering can not be lower than " + MIN_SMOTHERING + " (%d)";

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Short period
     */
    private final int shortPeriod;

    /**
     * Long period
     */
    private final int longPeriod;

    /**
     * Signal period
     */
    private final int signalPeriod;

    /**
     * Smothering
     */
    private final int smothering;

    /**
     * Indicator target
     */
    private final IndicatorTarget target;

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
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public MACDIndicatorOptions(
            @JsonProperty("shortPeriod") @NotNull Integer shortPeriod,
            @JsonProperty("longPeriod") @NotNull Integer longPeriod,
            @JsonProperty("signalPeriod") @NotNull Integer signalPeriod,
            @JsonProperty("smothering") @Nullable Integer smothering,
            @JsonProperty("target") @Nullable IndicatorTarget target
    ) {
        this.shortPeriod = validShortPeriod(shortPeriod);
        this.longPeriod = validLongPeriod(longPeriod);
        this.signalPeriod = validSignalPeriod(signalPeriod);
        this.smothering = validSmothering(smothering);
        this.target = IndicatorUtils.ofNullable(target);
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of Short Period option
     *
     * @return short period
     */
    public int getShortPeriod() {
        return shortPeriod;
    }

    /**
     * Getter of Long Period option
     *
     * @return long period
     */
    public int getLongPeriod() {
        return longPeriod;
    }

    /**
     * Getter of Signal Period option
     *
     * @return signal period
     */
    public int getSignalPeriod() {
        return signalPeriod;
    }

    /**
     * Getter of Smothering option
     *
     * @return smothering
     */
    public int getSmothering() {
        return smothering;
    }

    /**
     * Getter of Target option
     *
     * @return target
     */
    public IndicatorTarget getTarget() {
        return target;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public String toString() {
        return ConversionUtils.toJson(this);
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    private int validShortPeriod(Integer period) {
        int result = Optional.of(period).get();
        if (result < MIN_SHORT_PERIOD) {
            throw new IllegalArgumentException(String.format(MIN_SHORT_PERIOD_ERROR_MSG, result));
        }
        return result;
    }

    private int validLongPeriod(Integer period) {
        int result = Optional.of(period).get();
        if (result <= this.shortPeriod) {
            throw new IllegalArgumentException(String.format(MIN_LONG_PERIOD_ERROR_MSG, result, this.shortPeriod));
        }
        return result;
    }

    private int validSignalPeriod(Integer period) {
        int result = Optional.of(period).get();
        if (result < MIN_SIGNAL_PERIOD) {
            throw new IllegalArgumentException(String.format(MIN_SIGNAL_PERIOD_ERROR_MSG, result));
        }
        return result;
    }

    private int validSmothering(Integer smothering) {
        int result = Optional.ofNullable(smothering).orElse(DEFAULT_SMOTHERING);
        if (result < MIN_SMOTHERING) {
            throw new IllegalArgumentException(String.format(MIN_SMOTHERING_ERROR_MSG, this.smothering));
        }
        return result;
    }
}
