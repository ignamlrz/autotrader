package org.ignamlrz.autotrader.core.model.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;
import org.ignamlrz.autotrader.core.utilities.time.Timeframe;

import javax.validation.constraints.NotNull;
import java.security.InvalidParameterException;
import java.util.Optional;

/**
 * Model of a basic candlestick
 */
@Schema(description = "Basic candlestick")
public class Candlestick {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Opening price
     */
    @Schema(description = "Opening price", example = "0.0163")
    private final float open;

    /**
     * Highest price
     */
    @Schema(description = "Highest price", example = "0.08")
    private final float high;

    /**
     * Lowest price
     */
    @Schema(description = "Lowest price", example = "0.01575")
    private final float low;

    /**
     * Closing price
     */
    @Schema(description = "Closing price", example = "0.01577")
    private final float close;

    /**
     * Volume
     */
    @Schema(description = "Volume", example = "148976.11697")
    private final float volume;

    /**
     * Timeframe
     */
    @Schema(description = "Timeframe")
    private final Timeframe timeframe;

    /**
     * Next candlestick
     */
    @Schema(description = "Next candlestick")
    Candlestick next;

    // ========================================================
    // = CONSTRUCTOR
    // ========================================================

    @JsonCreator
    public Candlestick(
            @JsonProperty("open") @NotNull Float open,
            @JsonProperty("high") @NotNull Float high,
            @JsonProperty("low") @NotNull Float low,
            @JsonProperty("close") @NotNull Float close,
            @JsonProperty("volume") @NotNull Float volume,
            @JsonProperty("timeframe") @NotNull Timeframe timeframe
    ) {
        this.open = Optional.of(open).get();
        this.high = Optional.of(high).get();
        this.low = Optional.of(low).get();
        this.close = Optional.of(close).get();
        this.volume = Optional.of(volume).get();
        this.timeframe = Optional.of(timeframe).get();
        this.next = null;
        doValidation();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of opening price
     *
     * @return opening price
     */
    public float getOpen() {
        return open;
    }

    /**
     * Getter of the highest price
     *
     * @return highest price
     */
    public float getHigh() {
        return high;
    }

    /**
     * Getter of the lowest price
     *
     * @return lowest price
     */
    public float getLow() {
        return low;
    }

    /**
     * Getter of closing price
     *
     * @return closing price
     */
    public float getClose() {
        return close;
    }

    /**
     * Getter of volume
     *
     * @return volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Getter of timeframe
     *
     * @return timeframe
     */
    public Timeframe getTimeframe() {
        return timeframe;
    }

    // ========================================================
    // = SETTERS
    // ========================================================

    /**
     * Setter of next
     *
     * @param next Next candlestick
     */
    public void setNext(Candlestick next) {
        this.next = next;
    }

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Getter of next
     *
     * @return next candlestick
     */
    public Candlestick next() {
        return next;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public final String toString() {
        return ConversionUtils.toJson(this);
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    /**
     * Private method for check validation of this class
     */
    private void doValidation() {
        if (this.open < 0) throw new InvalidParameterException("opening price is lower than 0");
        if (this.close < 0) throw new InvalidParameterException("closing price is lower than 0");
        if (this.high < this.open || this.high < this.close)
            throw new InvalidParameterException("high must be the highest value");
        if (this.low > this.open || this.low > this.close)
            throw new InvalidParameterException("low must be the lowest value");
        if (this.volume < 0) throw new InvalidParameterException("volume is lower than 0");
    }
}
