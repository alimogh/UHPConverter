package com.rany.albeg.wein.uhpconverter.di;

import android.content.Context;

import com.rany.albeg.wein.uhpconverter.App;
import com.rany.albeg.wein.uhpconverter.R;
import com.rany.albeg.wein.uhpconverter.di.qualifier.CurrencyHRK;
import com.rany.albeg.wein.uhpconverter.schedulers.AppSchedulerProvider;
import com.rany.albeg.wein.uhpconverter.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
@Module
public class AppModule {
    @Provides
    public Context provideContext(App application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://hnbex.eu/api/v1/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @CurrencyHRK
    public String provideCurrencyHRK(Context context) {
        return context.getString(R.string.base_currency_hrk);
    }
}
