package com.example.androidbilling.sales_menu_components.salesmenu.provider;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;
import com.example.androidbilling.sales_menu_components.salesmenu.viewmodel.SalesViewModel;
import com.example.androidbilling.sales_menu_components.salesmenu.viewmodel.SalesViewModelFactory;

public class SalesMenuViewModelProvider {
    static SalesViewModel instance;
    private static  SalesMenuView mainContext;



    public SalesMenuViewModelProvider(SalesMenuView mainContext){
        this.mainContext = mainContext;
    }

    public static SalesViewModel getInstance() {

        if (instance == null) {
            instance = new ViewModelProvider(mainContext, new SalesViewModelFactory()).get(SalesViewModel.class);
        }
        return instance;
    }

    public static void clearSalesMenuProviderInstance(){
        instance = null;
    }
}
