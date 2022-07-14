package io.github.ignamlrz.autotrader.core.model.exchange;

import io.github.ignamlrz.autotrader.core.annotations.ExchangeMetadata;

public interface Exchangeable {

    /**
     * Method for obtain exchange metadata
     *
     * @return exchange metadata
     */
    ExchangeMetadata exchangeMetadata();
}
