package com.rany.albeg.wein.uhpconverter.convert;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.rany.albeg.wein.uhpconverter.data.model.ConversionResult;
import com.rany.albeg.wein.uhpconverter.data.model.Rate;
import com.rany.albeg.wein.uhpconverter.data.usecase.GetDailyRatesUseCase;
import com.rany.albeg.wein.uhpconverter.schedulers.TestSchedulerProvider;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public class ConvertViewModelTest {

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    private GetDailyRatesUseCase mUseCase;
    @Mock
    private Observer<ConversionResult> mConversionResultObserver;
    private TestScheduler mTestScheduler;
    private ConvertViewModel mViewModel;


    @Before
    public void setUp() {
        mTestScheduler = new TestScheduler();
        mViewModel = new ConvertViewModel("HRK",new CompositeDisposable(), new TestSchedulerProvider(mTestScheduler),
                mUseCase);
    }

    @Test
    public void onClickConvert_success() {

        List<Rate> someRates = Arrays.asList(new Rate(), new Rate(), new Rate());

        when(mUseCase.execute()).thenReturn(Single.just(someRates));

        mViewModel.conversion().observeForever(mConversionResultObserver);

        mViewModel.onClickConvert(1,"USD", "HRK");

        mTestScheduler.triggerActions();

        ConversionResult conversionResult = Objects.requireNonNull(mViewModel.conversion().getValue());

        assertEquals(conversionResult.status, ConversionResult.SUCCESS);
        assertTrue(conversionResult.data != null);
    }

    @Test
    public void onClickConvert_loading() {
        List<Rate> someRates = Arrays.asList(new Rate(), new Rate(), new Rate());

        when(mUseCase.execute()).thenReturn(Single.just(someRates));

        mViewModel.conversion().observeForever(mConversionResultObserver);

        mViewModel.onClickConvert(1,"USD", "HRK");

        ConversionResult conversionResult = Objects.requireNonNull(mViewModel.conversion().getValue());

        assertEquals(conversionResult.status, ConversionResult.LOADING);
    }

    @Test
    public void onClickConvert_error() {

        when(mUseCase.execute()).thenReturn(Single.error(new Throwable("An error occurred")));

        mViewModel.conversion().observeForever(mConversionResultObserver);

        mViewModel.onClickConvert(1,"USD", "HRK");
        mTestScheduler.triggerActions();

        ConversionResult conversionResult = Objects.requireNonNull(mViewModel.conversion().getValue());

        assertEquals(conversionResult.status, ConversionResult.ERROR);
    }
}