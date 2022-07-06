package org.ignamlrz.autotrader.core.analysis.indicators;

import java.util.Map;

/**
 * Interface of Indicator Options
 */
public interface IndicatorOptions {

    /**
     * Method for get indicator options data as a map
     *
     * @return map of indicator options data
     */
    Map<String, Object> toMap();
}
