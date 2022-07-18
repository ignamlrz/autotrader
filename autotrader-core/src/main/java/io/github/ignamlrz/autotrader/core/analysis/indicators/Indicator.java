package io.github.ignamlrz.autotrader.core.analysis.indicators;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.ignamlrz.autotrader.core.analysis.Analyzable;
import io.github.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicator;
import io.github.ignamlrz.autotrader.core.analysis.indicators.macd.MACDIndicator;
import io.github.ignamlrz.autotrader.core.annotations.IndicatorMetadata;
import io.github.ignamlrz.autotrader.core.utilities.ClassLoaderUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Abstract indicator
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = EMAIndicator.class, name = "ema"),
        @JsonSubTypes.Type(value = MACDIndicator.class, name = "macd")
})
public abstract class Indicator implements Analyzable, IndicatorProcessable {

    // ========================================================
    // = GETTERS
    // ========================================================

    /**
     * Method for get an {@link IndicatorOptions}
     *
     * @return an {@link IndicatorOptions}
     */
    public abstract IndicatorOptions getOptions();

    // ========================================================
    // = METHODS
    // ========================================================

    /**
     * Retrieve Indicator Metadata
     *
     * @return indicator metadata
     */
    public final IndicatorMetadata metadata() {
        return this.getClass().getAnnotation(IndicatorMetadata.class);
    }

    // ========================================================
    // = STATIC METHODS
    // ========================================================

    /**
     * Method for find all Indicators
     *
     * @return a set of indicator metadata
     */
    public static Set<IndicatorMetadata> findAll() {
        return ClassLoaderUtils.findAllClasses(Indicator.class.getPackage().toString()).stream()
                .filter(aClass -> aClass.getAnnotation(IndicatorMetadata.class) != null)
                .map(aClass -> (IndicatorMetadata) aClass.getAnnotation(IndicatorMetadata.class))
                .collect(Collectors.toSet());
    }
}
