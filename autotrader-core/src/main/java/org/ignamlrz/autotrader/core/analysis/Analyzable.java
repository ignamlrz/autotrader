package org.ignamlrz.autotrader.core.analysis;

import org.ignamlrz.autotrader.core.model.market.BasicChart;

public interface Analyzable {
    <T extends BasicChart> Result[] analyze(T chart);
}
