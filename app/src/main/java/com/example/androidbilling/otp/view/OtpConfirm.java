package com.example.androidbilling.otp.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.databinding.OtpLayoutBinding;
import com.example.androidbilling.otp.viewmodel.OtpConfirmViewModel;
import com.example.androidbilling.otp.viewmodel.OtpConfirmViewModelFactory;
import com.example.androidbilling.utilities.global.SweetToast;

public class OtpConfirm extends AppCompatActivity implements TextWatcher {

    OtpLayoutBinding otpLayoutBinding;
    OtpConfirmViewModel otpConfirmViewModel;

    //    private EditText code1, code2, code3, code4;
    private String code1, code2, code3, code4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupViewBinding();
        initComponents();
        initViewModel();
        initObserver();
        setupOnClick();

    }

    private void setupOnClick() {
        otpLayoutBinding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

                code1 = otpLayoutBinding.input1.getText().toString();
                code2 = otpLayoutBinding.input2.getText().toString();
                code3 = otpLayoutBinding.input3.getText().toString();
                code4 = otpLayoutBinding.input4.getText().toString();

                String otp = code1 + code2 + code3 + code4;

                if (otp.length() == 4) {
                    otpConfirmViewModel.saveOtp(otp);
                } else {
                    SweetToast.info(OtpConfirm.this, "Enter valid otp code");
                }

            }
        });
    }

    private void initObserver() {
        otpConfirmViewModel.getErrorLiveData().observe(this, s -> {
            //
        });
    }

    private void initViewModel() {
        otpConfirmViewModel = new ViewModelProvider(this, new OtpConfirmViewModelFactory()).get(OtpConfirmViewModel.class);
    }

    private void initComponents() {
        otpLayoutBinding.input1.addTextChangedListener(this);
        otpLayoutBinding.input2.addTextChangedListener(this);
        otpLayoutBinding.input2.addTextChangedListener(this);
        otpLayoutBinding.input2.addTextChangedListener(this);
    }

    private void setupViewBinding() {
        otpLayoutBinding = OtpLayoutBinding.inflate(getLayoutInflater());
        setContentView(otpLayoutBinding.getRoot());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.length() == 1) {
            if (otpLayoutBinding.input1.length() == 1) {
                otpLayoutBinding.input2.requestFocus();
            }

            if (otpLayoutBinding.input2.length() == 1) {
                otpLayoutBinding.input3.requestFocus();
            }

            if (otpLayoutBinding.input3.length() == 1) {
                otpLayoutBinding.input4.requestFocus();
            }


        } else if (s.length() == 0) {
            if (otpLayoutBinding.input4.length() == 0) {
                otpLayoutBinding.input3.requestFocus();
            }

            if (otpLayoutBinding.input3.length() == 0) {
                otpLayoutBinding.input2.requestFocus();
            }

            if (otpLayoutBinding.input2.length() == 0) {
                otpLayoutBinding.input1.requestFocus();
            }

        }

    }


}
