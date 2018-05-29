package com.rany.albeg.wein.uhpconverter.convert;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rany.albeg.wein.uhpconverter.R;
import com.rany.albeg.wein.uhpconverter.data.model.Conversion;
import com.rany.albeg.wein.uhpconverter.data.model.ConversionResult;
import com.rany.albeg.wein.uhpconverter.views.SmarterSpinner;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class ConvertActivity extends AppCompatActivity {

    @BindView(R.id.tv_selling_rate)
    TextView mSellingRate;
    @BindView(R.id.tv_buying_rate)
    TextView mBuyingRate;
    @BindView(R.id.sp_from)
    SmarterSpinner mCurrencyCodeFrom;
    @BindView(R.id.sp_to)
    SmarterSpinner mCurrencyCodeTo;
    @BindView(R.id.pb_loading)
    ProgressBar mLoading;

    @Inject
    ConvertViewModelFactory mConvertViewModelFactory;
    private ConvertViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mViewModel = ViewModelProviders.of(this, mConvertViewModelFactory).get(ConvertViewModel.class);
        observe();

        initCurrencyCodePickers();
    }

    private void initCurrencyCodePickers() {
        String[] currencyCodes = getResources().getStringArray(R.array.currency_codes);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                currencyCodes);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mCurrencyCodeFrom.setAdapter(dataAdapter);
        mCurrencyCodeTo.setAdapter(dataAdapter);

        mCurrencyCodeFrom.programmaticallySetPosition(0, true);
        mCurrencyCodeTo.programmaticallySetPosition(1, true);

        mCurrencyCodeFrom.setOnItemSelectedListener(new SmarterSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id, boolean userSelected) {
                if (userSelected) {
                    mCurrencyCodeTo.programmaticallySetPosition(0, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mCurrencyCodeTo.setOnItemSelectedListener(new SmarterSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id, boolean userSelected) {
                if (userSelected) {
                    mCurrencyCodeFrom.programmaticallySetPosition(0, true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void observe() {
        mViewModel.conversion().observe(this, this::renderConversionResult);
    }

    private void renderConversionResult(ConversionResult conversionResult) {
        switch (conversionResult.status) {

            case ConversionResult.ERROR:
                error(conversionResult.error);
                break;
            case ConversionResult.LOADING:
                loading();
                break;
            case ConversionResult.SUCCESS:
                success(conversionResult.data);
                break;
        }
    }

    private void success(Conversion conversion) {
        mLoading.setVisibility(View.GONE);
        if (conversion.getStatus() == Conversion.VALID) {
            mSellingRate.setText(getString(R.string.fmt_selling, conversion.getSellingValue()));
            mBuyingRate.setText(getString(R.string.fmt_buying, conversion.getBuyingValue()));
        } else {
            mSellingRate.setText("---");
            mBuyingRate.setText("---");
            Toast.makeText(this, "Conversion not available!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loading() {
        mLoading.setVisibility(View.VISIBLE);
    }

    private void error(Throwable error) {
        mLoading.setVisibility(View.GONE);

        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.bt_convert)
    void onClickConvert() {
        String ccFrom = mCurrencyCodeFrom.getSelectedItem().toString();
        String ccTo = mCurrencyCodeTo.getSelectedItem().toString();

        mViewModel.onClickConvert(1, ccFrom, ccTo);
    }
}
