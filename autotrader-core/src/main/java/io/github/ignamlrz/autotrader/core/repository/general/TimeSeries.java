package io.github.ignamlrz.autotrader.core.repository.general;

public class TimeSeries extends Series {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private final long timestamp;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    public TimeSeries(long timestamp) {
        this.timestamp = timestamp;
    }

    // ========================================================
    // = GETTER/SETTER
    // ========================================================

    public long getTimestamp() {
        return timestamp;
    }

    // ========================================================
    // = METHODS
    // ========================================================

    public final <T extends TimeSeries> void insert(T timeSeries) {

    }
}
