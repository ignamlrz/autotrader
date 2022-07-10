package org.ignamlrz.autotrader.core.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.ignamlrz.autotrader.binance.models.market.BinanceChart;
import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorTarget;
import org.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;
import org.ignamlrz.autotrader.core.utilities.time.Interval;
import org.ignamlrz.autotrader.core.utilities.time.Timeframe;

import javax.naming.NameNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstract chart
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BinanceChart.class, name = ChartType.BINANCE),
})
public abstract class Chart {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    protected final Interval interval;

    // ========================================================
    // = CONSTRUCTOR
    // ========================================================

    protected Chart(@JsonProperty("interval") @NotNull Interval interval) {
        this.interval = Optional.of(interval).get();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of a candlestick list
     *
     * @return a candlestick list
     */
    public abstract List<? extends Candlestick> getCandlesticks();

    /**
     * Getter of interval
     *
     * @return interval
     */
    public final Interval getInterval() {
        return interval;
    }

    // ========================================================
    // = ABSTRACT METHODS
    // ========================================================

    /**
     * Find data given a target stored on candlestick collection
     *
     * @return a list of numbers
     */
    public abstract List<Float> dataByTarget(@NotNull IndicatorTarget target) throws NameNotFoundException;

    /**
     * Return candlestick series to a map with opening time as key
     *
     * @return a map of candlesticks
     */
    public abstract Map<Long, ? extends Candlestick> toMap();

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Getter of closing timestamp list
     *
     * @return a closing timestamp list
     */
    public final List<Long> closeTimestamp() {
        return timeframes().stream().map(Timeframe::getClose).collect(Collectors.toList());
    }

    /**
     * Method for get next {@link Candlestick}
     *
     * @param timestamp Current timestamp
     * @return next {@link Candlestick}
     */
    public final Candlestick next(long timestamp) {
        return toMap().get(this.interval.next(timestamp));
    }

    /**
     * Getter of opening timestamp list
     *
     * @return an opening timestamp list
     */
    public final List<Long> openTimestamps() {
        return timeframes().stream().map(Timeframe::getOpen).collect(Collectors.toList());
    }

    /**
     * Method for get previous {@link Candlestick}
     *
     * @param timestamp Current timestamp
     * @return previous {@link Candlestick}
     */
    public final Candlestick previous(long timestamp) {
        return toMap().get(this.interval.previous(timestamp));
    }

    /**
     * Getter of timeframe list
     *
     * @return a timeframe list
     */
    public final List<Timeframe> timeframes() {
        return getCandlesticks().stream().map(Candlestick::getTimeframe).collect(Collectors.toList());
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public final String toString() {
        return ConversionUtils.toJson(this);
    }
}
