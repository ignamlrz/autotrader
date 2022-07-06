package org.ignamlrz.autotrader.core.analysis.indicators;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.ignamlrz.autotrader.core.analysis.Analyzable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Abstract indicator
 */
@Schema(description = "Abstract Indicator")
public abstract class Indicator implements Analyzable, IndicatorProcessable {

    /**
     * Indicator identifier
     */
    @Schema(description = "Indicator identifier", example = "kvo")
    @NotNull
    @NotBlank
    @Getter
    protected final String identifier;

    /**
     * Indicator name
     */
    @Schema(description = "Indicator name", example = "Klinger Volume Oscillator")
    @NotNull
    @NotBlank
    @Getter
    protected final String name;

    /**
     * Indicator type
     */
    @Schema(description = "Indicator type", example = "INDICATOR")
    @NotNull
    @Getter
    protected final Type type;

    /**
     * Indicator options
     */
    @Schema(description = "Indicator options")
    @NotNull
    @Getter
    protected final IndicatorOptions options;

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
