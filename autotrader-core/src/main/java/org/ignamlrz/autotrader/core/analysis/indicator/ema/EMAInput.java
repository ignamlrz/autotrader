package org.ignamlrz.autotrader.core.analysis.indicator.ema;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exponential Moving Average indicator input
 */
public final class EMAInput implements IndicatorInput {

    public enum Type {
        REAL
    }

    /**
     * Array of numbers
     */
    @Getter
    private final List<? extends Number> real;

    /**
     * Constructor of the {@link EMAInput}
     *
     * @param real an array of numbers
     */
    public EMAInput(List<? extends Number> real) {
        this.real = real;
    }


    @Override
    public Map<String, List<? extends Number>> toMap() {
        Map<String, List<? extends Number>> map = new HashMap<>();
        map.put(Type.REAL.name(), real);
        return map;
    }
}
