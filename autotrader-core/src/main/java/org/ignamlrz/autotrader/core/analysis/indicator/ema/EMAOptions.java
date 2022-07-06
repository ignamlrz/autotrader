package org.ignamlrz.autotrader.core.analysis.indicator.ema;

import lombok.Builder;
import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.indicators.Indicator;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorOptions;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Exponential Moving Average indicator options
 */
public final class EMAOptions implements IndicatorOptions {

    public enum Type {
        PERIOD, TARGET
    }

    /**
     * Period
     */
    @Getter
    private final int period;


    /**
     * Indicator target
     */
    @Getter
    private final Indicator.Target target;

    @Builder
    private EMAOptions(int period, @Nullable Indicator.Target target) {
        if(period < 5) {
            throw new IllegalArgumentException("Short period can not be lower than 0");
        }
        this.period = period;
        this.target = IndicatorUtils.ofNullable(target);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(Type.PERIOD.name(), period);
        map.put(Type.TARGET.name(), target);
        return map;
    }
}
