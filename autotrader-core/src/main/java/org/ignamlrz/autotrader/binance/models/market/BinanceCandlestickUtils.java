package org.ignamrlz.autotrader.binance.models.market;

import org.ignamlrz.autotrader.core.time.Timeframe;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class BinanceCandlestickUtils {

    @Valid
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
        return BinanceCandlestick.builder()
                .timeframe(Timeframe.builder()
                        .open((long) array[OPEN_TIME_INDEX])
                        .close((long) array[CLOSE_TIME_INDEX])
                        .build())
                .open((float) array[OPEN_PRICE_INDEX])
                .high((float) array[HIGH_PRICE_INDEX])
                .low((float) array[LOW_PRICE_INDEX])
                .close((float) array[CLOSE_PRICE_INDEX])
                .volume((float) array[VOLUME_INDEX])
                .quoteAssetVolume((float) array[QUOTE_ASSET_VOLUME_INDEX])
                .trades((int) array[NUMBER_OF_TRADES_INDEX])
                .takerBuyBaseAssetVolume((float) array[TAKER_BUY_BASE_ASSET_VOLUME_INDEX])
                .takerBuyQuoteAssetVolume((float) array[TAKER_BUY_QUOTE_ASSET_VOLUME_INDEX])
                .build();
    }
}
