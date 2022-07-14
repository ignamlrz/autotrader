package io.github.ignamlrz.autotrader.binance.models.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorTarget;
import io.github.ignamlrz.autotrader.core.annotations.ExchangeMetadata;
import io.github.ignamlrz.autotrader.core.model.PairAsset;
import io.github.ignamlrz.autotrader.core.model.exchange.ExchangeDataType;
import io.github.ignamlrz.autotrader.core.model.exchange.ExchangeType;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;
import io.github.ignamlrz.autotrader.core.model.market.Candlestick;
import io.github.ignamlrz.autotrader.core.model.market.ChartModel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@ExchangeMetadata(identifier = ExchangeType.BINANCE, dataType = ExchangeDataType.exchangeInfo)
@Schema(description = "Binance chart")
public class BinanceChartModel extends ChartModel {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final SortedMap<Long, BinanceCandlestick> candlesticks;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    @JsonCreator
    public BinanceChartModel(
            @JsonProperty("pairAsset") @NotNull PairAsset pairAsset,
            @JsonProperty("interval") @NotNull Interval interval,
            @JsonProperty("candlesticks") @NotNull List<BinanceCandlestick> candlesticks
    ) {
        super(pairAsset, interval);
        // ...save as a map
        this.candlesticks = new TreeMap<>();
        for (BinanceCandlestick kline : candlesticks)
            this.candlesticks.putIfAbsent(kline.getTimeframe().getOpen(), kline);

        // ...set next value on each kline
        long[] timestamp = this.candlesticks.keySet().stream()
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
    // = GETTERS
    // ========================================================

    @Override
    public List<? extends Candlestick> getCandlesticks() {
        return new ArrayList<>(this.candlesticks.values());
    }

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Array of data
     *
     * @return candlesticks as array of data
     */
    public Object[] toArray() {
        return this.candlesticks.values().stream().map(BinanceCandlestick::toArray).toArray();
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public List<Float> dataByTarget(@NotNull IndicatorTarget target) {
        switch (target) {
            case OPEN:
                return candlesticks.values().stream().map(Candlestick::getOpen).collect(Collectors.toList());
            case HIGH:
                return candlesticks.values().stream().map(Candlestick::getHigh).collect(Collectors.toList());
            case LOW:
                return candlesticks.values().stream().map(Candlestick::getLow).collect(Collectors.toList());
            case VOLUME:
                return candlesticks.values().stream().map(Candlestick::getVolume).collect(Collectors.toList());
            case CLOSE:
            default:
                return candlesticks.values().stream().map(Candlestick::getClose).collect(Collectors.toList());
        }
    }

    @Override
    public boolean remove(long timestamp) {
        // ...remove candlestick
        Candlestick deleted = this.candlesticks.remove(timestamp);
        if (deleted == null) return false;

        // ...set next candlestick on before candlestick
        Candlestick next = deleted.next();
        Candlestick before = candlestickBefore(timestamp);
        if (before != null) before.setNext(next);
        return true;
    }

    @Override
    public boolean put(Candlestick candlestick) {
        // ...first validate candlestick
        validateCandlestick(candlestick);

        // ...obtain timestamp
        long timestamp = candlestick.getTimeframe().getOpen();

        // ...check if already exists
        if (this.candlesticks.containsKey(timestamp)) {
            // ...put data and return false (no new creation)
            this.candlesticks.put(timestamp, (BinanceCandlestick) candlestick);
            return false;
        }

        if (timestamp < this.candlesticks.lastKey()) {
            // Inserted between candlestick series
            // ...find before and after timestamps
            Candlestick before = candlestickBefore(timestamp);
            Candlestick next = (before != null)
                    ? before.next()
                    : this.candlesticks.get(this.candlesticks.firstKey());

            // ...put candlestick
            this.candlesticks.put(timestamp, (BinanceCandlestick) candlestick);

            // ...set next
            if (before != null) before.setNext(candlestick);
            candlestick.setNext(next);

        } else {
            // Inserted as last element
            // ...put candlestick
            this.candlesticks.put(timestamp, (BinanceCandlestick) candlestick);

            // ...modify next of last candlestick to point on candlesticks
            Candlestick last = this.candlesticks.get(this.candlesticks.lastKey());
            last.setNext(candlestick);

        }
        return true;
    }

    @Override
    public SortedMap<Long, ? extends Candlestick> toMap() {
        return this.candlesticks;
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    /**
     * Do validation of field properties
     */
    private void doValidation() {
        long count = this.candlesticks.values().stream()
                .filter(kline -> kline.next() == null)
                .count();
        if (count != 1) throw new InvalidParameterException("Must be one candlestick with next property as null");

        this.candlesticks.values().forEach(this::validateCandlestick);
    }

    /**
     * Method for validate a candlestick with chart properties
     *
     * @param candlestick Candlestick to check
     */
    private void validateCandlestick(Candlestick candlestick) {
        // ...check candlestick with chart interval
        if (!this.interval.testMillis(candlestick.getTimeframe().diff() + 1))
            throw new InvalidParameterException("candlestick interval not match with chart interval");
        // ...opening timestamp must be module 0
        if ((candlestick.getTimeframe().getOpen() % this.interval.toMillis()) != 0)
            throw new InvalidParameterException("candlesticks timestamp not match with openings of chart interval");
    }

    /**
     * Method for find the candlestick before a timestamp
     *
     * @param timestamp Timestamp to search
     * @return candlestick before a timestamp
     */
    private @Nullable Candlestick candlestickBefore(long timestamp) {
        // ...get all timestamps
        long[] keyTimestamps = this.candlesticks.keySet().stream()
                .flatMapToLong(LongStream::of)
                .toArray();

        // ...find candlestick before this date
        Candlestick candlestick = null;
        for (long keyTimestamp : keyTimestamps) {
            if ((timestamp - keyTimestamp) < 0) return candlestick;
            candlestick = this.candlesticks.get(keyTimestamp);
        }

        return candlestick;
    }
}
