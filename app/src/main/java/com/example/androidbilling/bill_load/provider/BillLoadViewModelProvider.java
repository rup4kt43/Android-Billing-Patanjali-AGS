package com.example.androidbilling.bill_load.provider;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.bill_load.view.BillLoadView;
import com.example.androidbilling.bill_load.viewModel.BillLoadViewModel;
import com.example.androidbilling.bill_load.viewModel.BillLoadViewModelFactory;

public class BillLoadViewModelProvider {
    private static BillLoadViewModel instance;


    static BillLoadView mainContext;

    public  BillLoadViewModelProvider(BillLoadView context) {
        mainContext = context;
    }

    public static BillLoadViewModel getInstance() {
        if (instance == null) {
            instance = new ViewModelProvider(mainContext, new BillLoadViewModelFactory()).get(BillLoadViewModel.class);
        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }
}
