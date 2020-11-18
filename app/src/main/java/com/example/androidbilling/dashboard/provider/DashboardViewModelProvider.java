package com.example.androidbilling.dashboard.provider;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.dashboard.view.DashboardView;
import com.example.androidbilling.dashboard.viewModel.DashboardViewModel;
import com.example.androidbilling.dashboard.viewModel.DashboardViewModelFactory;

public class DashboardViewModelProvider {
    static DashboardViewModel instance;
    private static DashboardView mainContext;

    public DashboardViewModelProvider(DashboardView mainContext) { this.mainContext = mainContext;}

    public static DashboardViewModel getInstance(){
        if(instance ==null){
            instance = new ViewModelProvider(mainContext, new DashboardViewModelFactory()).get(DashboardViewModel.class);
        }
        return instance;
    }
}
