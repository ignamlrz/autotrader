package io.github.ignamlrz.autotrader.core.analysis.indicators.macd;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ignamlrz.autotrader.core.analysis.AnalysisResult;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorCategory;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorType;
import io.github.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicator;
import io.github.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions;
import io.github.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOutput;
import io.github.ignamlrz.autotrader.core.model.market.ChartModel;
import io.github.ignamlrz.autotrader.core.utilities.FloatUtils;
import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;
import io.github.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import io.github.ignamlrz.autotrader.core.annotations.IndicatorMetadata;

import javax.naming.NameNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Moving Average Convergence/Divergence indicator
 *
 * @see <a href="https://www.investopedia.com/terms/m/macd.asp">investopedia.com</a>
 * @see <a href="https://tulipindicators.org/macd">tulipindicators.org</a>
 */
@IndicatorMetadata(identifier = IndicatorType.MACD, name = "Moving Average Convergence/Divergence", type = IndicatorCategory.INDICATOR)
public class MACDIndicator extends Indicator {

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    // Error messages
    static final String INPUT_ERROR_MSG = "input must be an MACD Indicator Input";
    static final String OUTPUT_ERROR_MSG = "output must be an MACD Indicator Output";

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * MACD Indicator options
     */
    MACDIndicatorOptions options;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of {@link MACDIndicator}
     *
     * @param options to use
     */
    @JsonCreator
    public MACDIndicator(@JsonProperty("options") @NotNull MACDIndicatorOptions options) {
        this.options = Optional.of(options).get();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    @Override
    public MACDIndicatorOptions getOptions() {
        return options;
    }

    // ========================================================
    // = OVERRIDE ANALYZABLE METHODS
    // ========================================================

    @Override
    public AnalysisResult analyze(IndicatorOutput output) {
        if (!(output instanceof MACDIndicatorOutput)) {
            throw new IllegalArgumentException(OUTPUT_ERROR_MSG);
        }
        throw new RuntimeException("Not implemented yet");
    }

    // ========================================================
    // = OVERRIDE INDICATOR PROCESSABLE METHODS
    // ========================================================

    @Override
    public <T extends IndicatorInput> IndicatorOutput run(T input) {
        if (!(input instanceof MACDIndicatorInput)) {
            throw new IllegalArgumentException(INPUT_ERROR_MSG);
        }

        return macd((MACDIndicatorInput) input);
    }

    @Override
    public <T extends ChartModel> IndicatorOutput run(T chart) {
        Float[] reals;
        try {
            reals = FloatUtils.arrayOf(chart.dataByTarget(this.options.getTarget()));
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        MACDIndicatorInput input = new MACDIndicatorInput(reals);
        return run(input);
    }

    // ========================================================
    // = OVERRIDE OBJECT METHODS
    // ========================================================

    @Override
    public int hashCode() {
        return metadata().name().hashCode() +
                this.options.getLongPeriod() * 1000
                + this.options.getShortPeriod() * 100
                + this.options.getSignalPeriod() * 10
                + this.options.getSmothering()
                + this.options.getTarget().hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        return false;
    }

    @Override
    public String toString() {
        return ConversionUtils.toJson(this);
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    /**
     * Process an MACD indicator
     *
     * @param input MACD indicator input
     * @return an MACD Indicator output
     */
    private MACDIndicatorOutput macd(MACDIndicatorInput input) {
        // ...calculate short and long EMA
        EMAIndicatorOutput shortOutput = ema(input.getReals(), this.options.getShortPeriod());
        EMAIndicatorOutput longOutput = ema(input.getReals(), this.options.getLongPeriod());

        // Calculate macd
        Float[] macd = FloatUtils.subtract(shortOutput.getEma(), longOutput.getEma());

        // ...values before longPeriod are marked as invalid
        FloatUtils.invalidateBefore(macd, this.options.getLongPeriod() - 1);

        // ...calculate signal
        Float[] signal = ema(macd, this.options.getSignalPeriod()).getEma();

        // ...calculate histogram
        Float[] histogram = FloatUtils.subtract(macd, signal);

        return new MACDIndicatorOutput(macd, signal, histogram);
    }

    /**
     * Process an EMA indicator
     *
     * @param input  Array of values to process
     * @param period Period to process
     * @return an array of results
     */
    EMAIndicatorOutput ema(Float[] input, int period) {
        EMAIndicatorOptions options = new EMAIndicatorOptions(period, this.options.getSmothering(), null);
        return EMAIndicator.run(input, options);
    }
}
