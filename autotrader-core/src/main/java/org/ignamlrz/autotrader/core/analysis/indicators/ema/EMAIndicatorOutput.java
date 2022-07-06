package org.ignamlrz.autotrader.core.analysis.indicators.ema;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

import java.util.HashMap;
import java.util.Map;

/**
 * Exponential Moving Average indicator output
 */
public final class EMAIndicatorOutput implements IndicatorOutput {

    public enum Type {
        EMA
    }

    /**
     * Array of data
     */
    @Getter
    private final float[] ema;

    /**
     * Constructor of the {@link EMAIndicatorOutput}
     *
     * @param ema an array of numbers
     */
    public EMAIndicatorOutput(float[] ema) {
        this.ema = ema;
    }

    @Override
    public Map<String, float[]> toMap() {
        Map<String, float[]> map = new HashMap<>();
        map.put(Type.EMA.name(), ema);
        return map;
    }
}
