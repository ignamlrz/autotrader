package io.github.ignamlrz.autotrader.core.analysis.indicators.macd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Moving Average Convergence/Divergence indicator input
 */
public class MACDIndicatorInput implements IndicatorInput {

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
     * Constructor of the {@link MACDIndicatorInput}
     *
     * @param reals an array of numbers
     */
    @JsonCreator
    public MACDIndicatorInput(@JsonProperty("reals") @NotNull Float[] reals) {
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
