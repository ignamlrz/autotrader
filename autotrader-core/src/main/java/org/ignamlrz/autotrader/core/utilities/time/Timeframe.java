package org.ignamlrz.autotrader.core.utilities.time;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.security.InvalidParameterException;
import java.util.Optional;

/**
 * Model of a timeframe
 */
@Schema(description = "Timeframe")
public final class Timeframe {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Open time on milliseconds
     */
    @Schema(description = "Open time on milliseconds")
    private final long open;

    /**
     * Close time on milliseconds
     */
    @Schema(description = "Close time on milliseconds", example = "59999")
    private final long close;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of a {@link Timeframe}
     *
     * @param open  Open date
     * @param close Close date
     */
    @JsonCreator
    public Timeframe(
            @JsonProperty("open") Long open,
            @JsonProperty("close") Long close
    ) {
        this.open = Optional.of(open).get();
        this.close = Optional.of(close).get();
        doValidation();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Getter of opening time
     *
     * @return opening time
     */
    public long getOpen() {
        return open;
    }

    /**
     * Getter of closing time
     *
     * @return closing time
     */
    public long getClose() {
        return close;
    }


    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Method for get milliseconds between open and close
     *
     * @return diff
     */
    public long diff() {
        return this.close - this.open;
    }

    /**
     * Method for get interval of this timeframe
     *
     * @return an interval
     */
    public Interval interval() {
        long diff = diff();
        return Interval.of(diff + 1);
    }

    /**
     * Method for retrieve the next timeframe
     *
     * @return next timeframe
     */
    public Timeframe next() {
        long openDate = this.close + 1;
        long closeDate = openDate + diff();
        return new Timeframe(openDate, closeDate);
    }

    /**
     * Method for retrieve the previous timeframe
     *
     * @return previous timeframe
     */
    public Timeframe previous() {
        long closeDate = this.open - 1;
        long openDate = closeDate - diff();
        return new Timeframe(openDate, closeDate);
    }

    // ========================================================
    // = PRIVATE METHODS
    // ========================================================

    private void doValidation() {
        if (this.close < this.open) throw new InvalidParameterException("closing time is lower than opening time");
    }
}
