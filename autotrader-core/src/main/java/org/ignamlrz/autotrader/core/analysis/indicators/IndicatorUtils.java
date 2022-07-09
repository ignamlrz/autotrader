package org.ignamlrz.autotrader.core.analysis.indicators;

import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * Utility class for {@link Indicator} class
 */
public final class IndicatorUtils {


    /**
     * Method for get a default {@link IndicatorTarget} if a null target is passed as argument
     *
     * @param target to evaluate
     * @return default target, if any was passed
     */
    public static IndicatorTarget ofNullable(@Nullable IndicatorTarget target) {
        return Optional.ofNullable(target).orElse(IndicatorTarget.CLOSE);
    }
}
