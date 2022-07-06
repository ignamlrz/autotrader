package org.ignamlrz.autotrader.core.model.market;

import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.ignamlrz.autotrader.core.time.Interval;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class BasicChart {
    List<BasicCandlestick> basicCandlesticks;
    Interval interval;

    public List<Float> getDataFrom(@Nullable Indicator.Target target) {
        switch (IndicatorUtils.ofNullable(target)) {
            case OPEN: return basicCandlesticks.stream().map(BasicCandlestick::getOpen).collect(Collectors.toList());
            case HIGH: return basicCandlesticks.stream().map(BasicCandlestick::getHigh).collect(Collectors.toList());
            case LOW: return basicCandlesticks.stream().map(BasicCandlestick::getLow).collect(Collectors.toList());
            case CLOSE:
            default:
                return basicCandlesticks.stream().map(BasicCandlestick::getClose).collect(Collectors.toList());
        }
    }
}
