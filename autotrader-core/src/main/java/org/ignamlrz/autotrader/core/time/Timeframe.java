package org.ignamlrz.autotrader.core.time;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

/**
 * Model of a timeframe
 */
@Builder
@Jacksonized
@Schema(description = "Timeframe")
public final class Timeframe {
    @Getter
    private long open;
    @Getter
    private long close;

    /**
     * Constructor of a timeframe
     *
     * @param open  Open date
     * @param close Close date
     */
    Timeframe(long open, long close) {
        this.open = open;
        this.close = close;
    }

    /**
     * Method for get milliseconds between open and close
     *
     * @return diff
     */
    public long getDiff() {
        return this.close - this.open;
    }

    /**
     * Method for get interval of this timeframe
     *
     * @return an interval
     */
    public Interval getInterval() {
        long diff = getDiff();
        return Interval.of(diff + 1);
    }

    /**
     * Method for retrieve the next timeframe
     *
     * @return next timeframe
     */
    public Timeframe next() {
        long openDate = this.close + 1;
        long closeDate = openDate + getDiff();
        return new Timeframe(openDate, closeDate);
    }

    /**
     * Method for retrieve the previous timeframe
     *
     * @return previous timeframe
     */
    public Timeframe previous() {
        long closeDate = this.open - 1;
        long openDate = closeDate - getDiff();
        return new Timeframe(openDate, closeDate);
    }
}
