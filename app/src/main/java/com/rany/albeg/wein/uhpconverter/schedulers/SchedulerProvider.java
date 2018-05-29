package com.rany.albeg.wein.uhpconverter.schedulers;

import io.reactivex.Scheduler;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public interface SchedulerProvider {

    Scheduler computation();

    Scheduler ui();

    Scheduler io();
}
