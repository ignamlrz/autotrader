package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import org.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Exponential Moving Average indicator input
 */
public class EMAIndicatorInput implements IndicatorInput {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Real numbers
     */
    private final Float[] reals;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of the {@link EMAIndicatorInput}
     *
     * @param reals an array of numbers
     */
    @JsonCreator
    public EMAIndicatorInput(@JsonProperty("reals") @NotNull Float[] reals) {
        this.reals = Optional.of(reals).get();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of reals
     *
     * @return reals
     */
    public Float[] getReals() {
        return this.reals;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public String toString() {
        return ConversionUtils.toJson(this);
    }
}
