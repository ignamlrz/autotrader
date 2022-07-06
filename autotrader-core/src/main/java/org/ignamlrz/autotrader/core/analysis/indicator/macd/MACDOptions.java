package org.ignamlrz.autotrader.core.analysis.indicator.macd;

import lombok.Builder;
import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOptions;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Moving Average Convergence/Divergence indicator options
 */
public final class MACDOptions implements IndicatorOptions {

    public enum Type {
        SHORT_PERIOD, LONG_PERIOD, SIGNAL_PERIOD, TARGET
    }

    /**
     * Short period
     */
    @Getter
    private final int shortPeriod;

    /**
     * Long period
     */
    @Getter
    private final int longPeriod;

    /**
     * Signal period
     */
    @Getter
    private final int signalPeriod;

    /**
     * Indicator target
     */
    @Getter
    private final Indicator.Target target;

    @Builder
    private MACDOptions(int shortPeriod, int longPeriod, int signalPeriod, @Nullable Indicator.Target target) {
        if(shortPeriod < 5) {
            throw new IllegalArgumentException("Short period can not be lower than 0");
        }
        if(longPeriod <= shortPeriod) {
            throw new IllegalArgumentException("Long period can not be lower or equal than short period");
        }
        if(signalPeriod < 5) {
            throw new IllegalArgumentException("Short period can not be lower than 0");
        }
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
        this.signalPeriod = signalPeriod;
        this.target = IndicatorUtils.ofNullable(target);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Type.SHORT_PERIOD.name(), shortPeriod);
        map.put(Type.LONG_PERIOD.name(), longPeriod);
        map.put(Type.SIGNAL_PERIOD.name(), signalPeriod);
        map.put(Type.TARGET.name(), target);
        return map;
    }
}
