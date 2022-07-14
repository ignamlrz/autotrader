package io.github.ignamlrz.autotrader.core.analysis.indicators.macd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

import javax.validation.constraints.NotNull;

/**
 * Moving Average Convergence/Divergence indicator output
 */
public class MACDIndicatorOutput implements IndicatorOutput {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * MACD output
     */
    private final Float[] macd;

    /**
     * MACD signal output
     */
    private final Float[] signal;

    /**
     * MACD histogram output
     */
    private final Float[] histogram;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of the {@link MACDIndicatorOutput}
     *
     * @param macd      MACD value array
     * @param signal    MACD signal array
     * @param histogram MACD histogram array
     */
    @JsonCreator
    public MACDIndicatorOutput(
            @JsonProperty("macd") @NotNull Float[] macd,
            @JsonProperty("signal") @NotNull Float[] signal,
            @JsonProperty("histogram") @NotNull Float[] histogram
    ) {
        this.macd = macd;
        this.signal = signal;
        this.histogram = histogram;
    }

    // ========================================================
    // = GETTERS
    // ========================================================


    /**
     * Getter of MACD
     *
     * @return MACD
     */
    public Float[] getMacd() {
        return macd;
    }

    /**
     * Getter of MACD signal
     *
     * @return MACD signal
     */
    public Float[] getSignal() {
        return signal;
    }

    /**
     * Getter of MACD histogram
     *
     * @return MACD histogram
     */
    public Float[] getHistogram() {
        return histogram;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public String toString() {
        return ConversionUtils.toJson(this);
    }
}
