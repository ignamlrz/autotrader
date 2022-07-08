package org.ignamlrz.autotrader.binance.models.market;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.ignamlrz.autotrader.core.model.market.BasicCandlestick;

import javax.validation.constraints.Min;

/**
 * Model of a Binance candlestick
 */
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Jacksonized
@Schema(description = "Binance candlestick")
public class BinanceCandlestick extends BasicCandlestick {
    /**
     * Number of trades
     */
    @Schema(description = "Number of trades", example = "308")
    @Min(0)
    @Getter
    private int trades;

    /**
     * Quote asset volume
     */
    @Schema(description = "Quote asset volume", example = "2434.19055")
    @Min(0)
    @Getter
    private float quoteAssetVolume;


    /**
     * Taker buy base asset volume
     */
    @Schema(description = "Taker buy base asset volume", example = "1756.87")
    @Min(0)
    @Getter
    private float takerBuyBaseAssetVolume;

    /**
     * Taker buy quote asset volume
     */
    @Schema(description = "Taker buy quote asset volume", example = "28.46")
    @Min(0)
    @Getter
    private float takerBuyQuoteAssetVolume;
}
