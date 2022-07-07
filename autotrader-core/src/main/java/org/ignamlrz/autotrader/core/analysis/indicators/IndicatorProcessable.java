package org.ignamlrz.autotrader.core.analysis.indicators;

import org.ignamlrz.autotrader.core.model.market.BasicChart;

/**
 * Interface that allow to be processable any indicator
 */
public interface IndicatorProcessable {

    /**
     * Method which add the ability to run an indicator
     *
     * @param input Indicator input data
     * @return Indicator output data
     */
    <T extends IndicatorInput> IndicatorOutput run(T input);

    /**
     * Method which add the ability to run an indicator
     *
     * @param chart Chart as input data
     * @return Indicator output data
     */
    <T extends BasicChart> IndicatorOutput run(T chart);
}
