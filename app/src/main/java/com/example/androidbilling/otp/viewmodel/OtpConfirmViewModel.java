package com.example.androidbilling.otp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.databinding.OtpLayoutBinding;
import com.example.androidbilling.otp.repository.OtpConfirmRepository;

public class OtpConfirmViewModel extends ViewModel {

    private OtpConfirmRepository otpConfirmRepository;
    private LiveData<String> errorLiveData;

    public OtpConfirmViewModel() {
        otpConfirmRepository = OtpConfirmRepository.getInstance();
        errorLiveData = otpConfirmRepository.getErrorMutableLiveData();

    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void saveOtp(String otp) {
        otpConfirmRepository.saveOtp(otp);
    }

}
