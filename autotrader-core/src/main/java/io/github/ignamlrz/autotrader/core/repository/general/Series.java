package io.github.ignamlrz.autotrader.core.repository.general;

public abstract class Series {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    private Series previous;
    private Series next;

    // ========================================================
    // = GETTER/SETTER
    // ========================================================

    /**
     * Get previous series
     *
     * @return previous series
     */
    public Series getPrevious() {
        return previous;
    }

    /**
     * Set previous series
     *
     * @param previous series to set
     */
    public void setPrevious(Series previous) {
        this.previous = previous;
    }

    /**
     * Get next series
     *
     * @return next series
     */
    protected Series getNext() {
        return next;
    }

    /**
     * Set next series
     *
     * @param next series to set
     */
    protected void setNext(Series next) {
        this.next = next;
    }
}
