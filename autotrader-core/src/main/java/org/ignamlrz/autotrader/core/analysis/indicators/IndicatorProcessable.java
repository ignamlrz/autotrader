package org.ignamlrz.autotrader.core.analysis.indicators;

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
    IndicatorOutput run(IndicatorInput input);
}
