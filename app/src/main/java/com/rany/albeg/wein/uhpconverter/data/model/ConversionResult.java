package com.rany.albeg.wein.uhpconverter.data.model;

import android.support.annotation.IntDef;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public class ConversionResult {

    @IntDef({ SUCCESS, ERROR, LOADING })
    @interface Status {}

    public static final int SUCCESS = 1;
    public static final int ERROR = 2;
    public static final int LOADING = 3;

    @Status
    public final int status;
    public final Conversion data;
    public final Throwable error;

    private ConversionResult(@Status int status, Conversion data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ConversionResult success(Conversion data) {
        return new ConversionResult(SUCCESS, data, null);
    }

    public static ConversionResult error(Throwable throwable) {
        return new ConversionResult(ERROR, null, throwable);
    }

    public static ConversionResult loading() {
        return new ConversionResult(LOADING, null, null);
    }
}
