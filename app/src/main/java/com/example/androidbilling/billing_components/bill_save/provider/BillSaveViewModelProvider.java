package com.example.androidbilling.billing_components.bill_save.provider;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.billing_components.bill_save.view.BillSaveView;
import com.example.androidbilling.billing_components.bill_save.viewModel.BillSaveViewModel;
import com.example.androidbilling.billing_components.bill_save.viewModel.BillSaveViewModelFactory;
import com.example.androidbilling.billing_components.billing_preview.view.BillingView;
import com.example.androidbilling.billing_components.billing_preview.viewModel.BillingViewModel;
import com.example.androidbilling.billing_components.billing_preview.viewModel.BillingViewModelFactory;

public class BillSaveViewModelProvider {

    static BillSaveViewModel instance;
    private static BillSaveView mainContext;

    public  BillSaveViewModelProvider(BillSaveView context) {
        mainContext = context;
    }

    public static BillSaveViewModel getInstance() {
        if (instance == null) {
            instance = new ViewModelProvider(mainContext, new BillSaveViewModelFactory()).get(BillSaveViewModel.class);
        }
        return instance;
    }
    public static void clearSalesMenuProviderInstance(){
        instance = null;
    }
}
