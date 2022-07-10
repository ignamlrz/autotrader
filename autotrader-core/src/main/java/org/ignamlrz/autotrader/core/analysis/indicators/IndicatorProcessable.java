package org.ignamlrz.autotrader.core.analysis.indicators;

import org.ignamlrz.autotrader.core.model.market.Chart;

/**
 * Interface that allow to be processable any indicator
 */
public interface IndicatorProcessable {

    /**
     * Method for run indicator
     *
     * @param input Indicator input data to process
     * @return Indicator output data
     */
    <T extends IndicatorInput> IndicatorOutput run(T input);

    /**
     * Method for run indicator
     *
     * @param chart Chart to process
     * @return Indicator output data
     */
    <T extends Chart> IndicatorOutput run(T chart);
}
