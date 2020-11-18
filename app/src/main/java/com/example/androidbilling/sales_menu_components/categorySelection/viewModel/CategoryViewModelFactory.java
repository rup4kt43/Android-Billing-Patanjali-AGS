package com.example.androidbilling.sales_menu_components.categorySelection.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.sales_menu_components.salesmenu.viewmodel.SalesViewModel;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CategoryViewModel();
    }
}
