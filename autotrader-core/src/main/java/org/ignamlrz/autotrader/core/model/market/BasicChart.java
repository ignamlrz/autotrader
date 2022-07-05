package org.ignamlrz.autotrader.core.model.market;

import org.ignamlrz.autotrader.core.analysis.Analyzable;
import org.ignamlrz.autotrader.core.analysis.Result;
import org.ignamlrz.autotrader.core.time.Interval;

public class BasicChart implements Analyzable {
    BasicCandlestick[] basicCandlesticks;
    Interval interval;

    @Override
    public <T extends BasicChart> Result[] analyze(T chart) {
        throw new RuntimeException("Not implemented");
    }
}
