package io.github.ignamlrz.autotrader.core.repository.symbol;

import io.github.ignamlrz.autotrader.core.repository.exchange.Exchange;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
public class Symbol {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Symbol name
     */
    @NotNull
    @MongoId
    private final String symbol;

    /**
     * Exchange supplier which belong this symbol
     */
    @NotNull
    @DBRef
    private final Exchange exchange;

    /**
     * Base pair
     */
    @NotNull
    @Size(min = 2, max = 5)
    private final String base;

    /**
     * Quote pair
     */
    @NotNull
    @Size(min = 2, max = 5)
    private final String quote;

    /**
     * Is buy allowable?
     */
    private Boolean isBuyAllowable;

    /**
     * Is sell allowable?
     */
    private Boolean isSellAllowable;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of a {@link Symbol}
     *
     * @param base  Base Asset
     * @param quote Quote Asset
     */
    public Symbol(@NotNull Exchange exchange, @NotNull String base, @NotNull String quote) {
        this(exchange.getSupplier() + ":" + base + quote, exchange, base, quote);
    }

    /**
     * Constructor of a {@link Symbol}
     *
     * @param base  Base Asset
     * @param quote Quote Asset
     */
    @PersistenceCreator
    public Symbol(String symbol, @NotNull Exchange exchange, String base, String quote) {
        super();
        this.symbol = symbol.toUpperCase();
        this.exchange = exchange;
        this.base = base.toUpperCase();
        this.quote = quote.toUpperCase();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter symbol pair
     *
     * @return symbol pair
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Getter exchange supplier which belong this symbol
     *
     * @return exchange supplier
     */
    public Exchange getExchange() {
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
     * Getter a checksum if is buy allowable
     *
     * @return true if buy is allowable, false otherwise
     */
    public boolean isBuyAllowable() {
        return Boolean.TRUE.equals(isBuyAllowable);
    }

    /**
     * Setter a checksum about if it is buy allowable
     *
     * @param buyAllowable to set
     */
    public void setBuyAllowable(@NotNull Boolean buyAllowable) {
        isBuyAllowable = buyAllowable;
    }

    /**
     * Getter a checksum if is sell allowable
     *
     * @return true if sell is allowable, false otherwise
     */
    public boolean isSellAllowable() {
        return Boolean.TRUE.equals(isSellAllowable);
    }

    /**
     * Setter a checksum about if it is sell allowable
     *
     * @param sellAllowable to set
     */
    public void setSellAllowable(@NotNull Boolean sellAllowable) {
        isSellAllowable = sellAllowable;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public String toString() {
        return "Symbol{" +
                "exchange=" + exchange +
                ", symbol='" + symbol + '\'' +
                '}';
    }
}
