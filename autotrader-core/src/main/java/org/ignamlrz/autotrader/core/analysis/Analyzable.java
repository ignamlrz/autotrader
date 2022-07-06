package org.ignamlrz.autotrader.core.analysis;

import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

public interface Analyzable {
    AnalysisResult analyze(IndicatorOutput output);
}
