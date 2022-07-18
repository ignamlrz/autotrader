package io.github.ignamlrz.autotrader.core.repository.general;

import org.springframework.data.mongodb.core.mapping.DBRef;
import reactor.util.annotation.Nullable;

public abstract class Series {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    @DBRef
    @Nullable
    private Series previous;
    @DBRef
    @Nullable
    private Series next;

    // ========================================================
    // = GETTER/SETTER
    // ========================================================

    /**
     * Set previous series
     *
     * @param previous series to set
     */
    public void setPrevious(Series previous) {
        this.previous = previous;
    }

    /**
     * Set next series
     *
     * @param next series to set
     */
    protected void setNext(Series next) {
        this.next = next;
    }

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Check if it has next series
     *
     * @return true if it has next series, false otherwise
     */
    public boolean hasNext() {
        return next() != null;
    }

    /**
     * Check if it has previous series
     *
     * @return true if it has previous series, false otherwise
     */
    public boolean hasPrevious() {
        return previous() != null;
    }

    /**
     * Get next series
     *
     * @return next series
     */
    public @Nullable Series next() {
        return next;
    }

    /**
     * Get previous series
     *
     * @return previous series
     */
    public @Nullable Series previous() {
        return previous;
    }

}
