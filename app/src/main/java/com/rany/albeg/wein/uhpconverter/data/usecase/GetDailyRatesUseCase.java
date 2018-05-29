package com.rany.albeg.wein.uhpconverter.data.usecase;

import com.rany.albeg.wein.uhpconverter.data.HnbexRepository;
import com.rany.albeg.wein.uhpconverter.data.model.Rate;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public class GetDailyRatesUseCase implements HnbexUseCase{

    private HnbexRepository mRepository;

    @Inject
    public GetDailyRatesUseCase(HnbexRepository repository) {
        mRepository = repository;
    }

    @Override
    public Single<List<Rate>> execute() {
        return mRepository.getDailyRates();
    }
}
