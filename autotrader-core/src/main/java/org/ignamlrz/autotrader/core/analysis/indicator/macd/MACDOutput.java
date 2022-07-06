package org.ignamlrz.autotrader.core.analysis.indicator.macd;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Moving Average Convergence/Divergence indicator output
 */
public final class MACDOutput implements IndicatorOutput {

    public enum Type {
        MACD, MACD_SIGNAL, MACD_HISTOGRAM
    }

    /**
     * Array of data
     */
    @Getter
    private final List<MACDOutputData> data;

    /**
     * Constructor of the {@link MACDOutput}
     *
     * @param data an array of numbers
     */
    public MACDOutput(List<MACDOutputData> data) {
        this.data = data;
    }

    @Override
    public Map<String, List<? extends Number>> toMap() {
        Map<String, List<? extends Number>> map = new HashMap<>();
        map.put(Type.MACD.name(), data.stream().map(MACDOutputData::getMacd).collect(Collectors.toList()));
        map.put(Type.MACD_SIGNAL.name(), data.stream().map(MACDOutputData::getMacdSignal).collect(Collectors.toList()));
        map.put(Type.MACD_HISTOGRAM.name(), data.stream().map(MACDOutputData::getMacdHistogram).collect(Collectors.toList()));
        return map;
    }
}
