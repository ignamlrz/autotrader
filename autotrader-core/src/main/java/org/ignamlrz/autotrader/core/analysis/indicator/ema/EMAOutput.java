package org.ignamlrz.autotrader.core.analysis.indicator.ema;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Exponential Moving Average indicator output
 */
public final class EMAOutput implements IndicatorOutput {

    public enum Type {
        EMA
    }

    /**
     * Array of data
     */
    @Getter
    private final List<EMAOutputData> data;

    /**
     * Constructor of the {@link EMAOutput}
     *
     * @param data an array of numbers
     */
    public EMAOutput(List<EMAOutputData> data) {
        this.data = data;
    }

    @Override
    public Map<String, List<? extends Number>> toMap() {
        Map<String, List<? extends Number>> map = new HashMap<>();
        map.put(Type.EMA.name(), data.stream().map(EMAOutputData::getEma).collect(Collectors.toList()));
        return map;
    }
}
