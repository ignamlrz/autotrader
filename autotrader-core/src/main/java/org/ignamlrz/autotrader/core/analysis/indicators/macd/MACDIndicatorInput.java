package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;

import java.util.HashMap;
import java.util.Map;

/**
 * Moving Average Convergence/Divergence indicator input
 */
public final class MACDIndicatorInput implements IndicatorInput {

    public enum Type {
        REAL
    }

    /**
     * Array of numbers
     */
    @Getter
    private final float[] reals;

    /**
     * Constructor of the {@link MACDIndicatorInput}
     *
     * @param reals an array of numbers
     */
    public MACDIndicatorInput(float[] reals) {
        this.reals = reals;
    }


    @Override
    public Map<String, float[]> toMap() {
        Map<String, float[]> map = new HashMap<>();
        map.put(Type.REAL.name(), reals);
        return map;
    }
}
