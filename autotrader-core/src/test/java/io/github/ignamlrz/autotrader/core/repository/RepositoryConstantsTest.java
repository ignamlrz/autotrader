package io.github.ignamlrz.autotrader.core.repository;

import io.github.ignamlrz.autotrader.core.repository.exchange.ExchangeSupplier;
import io.github.ignamlrz.autotrader.core.utilities.time.Interval;

import java.util.Date;

public class RepositoryConstantsTest {

    // ========================================================
    // = EXCHANGE CONSTANTS
    // ========================================================

    public static final ExchangeSupplier EXCHANGE_SUPPLIER = ExchangeSupplier.BINANCE;

    // ========================================================
    // = SYMBOL CONSTANTS
    // ========================================================

    public static final String SYMBOL_ASSET = "BTC";
    public static final String SYMBOL_QUOTE = "USD";

    // ========================================================
    // = KLINE (CANDLESTICK) CONSTANTS
    // ========================================================

    public static final Interval KLINE_INTERVAL = Interval.DAY_1;
    public static final long TIMESTAMP = new Date().getTime();
    public static final float OPEN = (float) (Math.random() * 100f);
    public static final float CLOSE = (float) (Math.random() * 100f);
    public static final float HIGH = (float) (Math.random() * 2f) + Math.max(OPEN, CLOSE);
    public static final float LOW = (float) (Math.min(OPEN, CLOSE) - (Math.random() * 2f));
    public static final float VOLUME = (float) (Math.random() * 10000f);
    public static final int TRADES = (int) (Math.random() * 50000);
    public static final float TAKER_BUY_VOLUME = (float) (Math.random() * VOLUME);
}
