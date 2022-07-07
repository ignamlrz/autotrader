package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import org.ignamlrz.autotrader.core.analysis.AnalysisResult;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import org.ignamlrz.autotrader.core.model.market.BasicChart;
import org.ignamlrz.autotrader.core.utilities.FloatUtils;

/**
 * Exponential Moving Average indicator
 *
 * @see <a href="https://www.investopedia.com/terms/e/ema.asp">investopedia.com</a>
 * @see <a href="https://tulipindicators.org/ema">tulipindicators.org</a>
 */
public final class EMAIndicator extends Indicator {

    static final String IDENTIFIER = "ema";
    static final String NAME = "Exponential Moving Average";
    static final Type TYPE = Type.OVERLAY;

    /**
     * Constructor of the {@link EMAIndicator}
     *
     * @param options to use
     */
    public EMAIndicator(EMAIndicatorOptions options) {
        super(IDENTIFIER, NAME, TYPE, options);
    }

    /**
     * Method for get {@link EMAIndicator} options
     *
     * @return EMA Indicator options
     */
    @Override
    public EMAIndicatorOptions getOptions() {
        return (EMAIndicatorOptions) this.options;
    }

    /**
     * Static method for run an EMA indicator
     *
     * @param input Array of values to process
     * @return an array of results
     */
    public static Float[] run(Float[] input, EMAIndicatorOptions options) {
        EMAIndicatorInput inputData = new EMAIndicatorInput(input);
        EMAIndicator emaIndicator = new EMAIndicator(options);
        return ((EMAIndicatorOutput) emaIndicator.run(inputData)).getEma();
    }

    @Override
    public <T extends IndicatorInput> IndicatorOutput run(T input) {
        if (!(input instanceof EMAIndicatorInput)) {
            throw new IllegalArgumentException("input must be an EMA Indicator Input");
        }
        return processEMAIndicator((EMAIndicatorInput) input);
    }

    @Override
    public <T extends BasicChart> IndicatorOutput run(T chart) {
        Float[] reals = FloatUtils.boxedArrayOf(chart.getDataFrom(getOptions().getTarget()));
        EMAIndicatorInput input = new EMAIndicatorInput(reals);
        return run(input);
    }

    @Override
    public AnalysisResult analyze(IndicatorOutput output) {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Process an EMA indicator
     *
     * @param input EMA indicator input
     * @return an EMA Indicator output
     */
    EMAIndicatorOutput processEMAIndicator(EMAIndicatorInput input) {
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
        float divisor = (float) (getOptions().getPeriod() + 1);
        float dividend = getOptions().getSmothering();
        return dividend / divisor;
    }

}
