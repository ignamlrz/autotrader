package org.ignamlrz.autotrader.core.analysis;

public class Result {
    public final AnalysisAction action;
    public final AnalysisResult result;

    public Result(AnalysisAction action, AnalysisResult result) {
        this.action = action;
        this.result = result;
    }
}
