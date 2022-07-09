package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import org.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Exponential Moving Average indicator output
 */
public class EMAIndicatorOutput implements IndicatorOutput {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * EMA output
     */
    private final Float[] ema;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of the {@link EMAIndicatorOutput}
     *
     * @param ema an array of numbers
     */
    @JsonCreator
    public EMAIndicatorOutput(@JsonProperty("ema") @NotNull Float[] ema) {
        this.ema = Optional.of(ema).get();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of EMA
     *
     * @return EMA
     */
    public Float[] getEma() {
        return this.ema;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public String toString() {
        return ConversionUtils.toJson(this);
    }
}
