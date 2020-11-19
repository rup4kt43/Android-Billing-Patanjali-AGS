package com.example.androidbilling.otp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.salse_return_component.viewModel.SalesReturnViewModel;

public class OtpConfirmViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new OtpConfirmViewModel();
    }
}
