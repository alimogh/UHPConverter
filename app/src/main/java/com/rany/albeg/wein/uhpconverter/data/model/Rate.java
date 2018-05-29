package com.rany.albeg.wein.uhpconverter.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public class Rate {

    // Example JSON response from Hnbex API.
    // ---------------------------
    // "selling_rate": "4.810091",
    // "currency_code": "AUD",
    // "median_rate": "4.795704",
    // "buying_rate": "4.781317",
    // "unit_value": 1
    // ---------------------------

    @SerializedName("selling_rate")
    private double mSellingRate;
    @SerializedName("currency_code")
    private String mCurrencyCode;
    @SerializedName("median_rate")
    private double mMedianRate;
    @SerializedName("buying_rate")
    private double mBuyingRate;
    @SerializedName("unit_value")
    private int mUnitValue;

    public Rate() {
        /* no-op */
    }

    public double getSellingRate() {
        return mSellingRate;
    }

    public String getCurrencyCode() {
        return mCurrencyCode;
    }

    public double getBuyingRate() {
        return mBuyingRate;
    }

    public int getUnitValue() {
        return mUnitValue;
    }

}
