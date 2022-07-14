package io.github.ignamlrz.autotrader.service.commons.response;

import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import java.security.InvalidParameterException;
import java.util.List;

public class StatusResponse<T> {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    @NotEmpty
    private final List<ErrorResponse> errors;

    @Nullable
    private final T data;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    public StatusResponse(List<ErrorResponse> errors, @Nullable T data) {
        this.errors = errors;
        this.data = data;
        doValidation();
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    public List<ErrorResponse> getErrors() {
        return errors;
    }

    @Nullable
    public T getData() {
        return data;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public final String toString() {
        return ConversionUtils.toJson(this);
    }

    // ========================================================
    // = PRIVATE METHOD
    // ========================================================

    private void doValidation() {
        if (this.errors == null) throw new InvalidParameterException("errors list can not be null");
        if (!this.errors.isEmpty() && this.data != null)
            throw new InvalidParameterException("data can not be sent if exists errors");
    }
}
