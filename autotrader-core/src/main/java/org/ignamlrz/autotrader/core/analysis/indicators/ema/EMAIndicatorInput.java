package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorInput;

import java.util.HashMap;
import java.util.Map;

/**
 * Exponential Moving Average indicator input
 */
public final class EMAIndicatorInput implements IndicatorInput {

    public enum Type {
        REAL
    }

    /**
     * Array of numbers
     */
    @Getter
    private final float[] reals;

    /**
     * Constructor of the {@link EMAIndicatorInput}
     *
     * @param reals an array of numbers
     */
    public EMAIndicatorInput(float[] reals) {
        this.reals = reals;
    }


    @Override
    public Map<String, float[]> toMap() {
        Map<String, float[]> map = new HashMap<>();
        map.put(Type.REAL.name(), reals);
        return map;
    }
}
