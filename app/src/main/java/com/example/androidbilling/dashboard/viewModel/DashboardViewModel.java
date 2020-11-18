package com.example.androidbilling.dashboard.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.dashboard.model.pojo.DashboardResultItem;
import com.example.androidbilling.dashboard.repository.DashboardRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {

    private DashboardRepository dashboardRepository;
    private LiveData<List<DashboardResultItem>> liveDashboardData;

    public DashboardViewModel(){

        dashboardRepository = DashboardRepository.getInstance();
        liveDashboardData = dashboardRepository.retriveDashboardData();

    }


    //----------------Live methods for view-------------------//

    public LiveData<List<DashboardResultItem>> fetchDashboardData(){
        return liveDashboardData;
    }




    //---------------Functional Methods----------------//
    public void retriveDashboardData() {

        dashboardRepository.getDashboardData();
    }
}
