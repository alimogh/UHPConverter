package com.rany.albeg.wein.uhpconverter.data.model;

import android.support.annotation.IntDef;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public class Conversion {

    @IntDef({ VALID, INVALID })
    @interface Status {}

    public static final int INVALID = 0;
    public static final int VALID = 1;

    @Conversion.Status
    private final int mStatus;
    private final String mFrom;
    private final String mTo;
    private final double mBuyingValue;
    private final double mSellingValue;

    public Conversion(int status, String from, String to, double buyingValue, double sellingValue) {
        mStatus = status;
        mFrom = from;
        mTo = to;
        mBuyingValue = buyingValue;
        mSellingValue = sellingValue;
    }

    public String getFrom() {
        return mFrom;
    }

    public String getTo() {
        return mTo;
    }

    public double getBuyingValue() {
        return mBuyingValue;
    }

    public double getSellingValue() {
        return mSellingValue;
    }

    public static Conversion invalid(String from,String to) {
        return new Conversion(INVALID, from, to, -1, -1);
    }

    public int getStatus() {
        return mStatus;
    }
}
