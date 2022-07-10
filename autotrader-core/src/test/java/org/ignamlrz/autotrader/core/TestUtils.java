package org.ignamlrz.autotrader.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import org.junit.jupiter.api.function.Executable;
import org.springframework.lang.Nullable;

import java.util.Optional;

import static org.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils.fromJson;
import static org.junit.jupiter.api.Assertions.*;

public class TestUtils {

    public static <T> T buildJSON(String json, Class<T> toClass, String errorMsg) throws JsonProcessingException {
        if (errorMsg == null) return Optional.of(fromJson(json, toClass)).get();
        else {
            ValueInstantiationException e = assertThrows(ValueInstantiationException.class, () -> fromJson(json, toClass));
            assertEquals(e.getCause().getMessage(), errorMsg);
            return null;
        }
    }

    @Deprecated
    public static <T extends Throwable> void runWithPossibleThrows(
            @Nullable Class<T> expectedType,
            Executable f
    ) throws Throwable {
        if (expectedType == null) {
            f.execute();
        } else {
            assertThrows(expectedType, f);
        }
    }
}
