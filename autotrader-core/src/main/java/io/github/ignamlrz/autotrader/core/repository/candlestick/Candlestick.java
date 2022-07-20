package io.github.ignamlrz.autotrader.core.repository.candlestick;

import io.github.ignamlrz.autotrader.core.repository.symbol.Symbol;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;
import io.github.ignamlrz.autotrader.core.utilities.time.Timeframe;
import io.github.ignamlrz.autotrader.core.utilities.time.TimeframeUtils;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * Model of a basic candlestick
 */
public class Candlestick {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Symbol which belong this candlestick
     */
    @NotNull
    @Indexed
    @DBRef
    private final Symbol symbol;

    /**
     * Interval which belong this candlestick
     */
    @NotNull
    @Indexed
    private final Interval interval;

    /**
     * Timestamp which belong this candlestick
     */
    @NotNull
    @Indexed
    private final Long timestamp;

    /**
     * Opening price
     */
    @NotNull
    @Min(0)
    private final Float open;

    /**
     * Highest price
     */
    @NotNull
    @Min(0)
    private final Float high;

    /**
     * Lowest price
     */
    @NotNull
    @Min(0)
    private final Float low;

    /**
     * Closing price
     */
    @NotNull
    @Min(0)
    private final Float close;

    /**
     * Volume
     */
    @NotNull
    @Min(0)
    private final Float volume;

    /**
     * Volume
     */
    @Min(0)
    private final Integer trades;

    /**
     * Volume
     */
    @Min(0)
    private final Float takerBuyVolume;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    public Candlestick(
            @NotNull Symbol symbol,
            @NotNull Interval interval,
            @NotNull Long timestamp,
            @NotNull Float open,
            @NotNull Float high,
            @NotNull Float low,
            @NotNull Float close,
            @NotNull Float volume,
            @Nullable Integer trades,
            @Nullable Float takerBuyVolume
    ) {
        this.symbol = symbol;
        this.interval = interval;
        this.timestamp = timestamp;
        this.open = Optional.of(open).get();
        this.high = Optional.of(high).get();
        this.low = Optional.of(low).get();
        this.close = Optional.of(close).get();
        this.volume = Optional.of(volume).get();
        this.trades = trades;
        this.takerBuyVolume = takerBuyVolume;
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter symbol which belong this candlestick
     *
     * @return associated symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Getter interval which belong this candlestick
     *
     * @return associated interval
     */
    public Interval getInterval() {
        return interval;
    }

    /**
     * Getter opening timestamp
     *
     * @return opening timestamp
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Getter timeframe
     *
     * @return timeframe
     */
    public Timeframe getTimeframe() {
        return TimeframeUtils.of(timestamp, interval);
    }

    /**
     * Getter opening price
     *
     * @return opening price
     */
    public float getOpen() {
        return open;
    }

    /**
     * Getter of the highest price
     *
     * @return highest price
     */
    public float getHigh() {
        return high;
    }

    /**
     * Getter of the lowest price
     *
     * @return lowest price
     */
    public float getLow() {
        return low;
    }

    /**
     * Getter closing price
     *
     * @return closing price
     */
    public float getClose() {
        return close;
    }

    /**
     * Getter total volume
     *
     * @return total volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Getter number of trades
     *
     * @return number of trades
     */
    public @Nullable Integer getTrades() {
        return trades;
    }

    /**
     * Getter taker buy volume
     *
     * @return taker buy volume
     */
    public @Nullable Float getTakerBuyVolume() {
        return takerBuyVolume;
    }

    /**
     * Getter taker sell volume
     *
     * @return taker sell volume
     */
    public @Nullable Float getTakerSellVolume() {
        if (this.takerBuyVolume == null) return null;
        return this.volume - this.takerBuyVolume;
    }

    /**
     * Getter quote volume
     *
     * @return quote volume
     */
    public @Nullable Float getQuoteVolume() {
        return volume / avgPrice();
    }

    /**
     * Getter taker buy quote volume
     *
     * @return taker buy quote volume
     */
    public @Nullable Float getTakerBuyQuoteVolume() {
        if (this.takerBuyVolume == null) return null;
        return this.takerBuyVolume / avgPrice();
    }

    /**
     * Getter taker sell quote volume
     *
     * @return taker sell quote volume
     */
    public @Nullable Float getTakerSellQuoteVolume() {
        Float takerSellVolume = this.getTakerSellVolume();
        if (takerSellVolume == null) return null;
        return takerSellVolume / avgPrice();
    }

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Method for calculate average price of this candlestick. Do the operation (high + low + open * 3 + close * 3) / 8
     *
     * @return average price
     */
    public float avgPrice() {
        return (this.high + this.low + this.open * 3f + this.close * 3f) * 0.125f;
    }

    /**
     * Method for calculate taker buy factor
     *
     * @return factor between 0 - 1 of taker buy
     */
    public @Nullable Float takerBuyFactor() {
        if (this.takerBuyVolume == null) return null;
        return this.takerBuyVolume / this.volume;
    }

    /**
     * Method for calculate taker sell factor
     *
     * @return factor between 0 - 1 of taker sell
     */
    public @Nullable Float takerSellFactor() {
        Float factorTakerBuy = takerBuyFactor();
        if (factorTakerBuy == null) return null;
        return 1f - factorTakerBuy;
    }
}
