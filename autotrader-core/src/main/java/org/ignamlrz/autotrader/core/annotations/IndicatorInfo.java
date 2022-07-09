package org.ignamlrz.autotrader.core.annotations;

import org.ignamlrz.autotrader.core.analysis.indicators.IndicatorCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IndicatorInfo {
    String identifier();
    String name();
    IndicatorCategory type();
}
