package io.github.ignamlrz.autotrader.core.repository.exchange;

public class Exchange {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Exchange supplier
     */
    private final ExchangeSupplier name;

    /**
     * Exchange status
     */
    private ExchangeStatus status;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of a {@link Exchange}
     *
     * @param name of this exchange
     */
    public Exchange(ExchangeSupplier name) {
        this.name = name;
        this.status = ExchangeStatus.MEDIUM;
    }

    // ========================================================
    // = GETTERS/SETTERS
    // ========================================================

    /**
     * Getter name exchange
     *
     * @return name exchange
     */
    public ExchangeSupplier getName() {
        return name;
    }

    /**
     * Getter exchange status
     *
     * @return exchange status
     */
    public ExchangeStatus getStatus() {
        return status;
    }

    /**
     * Setter a new exchange status
     *
     * @param status to set
     */
    public void setStatus(ExchangeStatus status) {
        this.status = status;
    }

    // ========================================================
    // = OVERRIDES METHODS
    // ========================================================

    @Override
    public String toString() {
        return "Exchange{" +
                "name=" + name +
                ", status=" + status +
                '}';
    }
}
