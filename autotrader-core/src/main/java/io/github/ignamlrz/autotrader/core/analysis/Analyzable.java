package io.github.ignamlrz.autotrader.core.analysis;

import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

public interface Analyzable {
    AnalysisResult analyze(IndicatorOutput output);
}
