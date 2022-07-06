package org.ignamlrz.autotrader.core.model.market;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.ignamlrz.autotrader.core.time.Timeframe;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Model of a basic candlestick
 */
@SuperBuilder
@NoArgsConstructor
@Jacksonized
@Schema(description = "Basic candlestick")
public class BasicCandlestick {

    /**
     * Opening price
     */
    @Schema(description = "Opening price", example = "0.0163")
    @Min(0)
    @Getter
    private float open;

    /**
     * Highest price
     */
    @Schema(description = "Highest price", example = "0.08")
    @Min(0)
    @Getter
    private float high;

    /**
     * Lowest price
     */
    @Schema(description = "Lowest price", example = "0.01575")
    @Min(0)
    @Getter
    private float low;

    /**
     * Closing price
     */
    @Schema(description = "Closing price", example = "0.01577")
    @Min(0)
    @Getter
    private float close;

    /**
     * Volume
     */
    @Schema(description = "Volume", example = "148976.11697")
    @Min(0)
    @Getter
    private float volume;

    /**
     * Timeframe
     */
    @Schema(description = "Timeframe")
    @NotNull
    @Getter
    private Timeframe timeframe;
}
