package com.example.androidbilling.dashboard.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.dashboard.model.pojo.DashboardResponse;
import com.example.androidbilling.dashboard.model.pojo.DashboardResultItem;
import com.example.androidbilling.network.ApiClient;
import com.example.androidbilling.utilities.global.GlobalFunctions;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRepository {

    private static DashboardRepository instance;
    private MutableLiveData<String> responseMessage;
    private MutableLiveData<List<DashboardResultItem>> mutableLiveDashboardData;
    private DashboardRepository() {
            responseMessage = new MutableLiveData<>();
            mutableLiveDashboardData = new MutableLiveData<>();
    }

    public static DashboardRepository getInstance() {
        if (instance == null) {
            instance = new DashboardRepository();
        }
        return instance;
    }

    //---------------Return method for viewModel---------------------//

    public MutableLiveData<List<DashboardResultItem>> retriveDashboardData(){
        return mutableLiveDashboardData;
    }



    //---------------Api Methods----------------------------//

    public void getDashboardData() {


        ApiClient.getInstance().getDashboardData().enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                if(response.code()==200){
                    if(response.body().getCode()==200){
                        mutableLiveDashboardData.postValue(response.body().getResult());
                    }
                }else if (response.code()==400){
                    try {
                        String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                        responseMessage.postValue(errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    responseMessage.postValue("Unknown error code!!");
                }
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                    responseMessage.postValue(t.toString());
            }
        });
    }
}
