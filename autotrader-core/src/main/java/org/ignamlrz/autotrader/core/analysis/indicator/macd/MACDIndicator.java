package org.ignamlrz.autotrader.core.analysis.indicator.macd;

import org.ignamlrz.autotrader.core.analysis.AnalysisResult;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;
import org.ignamlrz.autotrader.core.model.market.BasicChart;

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
     */
    public MACDIndicator(MACDOptions options) {
        super(IDENTIFIER, NAME, TYPE, options);
    }

    @Override
    public IndicatorOutput run(IndicatorInput input) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public <T extends BasicChart> AnalysisResult analyze(T chart) {
        MACDOptions options = (MACDOptions) this.options;
        MACDInput input = new MACDInput(chart.getData(options.getTarget()));
        IndicatorOutput output = run(input);
        throw new RuntimeException("Not implemented yet");
    }

}
