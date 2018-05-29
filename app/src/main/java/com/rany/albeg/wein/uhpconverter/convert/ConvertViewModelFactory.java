package com.rany.albeg.wein.uhpconverter.convert;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.rany.albeg.wein.uhpconverter.data.usecase.GetDailyRatesUseCase;
import com.rany.albeg.wein.uhpconverter.di.qualifier.CurrencyHRK;
import com.rany.albeg.wein.uhpconverter.schedulers.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public class ConvertViewModelFactory implements ViewModelProvider.Factory {

    private String mBaseCurrency;
    private final GetDailyRatesUseCase mUseCase;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mDisposables;

    @Inject
    public ConvertViewModelFactory(@CurrencyHRK String baseCurrency, GetDailyRatesUseCase useCase,
            SchedulerProvider schedulerProvider, CompositeDisposable disposables) {
        mBaseCurrency = baseCurrency;
        mUseCase = useCase;
        mSchedulerProvider = schedulerProvider;
        mDisposables = disposables;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ConvertViewModel.class)) {
            //noinspection unchecked
            return (T) new ConvertViewModel(mBaseCurrency, mDisposables, mSchedulerProvider, mUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
