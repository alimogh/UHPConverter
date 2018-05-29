package com.rany.albeg.wein.uhpconverter.data.usecase;

import com.rany.albeg.wein.uhpconverter.data.model.Rate;

import java.util.List;

import io.reactivex.Single;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public interface HnbexUseCase {

    Single<List<Rate>> execute();
}
