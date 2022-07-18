package io.github.ignamlrz.autotrader.core.repository.candlestick;

import io.github.ignamlrz.autotrader.core.collection.LinkedUnrepeatableEnumMap;
import io.github.ignamlrz.autotrader.core.repository.general.Series;

import java.util.ArrayList;
import java.util.List;

public class Candlestick extends Series {

    // ========================================================
    // = ENUMS
    // ========================================================

    public enum TypeData {
        OPEN, HIGH, LOW, CLOSE, VOLUME, TRADES, TAKER_BUY_VOLUME
    }

    // ========================================================
    // = STATIC FIELDS
    // ========================================================

    //TODO Esto es una mezcla entre el TimeSeries y el Candlestick. Realmente sería:
    //  - Candlestick extiende de Series (Contiene la logica para extraer cualquier tipo de datos)
    //  - TimeSeries es una clase que contiene un map<Long, ? extent Series> donde Long corresponde a un timestamp
    //    y su valor se corresponde a una serie (contiene toda la logica para añadir en principio/medio/final una nueva serie)
    //  - CandlestickSeries contiene un TimeSeries de Candlestic
    static LinkedUnrepeatableEnumMap<TypeData> BINANCE_DATA_SORTING = new LinkedUnrepeatableEnumMap<>(new TypeData[]{
            TypeData.OPEN,
            TypeData.HIGH,
            TypeData.LOW,
            TypeData.CLOSE
    });

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final Number[] data;
    private final LinkedUnrepeatableEnumMap<TypeData> dataType;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    public Candlestick(Number[] data, LinkedUnrepeatableEnumMap<TypeData> dataType) {
        this.data = data;
        this.dataType = dataType;
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
        return (Float) dataOf(TypeData.OPEN);
    }

    /**
     * Get high price
     *
     * @return high price
     */
    public Float getHigh() {
        return (Float) dataOf(TypeData.HIGH);
    }

    /**
     * Get low price
     *
     * @return low price
     */
    public Float getLow() {
        return (Float) dataOf(TypeData.LOW);
    }

    /**
     * Get close price
     *
     * @return close price
     */
    public Float getClose() {
        return (Float) dataOf(TypeData.CLOSE);
    }

    /**
     * Get total volume
     *
     * @return total volume
     */
    public Float getVolume() {
        return (Float) dataOf(TypeData.VOLUME);
    }

    /**
     * Get num trades
     *
     * @return num trades
     */
    public Integer getTrades() {
        return (Integer) dataOf(TypeData.TRADES);
    }

    /**
     * Get taker buy volume
     *
     * @return taker buy volume
     */
    public Float getTakerBuyVolume() {
        return (Float) dataOf(TypeData.TAKER_BUY_VOLUME);
    }

    /**
     * Get taker sell volume
     *
     * @return taker sell volume
     */
    public Float getTakerSellVolume() {
        Float volume = getVolume();
        Float takerBuyVolume = getTakerBuyVolume();
        if (volume == null || takerBuyVolume == null) return null;
        return volume - takerBuyVolume;
    }

    // ========================================================
    // = METHOD
    // ========================================================

    /**
     * Get candlestick data type index
     *
     * @param key type of data
     * @return candlestick data type index. Null if not exists that data type
     */
    public Integer indexOf(TypeData key) {
        return this.dataType.get(key);
    }

    /**
     * Get candlestick data
     *
     * @param key type of data
     * @return candlestick data
     */
    public Number dataOf(TypeData key) {
        Integer index = this.dataType.get(key);
        if (index == null) return null;
        return this.data[index];
    }

    // ========================================================
    // = OVERRIDE METHOD
    // ========================================================

    @Override
    public String toString() {
        return "Candlestick" + generateStringRepresentation();
    }

    // ========================================================
    // = PRIVATE METHOD
    // ========================================================

    private String generateStringRepresentation() {
        String format = "{%s}";
        List<String> listData = new ArrayList<>();
        for (TypeData type : new ArrayList<>(dataType.getSortedEnums())) {
            int index = dataType.get(type);
            if(index >= data.length) listData.add(type.name() + "=null");
            else listData.add(type.name() + "=" + data[dataType.get(type)]);
        }
        return String.format(format, String.join(",", listData));
    }
}
