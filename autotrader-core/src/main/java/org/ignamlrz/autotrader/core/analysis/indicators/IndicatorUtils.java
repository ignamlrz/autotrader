package org.ignamlrz.autotrader.core.analysis.indicators;

import org.springframework.lang.Nullable;

/**
 * Utility class for {@link Indicator} class
 */
public final class IndicatorUtils {


    /**
     * Method for get a default {@link Indicator.Target} if a null target is passed as argument
     *
     * @param target to evaluate
     * @return default target, if any was passed
     */
    public static Indicator.Target ofNullable(@Nullable Indicator.Target target) {
        if (target == null) return Indicator.Target.CLOSE;
        return target;
    }
}
