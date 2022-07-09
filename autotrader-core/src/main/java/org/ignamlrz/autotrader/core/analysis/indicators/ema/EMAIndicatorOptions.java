package org.ignamlrz.autotrader.core.analysis.indicators.ema;

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
 * Exponential Moving Average indicator options
 */
public class EMAIndicatorOptions implements IndicatorOptions {

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    // Class options
    static final int MIN_PERIOD = 1;
    static final int DEFAULT_SMOTHERING = 2;
    static final int MIN_SMOTHERING = 1;

    // Error messages
    static final String MIN_PERIOD_ERROR_MSG = "Period can not be lower than " + MIN_PERIOD;
    static final String MIN_SMOTHERING_ERROR_MSG = "Smothering can not be lower than " + MIN_SMOTHERING;

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Period
     */
    private final int period;

    /**
     * Smothering
     */
    private final int smothering;

    /**
     * Indicator target
     */
    private final IndicatorTarget target;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Default Constructor of EMA Indicator Options
     */
    public EMAIndicatorOptions() {
        this.period = MIN_PERIOD;
        this.smothering = MIN_SMOTHERING;
        this.target = IndicatorUtils.ofNullable(null);
    }

    /**
     * Constructor of EMA Indicator Options
     *
     * @param period     Period
     * @param smothering Smothering
     * @param target     Target
     */
    @JsonCreator
    public EMAIndicatorOptions(
            @JsonProperty("period") @NotNull Integer period,
            @JsonProperty("smothering") @Nullable Integer smothering,
            @JsonProperty("target") @Nullable IndicatorTarget target
    ) {

        this.period = validPeriod(period);
        this.smothering = validSmothering(smothering);
        this.target = IndicatorUtils.ofNullable(target);
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of Period option
     *
     * @return period
     */
    public int getPeriod() {
        return period;
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

    private int validPeriod(Integer period) {
        int result = Optional.of(period).get();
        if (result < MIN_PERIOD) {
            throw new IllegalArgumentException(MIN_PERIOD_ERROR_MSG + String.format(" (%d)", result));
        }
        return result;
    }

    private int validSmothering(Integer smothering) {
        int result = Optional.ofNullable(smothering).orElse(DEFAULT_SMOTHERING);
        if (result < MIN_SMOTHERING) {
            throw new IllegalArgumentException(MIN_SMOTHERING_ERROR_MSG + String.format(" (%d)", result));
        }
        return result;
    }
}
