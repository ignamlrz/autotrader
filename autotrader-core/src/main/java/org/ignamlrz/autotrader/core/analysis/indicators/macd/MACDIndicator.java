package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import org.ignamlrz.autotrader.core.analysis.AnalysisResult;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicator;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorInput;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOutput;
import org.ignamlrz.autotrader.core.model.market.BasicChart;
import org.ignamlrz.autotrader.core.utilities.FloatUtils;

/**
 * Moving Average Convergence/Divergence indicator
 */
public final class MACDIndicator extends Indicator {

    static final String IDENTIFIER = "macd";
    static final String NAME = "Moving Average Convergence/Divergence";
    static final Indicator.Type TYPE = Type.INDICATOR;

    /**
     * Constructor of the {@link MACDIndicator}
     *
     * @param options to use on this indicator
     * @see <a href="https://www.investopedia.com/terms/m/macd.asp">investopedia.com</a>
     * @see <a href="https://tulipindicators.org/macd">tulipindicators.org</a>
     */
    public MACDIndicator(MACDIndicatorOptions options) {
        super(IDENTIFIER, NAME, TYPE, options);
    }

    /**
     * Method for get MACD options
     *
     * @return MACD options
     */
    public MACDIndicatorOptions getMACDOptions() {
        return (MACDIndicatorOptions) this.options;
    }

    @Override
    public IndicatorOutput run(IndicatorInput indicatorInput) {
        MACDIndicatorInput macdInput = (MACDIndicatorInput) indicatorInput;
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T extends BasicChart> IndicatorOutput run(T chart) {
        MACDIndicatorOptions options = (MACDIndicatorOptions) this.options;
        float[] realList = FloatUtils.arrayOf(chart.getDataFrom(options.getTarget()));
        MACDIndicatorInput input = new MACDIndicatorInput(realList);
        return run(input);
    }

    @Override
    public AnalysisResult analyze(IndicatorOutput output) {
        throw new RuntimeException("Not implemented yet");
    }

    /**
     * Process an MACD indicator
     *
     * @param input MACD indicator input
     * @return an MACD Indicator output
     */
    MACDIndicatorOutput processMACDIndicator(MACDIndicatorInput input) {
        int length = input.getReals().length;
        float[] macd = new float[length];
        float[] histogram = new float[length];

        // ...calculate short and long EMA
        float[] outShortPeriod = processEMAIndicator(input.getReals(), getMACDOptions().getShortPeriod());
        float[] outLongPeriod = processEMAIndicator(input.getReals(), getMACDOptions().getLongPeriod());

        // ...calculate macd
        for (int i = 0; i < length; i++) {
            macd[i] = outShortPeriod[i] - outLongPeriod[i];
        }

        // ...calculate signal
        float[] signal = processEMAIndicator(macd, getMACDOptions().getSignalPeriod());

        // ...calculate histogram
        for (int i = 0; i < length; i++) {
            histogram[i] = macd[i] - signal[i];
        }

        return new MACDIndicatorOutput(macd, signal, histogram);
    }

    /**
     * Process an EMA indicator
     *
     * @param input  Array of values to process
     * @param period Period to process
     * @return an array of results
     */
    float[] processEMAIndicator(float[] input, int period) {
        EMAIndicatorInput emaInput = new EMAIndicatorInput(input);
        EMAIndicatorOptions emaOptions = new EMAIndicatorOptions(period, getMACDOptions().getTarget());
        EMAIndicator emaIndicator = new EMAIndicator(emaOptions);
        return ((EMAIndicatorOutput) emaIndicator.run(emaInput)).getEma();
    }
}
