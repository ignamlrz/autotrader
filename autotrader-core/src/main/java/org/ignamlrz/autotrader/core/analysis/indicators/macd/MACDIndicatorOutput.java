package org.ignamlrz.autotrader.core.analysis.indicators.macd;

import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOutput;

import java.util.HashMap;
import java.util.Map;

/**
 * Moving Average Convergence/Divergence indicator output
 */
public final class MACDIndicatorOutput implements IndicatorOutput {

    public enum Type {
        MACD, MACD_SIGNAL, MACD_HISTOGRAM
    }

    /**
     * MACD
     */
    @Getter
    private final float[] macd;

    /**
     * MACD signal
     */
    @Getter
    private final float[] macdSignal;

    /**
     * MACD histogram
     */
    @Getter
    private final float[] macdHistogram;

    /**
     * Constructor of the {@link MACDIndicatorOutput}
     *
     * @param macd MACD value array
     * @param macdSignal MACD signal array
     * @param macdHistogram MACD histogram array
     */
    public MACDIndicatorOutput(float[] macd, float[] macdSignal, float[] macdHistogram) {
        this.macd = macd;
        this.macdSignal = macdSignal;
        this.macdHistogram = macdHistogram;
    }

    @Override
    public Map<String, float[]> toMap() {
        Map<String, float[]> map = new HashMap<>();
        map.put(Type.MACD.name(), macd);
        map.put(Type.MACD_SIGNAL.name(), macdSignal);
        map.put(Type.MACD_HISTOGRAM.name(), macdHistogram);
        return map;
    }
}
