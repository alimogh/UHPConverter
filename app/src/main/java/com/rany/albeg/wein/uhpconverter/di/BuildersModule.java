package com.rany.albeg.wein.uhpconverter.di;

import com.rany.albeg.wein.uhpconverter.convert.ConvertActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector()
    abstract ConvertActivity bindConvertActivity();
}
