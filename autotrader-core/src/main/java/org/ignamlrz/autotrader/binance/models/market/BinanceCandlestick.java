package org.ignamlrz.autotrader.binance.models.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.ignamlrz.autotrader.core.model.market.Candlestick;
import org.ignamlrz.autotrader.core.utilities.time.Timeframe;

import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
import java.util.Optional;

/**
 * Model of a Binance candlestick
 */
@Schema(description = "Binance candlestick")
public class BinanceCandlestick extends Candlestick {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Number of trades
     */
    @Schema(description = "Number of trades", example = "308")
    private final int trades;

    /**
     * Quote asset volume
     */
    @Schema(description = "Quote asset volume", example = "2434.19055")
    private final float quoteVolume;


    /**
     * Taker buy base asset volume
     */
    @Schema(description = "Taker buy base asset volume", example = "1756.87")
    private final float takerBuyVolume;

    /**
     * Taker buy quote asset volume
     */
    @Schema(description = "Taker buy quote asset volume", example = "28.46")
    private final float takerBuyQuoteVolume;

    // ========================================================
    // = CONSTRUCTOR
    // ========================================================

    @JsonCreator
    public BinanceCandlestick(
            @JsonProperty("open") @NotNull Float open,
            @JsonProperty("high") @NotNull Float high,
            @JsonProperty("low") @NotNull Float low,
            @JsonProperty("close") @NotNull Float close,
            @JsonProperty("volume") @NotNull Float volume,
            @JsonProperty("timeframe") @NotNull Timeframe timeframe,
            @JsonProperty("trades") @NotNull Integer trades,
            @JsonProperty("quoteVolume") @NotNull Float quoteVolume,
            @JsonProperty("takerBuyVolume") @NotNull Float takerBuyVolume,
            @JsonProperty("takerBuyQuoteVolume") @NotNull Float takerBuyQuoteVolume
    ) {
        super(open, high, low, close, volume, timeframe);
        this.trades = Optional.of(trades).get();
        this.quoteVolume = Optional.of(quoteVolume).get();
        this.takerBuyVolume = Optional.of(takerBuyVolume).get();
        this.takerBuyQuoteVolume = Optional.of(takerBuyQuoteVolume).get();
        doValidation();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of num of trades
     *
     * @return Num of trades
     */
    public int getTrades() {
        return this.trades;
    }

    /**
     * Getter of quote asset volume
     *
     * @return quote asset volume
     */
    public float getQuoteVolume() {
        return quoteVolume;
    }

    /**
     * Getter of taker buy base asset volume
     *
     * @return taker buy base asset volume
     */
    public float getTakerBuyVolume() {
        return takerBuyVolume;
    }

    /**
     * Getter of taker buy quote asset volume
     *
     * @return taker buy quote asset volume
     */
    public float getTakerBuyQuoteVolume() {
        return takerBuyQuoteVolume;
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    private void doValidation() {
        if (this.trades < 0) throw new InvalidParameterException("num trades is lower than 0");
        if (this.quoteVolume < 0) throw new InvalidParameterException("quote asset volume is lower than 0");
        if (this.takerBuyVolume < 0)
            throw new InvalidParameterException("taker buy base asset volume is lower than 0");
        if (this.takerBuyQuoteVolume < 0)
            throw new InvalidParameterException("taker buy quote asset volume is lower than 0");
    }
}
