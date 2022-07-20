package io.github.ignamlrz.autotrader.core.repository.exchange;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;

@Document
public class Exchange {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    /**
     * Exchange supplier
     */
    @MongoId
    private final ExchangeSupplier supplier;

    /**
     * Exchange status
     */
    @NotNull
    private ExchangeStatus status;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    /**
     * Constructor of a {@link Exchange}
     *
     * @param supplier of this exchange
     */
    public Exchange(ExchangeSupplier supplier) {
        this.supplier = supplier;
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
    public ExchangeSupplier getSupplier() {
        return supplier;
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
                "name=" + supplier +
                ", status=" + status +
                '}';
    }
}
