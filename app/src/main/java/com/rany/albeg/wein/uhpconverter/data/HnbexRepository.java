package com.rany.albeg.wein.uhpconverter.data;

import com.rany.albeg.wein.uhpconverter.data.model.Rate;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public class HnbexRepository {

    private final HnbexService mHnbexService;

    @Inject
    public HnbexRepository(Retrofit retrofit) {
        mHnbexService = retrofit.create(HnbexService.class);
    }

    public Single<List<Rate>> getDailyRates(){
        return mHnbexService.getDailyRates();
    }
}
