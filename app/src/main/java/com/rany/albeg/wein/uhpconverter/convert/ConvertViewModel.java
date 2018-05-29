package com.rany.albeg.wein.uhpconverter.convert;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.rany.albeg.wein.uhpconverter.common.BaseViewModel;
import com.rany.albeg.wein.uhpconverter.data.model.Conversion;
import com.rany.albeg.wein.uhpconverter.data.model.ConversionResult;
import com.rany.albeg.wein.uhpconverter.data.model.Rate;
import com.rany.albeg.wein.uhpconverter.data.usecase.HnbexUseCase;
import com.rany.albeg.wein.uhpconverter.di.qualifier.CurrencyHRK;
import com.rany.albeg.wein.uhpconverter.schedulers.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static com.rany.albeg.wein.uhpconverter.data.model.ConversionResult.error;
import static com.rany.albeg.wein.uhpconverter.data.model.ConversionResult.loading;
import static com.rany.albeg.wein.uhpconverter.data.model.ConversionResult.success;

/**
 * This file is a part of UHPConverter project.
 *
 * @author Rany Albeg Wein
 * @version 1.0.0
 * @since 29/05/2018
 */
public class ConvertViewModel extends BaseViewModel {

    private MutableLiveData<ConversionResult> mConversionResult;
    private final String mBaseCurrency;
    private final SchedulerProvider mSchedulerProvider;
    private final HnbexUseCase mUseCase;

    ConvertViewModel(@CurrencyHRK String baseCurrency, CompositeDisposable disposables,
            SchedulerProvider schedulerProvider, HnbexUseCase useCase) {
        super(disposables);
        mBaseCurrency = baseCurrency;
        mSchedulerProvider = schedulerProvider;
        mUseCase = useCase;
    }

    public LiveData<ConversionResult> conversion() {
        if (mConversionResult == null) {
            mConversionResult = new MutableLiveData<>();
        }
        return mConversionResult;
    }

    /**
     * Convert from one currency to the other.
     *
     * @param ccFrom Currency code from which you'd like to convert.
     * @param ccTo   Currency code to which you'd like to convert.
     */
    public void onClickConvert(int unit, String ccFrom, String ccTo) {
        addDisposable(mUseCase.execute()
                              .subscribeOn(mSchedulerProvider.computation())
                              .observeOn(mSchedulerProvider.ui())
                              .doOnSubscribe(__ -> mConversionResult.setValue(loading()))
                              .subscribe(
                                      rates -> mConversionResult.setValue(success(convert(unit, ccFrom, ccTo, rates))),
                                      throwable -> mConversionResult.setValue(error(throwable))));
    }

    @SuppressWarnings("ConstantConditions")
    private Conversion convert(int unit, String ccFrom, String ccTo, List<Rate> hnbexRates) {
        double buyingValue = Double.MIN_VALUE;
        double sellingValue = Double.MIN_VALUE;

        if (ccTo.equals(mBaseCurrency)) {
            Rate rate = getRateForCurrencyCode(ccFrom, hnbexRates);
            if (rate == null) {
                return Conversion.invalid(ccFrom, ccTo);
            }

            buyingValue = unit * rate.getBuyingRate() / rate.getUnitValue();
            sellingValue = unit * rate.getSellingRate() / rate.getUnitValue();

        } else if (ccFrom.equals(mBaseCurrency)) {
            Rate rate = getRateForCurrencyCode(ccTo, hnbexRates);

            if (rate == null) {
                return Conversion.invalid(ccFrom, ccTo);
            }

            buyingValue = unit * (rate.getUnitValue() / rate.getBuyingRate());
            sellingValue = unit * (rate.getUnitValue() / rate.getSellingRate());
        }

        return new Conversion(Conversion.VALID, ccFrom, ccTo, buyingValue, sellingValue);
    }

    private Rate getRateForCurrencyCode(String currencyCode, List<Rate> rates) {
        for (Rate rate : rates) {
            if (rate.getCurrencyCode().equals(currencyCode)) {
                return rate;
            }
        }
        return null;
    }
}
