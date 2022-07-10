package org.ignamlrz.autotrader.binance.models.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorTarget;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorUtils;
import org.ignamlrz.autotrader.core.model.market.Candlestick;
import org.ignamlrz.autotrader.core.model.market.Chart;
import org.ignamlrz.autotrader.core.utilities.time.Interval;

import javax.naming.NameNotFoundException;
import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class BinanceChart extends Chart {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final Map<Long, BinanceCandlestick> candlesticks;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    @JsonCreator
    public BinanceChart(
            @JsonProperty("interval") @NotNull Interval interval,
            @JsonProperty("candlesticks") @NotNull List<BinanceCandlestick> candlesticks
    ) {
        super(interval);
        // ...save as a map
        this.candlesticks = candlesticks.stream()
                .collect(Collectors.toMap(entry -> entry.getTimeframe().getOpen(), entry -> entry));
        // ...set next value on each kline
        long[] timestamp = this.candlesticks.keySet().stream()
                .sorted()
                .flatMapToLong(LongStream::of)
                .toArray();
        for (int i = 1; i < timestamp.length; i++) {
            Candlestick next = this.candlesticks.get(timestamp[i]);
            this.candlesticks.get(timestamp[i - 1]).setNext(next);
        }
        // ...do validation
        doValidation();
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public List<? extends Candlestick> getCandlesticks() {
        return new ArrayList<>(this.candlesticks.values());
    }

    @Override
    public List<Float> dataByTarget(IndicatorTarget target) throws NameNotFoundException {
        switch (IndicatorUtils.ofNullable(target)) {
            case CLOSE:
                return candlesticks.values().stream().map(Candlestick::getClose).collect(Collectors.toList());
            case OPEN:
                return candlesticks.values().stream().map(Candlestick::getOpen).collect(Collectors.toList());
            case HIGH:
                return candlesticks.values().stream().map(Candlestick::getHigh).collect(Collectors.toList());
            case LOW:
                return candlesticks.values().stream().map(Candlestick::getLow).collect(Collectors.toList());
            case VOLUME:
                return candlesticks.values().stream().map(Candlestick::getVolume).collect(Collectors.toList());
            default:
                throw new NameNotFoundException(target + " not found");
        }
    }

    @Override
    public Map<Long, ? extends Candlestick> toMap() {
        return this.candlesticks;
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    private void doValidation() {
        long count = this.candlesticks.values().stream()
                .filter(kline -> kline.next() != null)
                .count();
        if (count == 1) throw new InvalidParameterException("Must be one candlestick with next property as null");


        this.candlesticks.values().forEach(this::validateCandlestick);
    }

    /**
     * Method for check a candlestick is serializable (close price coincide with open price)
     *
     * @param candlestick Candlestick to check
     */
    private void validateCandlestick(Candlestick candlestick) {
        // ...check candlestick with chart interval
        if (!this.interval.equals(candlestick.getTimeframe().interval()))
            throw new InvalidParameterException("candlestick interval not match with chart interval");
        // ...opening timestamp must be module 0
        if ((candlestick.getTimeframe().getOpen() % this.interval.toMillis()) == 0)
            throw new InvalidParameterException("candlesticks opening timestamp not match with openings of chart interval");
    }
}
