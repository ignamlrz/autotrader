package org.ignamlrz.autotrader.core.analysis.indicator.macd;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Moving Average Convergence/Divergence indicator input
 */
public final class MACDInput implements IndicatorInput {

    public enum Type {
        REAL
    }

    /**
     * Array of numbers
     */
    @Getter
    private final List<? extends Number> real;

    /**
     * Constructor of the {@link MACDInput}
     *
     * @param real an array of numbers
     */
    public MACDInput(List<? extends Number> real) {
        this.real = real;
    }


    @Override
    public Map<String, List<? extends Number>> toMap() {
        Map<String, List<? extends Number>> map = new HashMap<>();
        map.put(Type.REAL.name(), real);
        return map;
    }
}
