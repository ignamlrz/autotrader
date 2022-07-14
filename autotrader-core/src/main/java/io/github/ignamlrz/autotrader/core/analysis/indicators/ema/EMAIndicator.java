package io.github.ignamlrz.autotrader.core.analysis.indicators.ema;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ignamlrz.autotrader.core.analysis.AnalysisResult;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorCategory;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorType;
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
 * Exponential Moving Average indicator
 *
 * @see <a href="https://www.investopedia.com/terms/e/ema.asp">investopedia.com</a>
 * @see <a href="https://tulipindicators.org/ema">tulipindicators.org</a>
 */
@IndicatorMetadata(identifier = IndicatorType.EMA, name = "Exponential Moving Average", type = IndicatorCategory.OVERLAY)
public class EMAIndicator extends Indicator {

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    // Error messages
    static final String INPUT_ERROR_MSG = "input must be an EMA Indicator Input";
    static final String OUTPUT_ERROR_MSG = "output must be an EMA Indicator Output";

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * EMA Indicator options
     */
    private final EMAIndicatorOptions options;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of {@link EMAIndicator}
     *
     * @param options to use
     */
    @JsonCreator
    public EMAIndicator(@JsonProperty("options") @NotNull EMAIndicatorOptions options) {
        this.options = Optional.of(options).get();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    @Override
    public EMAIndicatorOptions getOptions() {
        return options;
    }

    // ========================================================
    // = STATIC METHODS
    // ========================================================

    /**
     * Static method for run EMA indicator
     *
     * @param input Array of values to process
     * @return an array of results
     */
    public static EMAIndicatorOutput run(Float[] input, EMAIndicatorOptions options) {
        EMAIndicatorInput inputData = new EMAIndicatorInput(input);
        EMAIndicator emaIndicator = new EMAIndicator(options);
        return ((EMAIndicatorOutput) emaIndicator.run(inputData));
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public AnalysisResult analyze(IndicatorOutput output) {
        if (!(output instanceof EMAIndicatorOutput)) {
            throw new IllegalArgumentException("output must be an EMA Indicator Output");
        }
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T extends IndicatorInput> IndicatorOutput run(T input) {
        if (!(input instanceof EMAIndicatorInput)) {
            throw new IllegalArgumentException("input must be an EMA Indicator Input");
        }
        return ema((EMAIndicatorInput) input);
    }

    @Override
    public <T extends ChartModel> IndicatorOutput run(T chart) {
        Float[] reals;
        try {
            reals = FloatUtils.arrayOf(chart.dataByTarget(this.options.getTarget()));
        } catch (NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        EMAIndicatorInput input = new EMAIndicatorInput(reals);
        return run(input);
    }

    @Override
    public String toString() {
        return ConversionUtils.toJson(this);
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    /**
     * Process an EMA indicator
     *
     * @param input EMA indicator input
     * @return an EMA Indicator output
     */
    private EMAIndicatorOutput ema(EMAIndicatorInput input) {
        float scalingFactor = getScalingFactor();
        Float previousOutput = null;

        Float[] numbers = input.getReals();
        Float[] outputNumbers = new Float[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == null) {
                outputNumbers[i] = null;
                continue;
            }

            float aux = (previousOutput == null)
                    ? numbers[i]
                    : (1 - scalingFactor) * previousOutput + scalingFactor * numbers[i];
            outputNumbers[i] = aux;
            previousOutput = aux;
        }

        return new EMAIndicatorOutput(outputNumbers);
    }

    /**
     * Calculate scaling factor using instantiated period
     *
     * @return scaling factor
     */
    private float getScalingFactor() {
        float divisor = (float) (this.options.getPeriod() + 1);
        float dividend = this.options.getSmothering();
        return dividend / divisor;
    }

}
