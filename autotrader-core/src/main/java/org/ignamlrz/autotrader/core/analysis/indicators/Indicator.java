package org.ignamlrz.autotrader.core.analysis.indicators;

import lombok.Data;
import org.ignamlrz.autotrader.core.analysis.Analyzable;

/**
 * Abstract indicator
 */
@Data
public abstract class Indicator implements Analyzable, IndicatorProcessable {

    /**
     * Enum indicating type of indicators
     */
    public enum Type {
        SIMPLE, INDICATOR, MATH, OVERLAY
    }

    /**
     * Enum indicating target of an indicator
     */
    public enum Target {
        OPEN, CLOSE, HIGH, LOW
    }

    /**
     * Indicator identifier
     */
    protected final String identifier;

    /**
     * Indicator name
     */
    protected final String name;

    /**
     * Indicator type
     */
    protected final Type type;

    /**
     * Indicator options
     */
    protected final IndicatorOptions options;

    /**
     * Constructor of a {@link Indicator}
     *
     * @param identifier  Indicator identifier
     * @param name Indicator name
     * @param type Indicator type
     */
    protected Indicator(String identifier, String name, Type type, IndicatorOptions options) {
        this.identifier = identifier;
        this.name = name;
        this.type = type;
        this.options = options;
    }
}
