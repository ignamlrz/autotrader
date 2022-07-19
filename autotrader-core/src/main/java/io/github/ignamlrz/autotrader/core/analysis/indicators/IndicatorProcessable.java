package io.github.ignamlrz.autotrader.core.analysis.indicators;

import io.github.ignamlrz.autotrader.core.repository.candlestick.Candlestick;

import java.util.List;

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
     * @param candlesticks to process
     * @return Indicator output data
     */
    IndicatorOutput run(List<Candlestick> candlesticks);
}
