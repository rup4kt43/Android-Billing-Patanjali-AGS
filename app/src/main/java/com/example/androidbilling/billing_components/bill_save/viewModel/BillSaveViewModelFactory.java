package com.example.androidbilling.billing_components.bill_save.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.billing_components.billing_preview.viewModel.BillingViewModel;

public class BillSaveViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BillSaveViewModel();
    }
}
