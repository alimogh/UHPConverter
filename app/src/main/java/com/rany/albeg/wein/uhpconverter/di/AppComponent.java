package com.rany.albeg.wein.uhpconverter.di;

import com.rany.albeg.wein.uhpconverter.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
@Singleton
@Component(modules = { AndroidSupportInjectionModule.class, AppModule.class, BuildersModule.class })
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);

        AppComponent build();
    }

    void inject(App application);
}