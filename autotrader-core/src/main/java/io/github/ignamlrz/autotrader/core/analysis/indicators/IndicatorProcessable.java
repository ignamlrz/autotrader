package io.github.ignamlrz.autotrader.core.analysis.indicators;

import io.github.ignamlrz.autotrader.core.model.market.ChartModel;

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
    <T extends ChartModel> IndicatorOutput run(T chart);
}
