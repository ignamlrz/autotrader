package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import org.ignamlrz.autotrader.core.analysis.AnalysisResult;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicator;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicatorOptions;
import org.ignamlrz.autotrader.core.model.market.BasicChart;
import org.ignamlrz.autotrader.core.utilities.FloatUtils;

/**
 * Moving Average Convergence/Divergence indicator
 *
 * @see <a href="https://www.investopedia.com/terms/m/macd.asp">investopedia.com</a>
 * @see <a href="https://tulipindicators.org/macd">tulipindicators.org</a>
 */
public final class MACDIndicator extends Indicator {

    static final String IDENTIFIER = "macd";
    static final String NAME = "Moving Average Convergence/Divergence";
    static final Indicator.Type TYPE = Type.INDICATOR;

    /**
     * Constructor of the {@link MACDIndicator}
     *
     * @param options to use
     */
    public MACDIndicator(MACDIndicatorOptions options) {
        super(IDENTIFIER, NAME, TYPE, options);
    }

    /**
     * Method for get {@link MACDIndicator} options
     *
     * @return MACD Indicator Options
     */
    @Override
    public MACDIndicatorOptions getOptions() {
        return (MACDIndicatorOptions) this.options;
    }

    @Override
    public <T extends IndicatorInput> IndicatorOutput run(T input) {
        if (!(input instanceof MACDIndicatorInput)) {
            throw new IllegalArgumentException("input must be an MACD Indicator Input");
        }

        return processMACDIndicator((MACDIndicatorInput) input);
    }

    @Override
    public <T extends BasicChart> IndicatorOutput run(T chart) {
        Float[] reals = FloatUtils.boxedArrayOf(chart.getDataFrom(getOptions().getTarget()));
        MACDIndicatorInput input = new MACDIndicatorInput(reals);
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
        Float[] macd = new Float[length];
        Float[] histogram = new Float[length];

        // ...calculate short and long EMA
        Float[] shortPeriod = processEMAIndicator(input.getReals(), getOptions().getShortPeriod());
        Float[] longPeriod = processEMAIndicator(input.getReals(), getOptions().getLongPeriod());

        // ...calculate macd
        for (int i = 0; i < length; i++) {
            macd[i] = shortPeriod[i] - longPeriod[i];
        }

        // ...lower than longPeriod are marked as invalid
        for (int i = 0; i < getOptions().getLongPeriod() - 1; i++) {
            macd[i] = null;
        }

        // ...calculate signal
        Float[] signal = processEMAIndicator(macd, getOptions().getSignalPeriod());


        // ...calculate histogram
        for (int i = 0; i < length; i++) {
            if(signal[i] == null || macd[i] == null)    histogram[i] = null;
            else  histogram[i] = macd[i] - signal[i];
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
    Float[] processEMAIndicator(Float[] input, int period) {
        EMAIndicatorOptions options = new EMAIndicatorOptions(period, getOptions().getTarget());
        return EMAIndicator.run(input, options);
    }
}
