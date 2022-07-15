package io.github.ignamlrz.autotrader.core.repository.candlestick;

import io.github.ignamlrz.autotrader.core.collection.SortedEnumMap;
import io.github.ignamlrz.autotrader.core.repository.general.Series;

import java.util.Map;

public abstract class Candlestick extends Series {

    // ========================================================
    // = ENUMS
    // ========================================================

    public enum TypeData {
        OPEN, HIGH, LOW, CLOSE, VOLUME, TRADES, TAKER_BUY_VOLUME, TAKER_BUY_SELL
    }

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    // TODO Esto es una mezcla entre el TimeSeries y el Candlestick. Realmente sería:
    //  - TimeSeries es una clase que contiene un map<Long, ? extent Series> donde Long corresponde a un timestamp
    //    y su valor se corresponde a una serie
    //  - CandlestickSeries corresponde con la herencia de TimeSeries, pero en este caso contendría un
    //  mapa<Long, Candlestick>.
    static SortedEnumMap<TypeData> BINANCE_DATA_SORTING = new SortedEnumMap<>(new TypeData[]{
            TypeData.OPEN,
            TypeData.HIGH,
            TypeData.LOW,
            TypeData.CLOSE
    });


    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final Map<Long, Float[]> data;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    public Candlestick(float[] data) {
        this.data = data;
    }

    // ========================================================
    // = GETTER/SETTER
    // ========================================================

    /**
     * Get open price
     *
     * @return open price
     */
    public Float getOpen() {
        if(!existsDataType(TypeData.OPEN)) return null;
        return data[getDataTypeSorting().];
    }

    /**
     * Get high price
     *
     * @return high price
     */
    public float getHigh() {
        return high;
    }

    /**
     * Get low price
     *
     * @return low price
     */
    public float getLow() {
        return low;
    }

    /**
     * Get close price
     *
     * @return close price
     */
    public float getClose() {
        return close;
    }

    /**
     * Get total volume
     *
     * @return total volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Get taker buy volume
     *
     * @return taker buy volume
     */
    public Float getTakerBuyVolume() {
        if (takerBuyVolume < 0) return null;
        return takerBuyVolume;
    }

    /**
     * Get taker sell volume
     *
     * @return taker sell volume
     */
    public Float getTakerSellVolume() {
        if (takerBuyVolume < 0) return null;
        return volume - takerBuyVolume;
    }

    /**
     * Get num trades
     *
     * @return num trades
     */
    public Integer getTrades() {
        if (trades < 0) return null;
        return trades;
    }

    // ========================================================
    // = ABSTRACT METHOD
    // ========================================================

    /**
     * Get candlestick data type sorting
     *
     * @return candlestick data type sorting
     */
    public abstract BiMap<TypeData> getDataTypeSorting();

    // ========================================================
    // = METHOD
    // ========================================================

    /**
     * Method for check if exists data type
     *
     * @param type Method for check if exists data type
     * @return true if exists, false otherwise
     */
    public final boolean existsDataType(TypeData type) {
        return getDataTypeSorting().
    }
    // ========================================================
    // = OVERRIDE METHOD
    // ========================================================

    @Override
    public String toString() {
        return "Candlestick{" +
                "timestamp=" + timestamp +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                '}';
    }

    // ========================================================
    // = PRIVATE METHOD
    // ========================================================

    private boolean getData(TypeData type) {
        getDataTypeSorting().contains()
    }
}
