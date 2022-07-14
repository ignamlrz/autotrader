package io.github.ignamlrz.autotrader.service.commons.response;

import io.github.ignamlrz.autotrader.core.utilities.conversion.ConversionUtils;

import javax.validation.constraints.NotEmpty;

public class ErrorResponse {

    // ========================================================
    // = INSTANCE FIELDS
    // ========================================================

    @NotEmpty
    private final int code;

    @NotEmpty
    private final String msg;

    // ========================================================
    // = CONSTRUCTORS
    // ========================================================

    public ErrorResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // ========================================================
    // = GETTERS
    // ========================================================

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    // ========================================================
    // = OVERRIDE METHODS
    // ========================================================

    @Override
    public final String toString() {
        return ConversionUtils.toJson(this);
    }
}
