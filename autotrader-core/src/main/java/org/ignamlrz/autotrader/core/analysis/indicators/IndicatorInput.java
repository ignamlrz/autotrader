package org.ignamlrz.autotrader.core.analysis.indicators;

import java.util.List;
import java.util.Map;

/**
 * Interface of Indicator Input
 */
public interface IndicatorInput {

    /**
     * Method for get indicator input data as a Map
     *
     * @return map of indicator input data
     */
    Map<String, List<? extends Number>> toMap();
}
