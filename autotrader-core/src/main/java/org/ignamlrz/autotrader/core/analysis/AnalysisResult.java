package org.ignamlrz.autotrader.core.analysis;

import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;

public class AnalysisResult {

    public enum Action {
        BUY, SELL
    }


    public enum Reliability {
        VERY_LOW, LOW, MEDIUM, HIGH, VERY_HIGH
    }

    private final Action action;

    private final Reliability result;

    private final Indicator indicator;

    public AnalysisResult(Action action, Reliability result, Indicator indicator) {
        this.action = action;
        this.result = result;
        this.indicator = indicator;
    }
}
