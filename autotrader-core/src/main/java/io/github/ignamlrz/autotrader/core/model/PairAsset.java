package io.github.ignamlrz.autotrader.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;

public class PairAsset {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final String base;
    private final String quote;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of a {@link PairAsset}
     * @param base Base Asset
     * @param quote Quote Asset
     */
    @JsonCreator
    public PairAsset(@JsonProperty("base") String base, @JsonProperty("quote") String quote) {
        this.base = base;
        this.quote = quote;
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    public String getBase() {
        return base;
    }

    public String getQuote() {
        return quote;
    }

    // ========================================================
    // = METHODS
    // ========================================================

    public String merge() {
        return base + quote;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public final String toString() {
        return ConversionUtils.toJson(this);
    }
}
