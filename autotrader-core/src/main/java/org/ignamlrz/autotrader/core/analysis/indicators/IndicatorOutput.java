package org.ignamlrz.autotrader.core.analysis.indicators;

import java.util.List;
import java.util.Map;

/**
 * Interface of Indicator Output
 */
public interface IndicatorOutput {

    /**
     * Method for get indicator output data as a map
     *
     * @return map of indicator input data
     */
    Map<String, List<? extends Number>> toMap();
}
