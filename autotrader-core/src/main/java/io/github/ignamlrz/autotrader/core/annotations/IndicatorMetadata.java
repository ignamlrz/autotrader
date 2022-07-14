package io.github.ignamlrz.autotrader.core.annotations;

import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorCategory;
import io.github.ignamlrz.autotrader.core.analysis.indicators.IndicatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IndicatorMetadata {
    IndicatorType identifier();
    String name();
    IndicatorCategory type();
}
