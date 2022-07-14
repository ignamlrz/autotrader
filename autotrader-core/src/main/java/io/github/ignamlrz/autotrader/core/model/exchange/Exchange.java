package io.github.ignamlrz.autotrader.core.model.exchange;

import io.github.ignamlrz.autotrader.core.annotations.ExchangeMetadata;
import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;

public abstract class Exchange implements Exchangeable {

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public final String toString() {
        return ConversionUtils.toJson(this);
    }

    @Override
    public ExchangeMetadata exchangeMetadata() {
        return this.getClass().getAnnotation(ExchangeMetadata.class);
    }
}
