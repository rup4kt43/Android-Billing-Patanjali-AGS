package com.example.androidbilling.billing_components.billing_preview.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.billing_components.bill_save.dto.SaveRequest;
import com.example.androidbilling.billing_components.billing_preview.pojo.GetCustomerResponse;
import com.example.androidbilling.billing_components.billing_preview.pojo.GetCustomerResultItem;
import com.example.androidbilling.billing_components.billing_preview.pojo.calculation_pojo.CalculationResponse;
import com.example.androidbilling.network.ApiClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingRepository {
    private static BillingRepository instance;
    private MutableLiveData<List<GetCustomerResultItem>> customerDetails;
    private MutableLiveData<CalculationResponse> recalculatedData;



    private BillingRepository() {
        customerDetails = new MutableLiveData<>();
        recalculatedData = new MutableLiveData<>();

    }

    public static BillingRepository getInstance() {

        if (instance == null) {
            instance = new BillingRepository();
        }
        return instance;
    }

    //----------------------Return Methods-------------------------------------//

    public MutableLiveData<List<GetCustomerResultItem>> getCustomerDetails(){
        return customerDetails;
    }

    public MutableLiveData<CalculationResponse> getRecalculatedData(){
        return recalculatedData;
    }




    //---------------------Api Call Methods-------------------------------------//
    public void retriveCustomerList(String companyId) {
        ApiClient.getInstance().getCustomer(companyId).enqueue(new Callback<GetCustomerResponse>() {
            @Override
            public void onResponse(Call<GetCustomerResponse> call, Response<GetCustomerResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    customerDetails.postValue(response.body().getResult());

                } else if (response.code() == 400) {

                    Log.e("MEssage",response.message().toLowerCase());
                }
            }

            @Override
            public void onFailure(Call<GetCustomerResponse> call, Throwable t) {
                Log.e("MEssage",t.toString());
            }
        });

    }

    public void recalculateData(SaveRequest saveRequest) {
        Log.e("Json", new Gson().toJson(saveRequest));
        Log.e("Json", new Gson().toJson(saveRequest));
        ApiClient.getInstance().recalculateData(saveRequest).enqueue(new Callback<CalculationResponse>() {
            @Override
            public void onResponse(Call<CalculationResponse> call, Response<CalculationResponse> response) {
                if (response.code() == 200) {
                    recalculatedData.postValue(response.body());

                } else if (response.code() == 400) {

                    Log.e("MEssage",response.message().toLowerCase());
                }
            }

            @Override
            public void onFailure(Call<CalculationResponse> call, Throwable t) {
                    Log.e("Message",t.toString());
            }
        });


    }
}
