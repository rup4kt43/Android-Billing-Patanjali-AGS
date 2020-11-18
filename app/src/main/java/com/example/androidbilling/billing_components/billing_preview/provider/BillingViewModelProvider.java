package com.example.androidbilling.billing_components.billing_preview.provider;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.billing_components.billing_preview.viewModel.BillingViewModel;
import com.example.androidbilling.billing_components.billing_preview.viewModel.BillingViewModelFactory;
import com.example.androidbilling.billing_components.billing_preview.view.BillingView;

public class BillingViewModelProvider {

    static BillingViewModel instance;
    private static BillingView mainContext;

    public  BillingViewModelProvider(BillingView context) {
        mainContext = context;
    }

    public static BillingViewModel getInstance() {
        if (instance == null) {
            instance = new ViewModelProvider(mainContext, new BillingViewModelFactory()).get(BillingViewModel.class);
        }
        return instance;
    }

    public static void clearSalesMenuProviderInstance(){
        instance = null;
    }


}
