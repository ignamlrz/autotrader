package org.ignamlrz.autotrader.binance.models.market;

import org.ignamlrz.autotrader.core.utilities.time.Timeframe;

import javax.validation.constraints.Size;
import java.security.InvalidParameterException;

public class BinanceCandlestickUtils {

    // ========================================================
    // = STATIC METHODS
    // ========================================================

    /**
     * Converse an array of objects to a {@link BinanceCandlestick}
     *
     * @param array Array of objects
     * @return a {@link BinanceCandlestick}
     * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">binance.io</a>
     */
    public static BinanceCandlestick of(@Size(min = 11, max = 12) Object[] array) {
        if(array.length < 11 || 12 < array.length) throw new InvalidParameterException("array must be of size 11 or 12");
        // Specify index of each element
        final int OPEN_TIME_INDEX = 0;
        final int OPEN_PRICE_INDEX = 1;
        final int HIGH_PRICE_INDEX = 2;
        final int LOW_PRICE_INDEX = 3;
        final int CLOSE_PRICE_INDEX = 4;
        final int VOLUME_INDEX = 5;
        final int CLOSE_TIME_INDEX = 6;
        final int QUOTE_ASSET_VOLUME_INDEX = 7;
        final int NUMBER_OF_TRADES_INDEX = 8;
        final int TAKER_BUY_BASE_ASSET_VOLUME_INDEX = 9;
        final int TAKER_BUY_QUOTE_ASSET_VOLUME_INDEX = 10;

        // Create a BinanceCandlestick
        Number[] arrayNumbers = new Number[array.length];
        for(int i=0; i< array.length; i++) {
            if(array[i] instanceof Integer) arrayNumbers[i] = (Integer) array[i];
            else if(array[i] instanceof Long) arrayNumbers[i] = (Long) array[i];
            else arrayNumbers[i] = Float.valueOf(array[i].toString());
        }
        return new BinanceCandlestick(
                (Float) arrayNumbers[OPEN_PRICE_INDEX],
                (Float) arrayNumbers[HIGH_PRICE_INDEX],
                (Float) arrayNumbers[LOW_PRICE_INDEX],
                (Float) arrayNumbers[CLOSE_PRICE_INDEX],
                (Float) arrayNumbers[VOLUME_INDEX],
                new Timeframe((Long) arrayNumbers[OPEN_TIME_INDEX], (Long) arrayNumbers[CLOSE_TIME_INDEX]),
                (Integer) arrayNumbers[NUMBER_OF_TRADES_INDEX],
                (Float) arrayNumbers[QUOTE_ASSET_VOLUME_INDEX],
                (Float) arrayNumbers[TAKER_BUY_BASE_ASSET_VOLUME_INDEX],
                (Float) arrayNumbers[TAKER_BUY_QUOTE_ASSET_VOLUME_INDEX]
        );
    }
}
