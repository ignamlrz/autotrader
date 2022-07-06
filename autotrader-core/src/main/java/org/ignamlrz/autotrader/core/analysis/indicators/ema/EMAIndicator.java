package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import org.ignamlrz.autotrader.core.analysis.AnalysisResult;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import org.ignamlrz.autotrader.core.analysis.indicators.macd.MACDIndicatorInput;
import org.ignamlrz.autotrader.core.model.market.BasicChart;
import org.ignamlrz.autotrader.core.utilities.FloatUtils;

/**
 * Exponential Moving Average indicator
 */
public final class EMAIndicator extends Indicator {

    static final String IDENTIFIER = "ema";
    static final String NAME = "Exponential Moving Average";
    static final Type TYPE = Type.OVERLAY;

    /**
     * Constructor of the {@link EMAIndicator}
     *
     * @param options to use on this indicator
     * @see <a href="https://www.investopedia.com/terms/e/ema.asp">investopedia.com</a>
     * @see <a href="https://tulipindicators.org/ema">tulipindicators.org</a>
     */
    public EMAIndicator(EMAIndicatorOptions options) {
        super(IDENTIFIER, NAME, TYPE, options);
    }

    @Override
    public IndicatorOutput run(IndicatorInput indicatorInput) {
        EMAIndicatorInput emaInput = (EMAIndicatorInput) indicatorInput;
        return processEMAIndicator(emaInput);
    }

    @Override
    public <T extends BasicChart> IndicatorOutput run(T chart) {
        EMAIndicatorOptions options = (EMAIndicatorOptions) this.options;
        float[] reals = FloatUtils.arrayOf(chart.getDataFrom(options.getTarget()));
        MACDIndicatorInput input = new MACDIndicatorInput(reals);
        return run(input);
    }

    @Override
    public AnalysisResult analyze(IndicatorOutput output) {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Calculate scaling factor using instantiated period
     *
     * @return scaling factor
     */
    float getScalingFactor() {
        EMAIndicatorOptions options = (EMAIndicatorOptions) this.options;
        float divisor = (float) (options.getPeriod() + 1);
        return options.getSmothering() / divisor;
    }

    /**
     * Process an EMA indicator
     *
     * @param emaInput EMA indicator input
     * @return an EMA Indicator output
     */
    EMAIndicatorOutput processEMAIndicator(EMAIndicatorInput emaInput) {
        float scalingFactor = getScalingFactor();
        float previousOutput = 0.f;

        float[] numbers = emaInput.getReals();
        float[] outputNumbers = new float[numbers.length];
        for (int i=0; i<numbers.length; i++) {
            float aux = (i == 0)
                    ? numbers[i]
                    : (1 - scalingFactor) * previousOutput + scalingFactor * numbers[i];
            outputNumbers[i] = aux;
            previousOutput = aux;
        }

        return new EMAIndicatorOutput(outputNumbers);
    }

}
