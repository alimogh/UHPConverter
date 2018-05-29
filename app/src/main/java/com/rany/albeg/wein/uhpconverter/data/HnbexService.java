package com.rany.albeg.wein.uhpconverter.data;

import com.rany.albeg.wein.uhpconverter.data.model.Rate;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public interface HnbexService {

    @GET("rates/daily")
    Single<List<Rate>> getDailyRates();
}
