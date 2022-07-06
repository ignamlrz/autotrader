package org.ignamlrz.autotrader.core.analysis.indicator.ema;

import org.ignamlrz.autotrader.core.analysis.AnalysisResult;
import org.ignamlrz.autotrader.core.analysis.indicator.macd.MACDInput;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import org.ignamlrz.autotrader.core.model.market.BasicChart;

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
     */
    public EMAIndicator(EMAOptions options) {
        super(IDENTIFIER, NAME, TYPE, options);
    }

    @Override
    public IndicatorOutput run(IndicatorInput input) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T extends BasicChart> AnalysisResult analyze(T chart) {
        EMAOptions options = (EMAOptions) this.options;
        MACDInput input = new MACDInput(chart.getData(options.getTarget()));
        IndicatorOutput output = run(input);
        throw new RuntimeException("Not implemented yet");
    }

}
