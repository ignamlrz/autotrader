package io.github.ignamlrz.autotrader.core.model.market;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.ignamlrz.autotrader.binance.models.market.BinanceChartModel;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorTarget;
import io.github.ignamlrz.autotrader.core.model.PairAsset;
import io.github.ignamlrz.autotrader.core.model.exchange.Exchange;
import io.github.ignamlrz.autotrader.core.model.exchange.ExchangeType;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;
import io.github.ignamlrz.autotrader.core.utilities.time.Timeframe;

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
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BinanceChartModel.class, name = ExchangeType.BINANCE),
})
@JsonPropertyOrder({ "id", "type", "pairAsset", "interval" })
public abstract class ChartModel extends Exchange {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    @NotNull
    protected final String id;

    @NotNull
    protected final Interval interval;

    @NotNull
    protected final PairAsset pairAsset;

    @NotNull
    protected final String type;

    // ========================================================
    // = CONSTRUCTOR
    // ========================================================

    protected ChartModel(
            @JsonProperty("pairAsset") PairAsset pairAsset,
            @JsonProperty("interval") @NotNull Interval interval
    ) {
        this.interval = Optional.of(interval).get();
        this.pairAsset = Optional.of(pairAsset).get();
        this.type = Optional.of(exchangeMetadata().identifier()).get();
        this.id = exchangeMetadata().identifier() + "/" + this.pairAsset.merge() + "/" + this.interval;
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
     * Getter of identifier
     *
     * @return identifier
     */
    @NotNull public final String getId() {
        return id;
    }

    /**
     * Getter of interval
     *
     * @return interval
     */
    @NotNull public final Interval getInterval() {
        return interval;
    }

    /**
     * Getter of pair
     *
     * @return interval
     */
    @NotNull public final PairAsset getPairAsset() {
        return pairAsset;
    }

    /**
     * Getter of type
     *
     * @return type
     */
    @NotNull public final String getType() {
        return this.type;
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
    public abstract boolean remove(long timestamp);

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
}
