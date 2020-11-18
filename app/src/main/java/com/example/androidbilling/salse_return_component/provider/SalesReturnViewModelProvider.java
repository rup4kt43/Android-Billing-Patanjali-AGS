package com.example.androidbilling.salse_return_component.provider;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.salse_return_component.view.SalesReturnView;
import com.example.androidbilling.salse_return_component.viewModel.SalesReturnViewModel;
import com.example.androidbilling.salse_return_component.viewModel.SalesReturnViewModelFactory;

public class SalesReturnViewModelProvider {


    private static SalesReturnViewModel instance;


    static SalesReturnView mainContext;

    public SalesReturnViewModelProvider(SalesReturnView context) {
        mainContext = context;
    }

    public static SalesReturnViewModel getInstance() {
        if (instance == null) {
            instance = new ViewModelProvider(mainContext, new SalesReturnViewModelFactory()).get(SalesReturnViewModel.class);
        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }
}
