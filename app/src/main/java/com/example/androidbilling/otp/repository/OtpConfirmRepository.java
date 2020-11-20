package com.example.androidbilling.otp.repository;

import androidx.lifecycle.MutableLiveData;

public class OtpConfirmRepository {

    public static OtpConfirmRepository instance;

    private MutableLiveData<String> errorMutableLiveData;

    public OtpConfirmRepository() {
        errorMutableLiveData = new MutableLiveData<>();
    }

    public static OtpConfirmRepository getInstance() {
        if (instance == null) {
            instance = new OtpConfirmRepository();
        }
        return instance;
    }

    public MutableLiveData<String> getErrorMutableLiveData() {
        return errorMutableLiveData;
    }

    public void saveOtp(String otp) {

    }

}
