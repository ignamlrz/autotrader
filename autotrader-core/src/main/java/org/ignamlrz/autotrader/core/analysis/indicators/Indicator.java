package org.ignamlrz.autotrader.core.analysis.indicators;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.ignamlrz.autotrader.core.analysis.Analyzable;
import org.ignamlrz.autotrader.core.analysis.indicators.ema.EMAIndicator;
import org.ignamlrz.autotrader.core.analysis.indicators.macd.MACDIndicator;
import org.ignamlrz.autotrader.core.annotations.IndicatorInfo;
import org.ignamlrz.autotrader.core.utilities.ClassLoaderUtils;

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
        @JsonSubTypes.Type(value = EMAIndicator.class, name = IndicatorType.EMA),
        @JsonSubTypes.Type(value = MACDIndicator.class, name = IndicatorType.MACD)
})
public abstract class Indicator implements Analyzable, IndicatorProcessable {

    // ========================================================
    // = GENERIC ENUMS
    // ========================================================

    /**
     * Enum indicating type of indicators
     */
    public enum Category {
    }

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
    public final IndicatorInfo metadata() {
        return this.getClass().getAnnotation(IndicatorInfo.class);
    }

    // ========================================================
    // = STATIC METHODS
    // ========================================================

    /**
     * Method for find all Indicators
     *
     * @return a set of indicator metadata
     */
    public static Set<IndicatorInfo> findAll() {
        return ClassLoaderUtils.findAllClasses(Indicator.class.getPackage().toString()).stream()
                .filter(aClass -> aClass.getAnnotation(IndicatorInfo.class) != null)
                .map(aClass -> (IndicatorInfo) aClass.getAnnotation(IndicatorInfo.class))
                .collect(Collectors.toSet());
    }
}
