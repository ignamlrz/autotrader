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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
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
     * Delete a {@link Candlestick}
     *
     * @param timestamp Key timestamp to delete
     * @return true if any Candlestick was deleted, false otherwise
     */
    public abstract boolean delete(long timestamp);

    /**
     * Insert a {@link Candlestick}
     *
     * @param candlestick {@link Candlestick} to insert
     * @return true if insert a new candlestick, false if modify a previous one
     */
    public abstract boolean put(Candlestick candlestick);

    /**
     * Return candlestick series to a map with opening time as key
     *
     * @return a map of candlesticks
     */
    public abstract SortedMap<Long, ? extends Candlestick> toMap();

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Retrieve closing timestamp list
     *
     * @return closing timestamp list
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
        return toMap().get(timestamp).next();
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

    /**
     * Getter of opening timestamp list
     *
     * @return an opening timestamp list
     */
    public final List<Long> timestamps() {
        return new ArrayList<>(toMap().keySet());
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public final String toString() {
        return ConversionUtils.toJson(this);
    }
}
