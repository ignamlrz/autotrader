package io.github.ignamlrz.autotrader.core.repository.symbol;

import io.github.ignamlrz.autotrader.core.repository.exchange.Exchange;
import io.github.ignamlrz.autotrader.core.repository.exchange.ExchangeSupplier;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class SymbolInfo {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Exchange supplier which belong this symbol
     */
    private final ExchangeSupplier exchange;

    /**
     * Base pair
     */
    private final String base;

    /**
     * Quote pair
     */
    private final String quote;

    /**
     * Is buy allowable?
     */
    private final Boolean isBuyAllowable;

    /**
     * Is sell allowable?
     */
    private final Boolean isSellAllowable;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of a {@link SymbolInfo}
     *
     * @param base            Base Asset
     * @param quote           Quote Asset
     * @param isBuyAllowable  Is buy allowable?
     * @param isSellAllowable Is sell allowable?
     */
    public SymbolInfo(Exchange exchange, String base, String quote, Boolean isBuyAllowable, Boolean isSellAllowable) {
        this.exchange = exchange.getName();
        this.base = base;
        this.quote = quote;
        this.isBuyAllowable = isBuyAllowable;
        this.isSellAllowable = isSellAllowable;
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter exchange supplier which belong this symbol
     *
     * @return exchange supplier
     */
    public ExchangeSupplier getExchange() {
        return exchange;
    }

    /**
     * Getter base pair
     *
     * @return base pair
     */
    public String getBase() {
        return base;
    }

    /**
     * Getter quote pair
     *
     * @return quote pair
     */
    public String getQuote() {
        return quote;
    }

    /**
     * Getter symbol pair
     *
     * @return symbol pair
     */
    @MongoId
    public String getSymbol() {
        return base + quote;
    }

    /**
     * Getter a checksum if is buy allowable
     *
     * @return true if buy is allowable, false otherwise
     */
    public Boolean getBuyAllowable() {
        return Boolean.TRUE.equals(isBuyAllowable);
    }

    /**
     * Getter a checksum if is sell allowable
     *
     * @return true if sell is allowable, false otherwise
     */
    public Boolean getSellAllowable() {
        return Boolean.TRUE.equals(isSellAllowable);
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public String toString() {
        return "PairInfo{" +
                "base='" + base + '\'' +
                ", quote='" + quote + '\'' +
                ", isBuyAllowable=" + isBuyAllowable +
                ", isSellAllowable=" + isSellAllowable +
                '}';
    }
}
