package org.ignamlrz.autotrader.binance.models.market;

import org.ignamlrz.autotrader.core.utilities.time.Timeframe;

import javax.validation.constraints.Size;

public class BinanceCandlestickUtils {

    /**
     * Converse an array of objects to a {@link BinanceCandlestick}
     *
     * @param array Array of objects
     * @return a {@link BinanceCandlestick}
     * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-data">binance.io</a>
     */
    public static BinanceCandlestick of(@Size(min = 11, max = 12) Object[] array) {
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
        return new BinanceCandlestick(
                Float.valueOf((String) array[OPEN_PRICE_INDEX]),
                Float.valueOf((String) array[HIGH_PRICE_INDEX]),
                Float.valueOf((String) array[LOW_PRICE_INDEX]),
                Float.valueOf((String) array[CLOSE_PRICE_INDEX]),
                Float.valueOf((String) array[VOLUME_INDEX]),
                new Timeframe((Long) array[OPEN_TIME_INDEX], (Long) array[CLOSE_TIME_INDEX]),
                (Integer) array[NUMBER_OF_TRADES_INDEX],
                Float.valueOf((String) array[QUOTE_ASSET_VOLUME_INDEX]),
                Float.valueOf((String) array[TAKER_BUY_BASE_ASSET_VOLUME_INDEX]),
                Float.valueOf((String) array[TAKER_BUY_QUOTE_ASSET_VOLUME_INDEX])
        );
    }
}
