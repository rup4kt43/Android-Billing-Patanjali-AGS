package com.example.androidbilling.salse_return_component.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.billing_components.bill_save.dto.SaveRequest;
import com.example.androidbilling.billing_components.bill_save.pojo.SaveBillResponse;
import com.example.androidbilling.network.ApiClient;
import com.example.androidbilling.salse_return_component.pojo.SalesReturnBillLoadResponse;
import com.example.androidbilling.salse_return_component.pojo.SalesReturnBillLoadResultItem;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesReturnRepository {

    private MutableLiveData<String> responseMessage;

    private static SalesReturnRepository instance;
    private MutableLiveData<List<SalesReturnBillLoadResultItem>> mutableSalesReturnResult;

    private SalesReturnRepository() {
        responseMessage = new MutableLiveData<>();
        mutableSalesReturnResult = new MutableLiveData<>();
    }

    public static SalesReturnRepository getInstance() {
        if (instance == null) {
            instance = new SalesReturnRepository();

        }
        return instance;
    }

    //--------------Live return method----------------//
    public MutableLiveData<String> getResponseMessage() {
        return responseMessage;
    }

    public MutableLiveData<List<SalesReturnBillLoadResultItem>> returnSalesReturnBill() {
        return mutableSalesReturnResult;
    }


    public void saveReturnData(SaveRequest saveRequest) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        Log.e("gson", gson.toJson(saveRequest));
        Log.e("Status", new Gson().toJson(saveRequest));
        Log.e("Status", new Gson().toJson(saveRequest));
        ApiClient.getInstance().saveBilling(saveRequest).enqueue(new Callback<SaveBillResponse>() {
            @Override
            public void onResponse(Call<SaveBillResponse> call, Response<SaveBillResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getStatusCode() == 200) {
                        Log.e("Result", response.body().getResult());
                        Log.e("Result", response.body().getResult());
                        responseMessage.postValue(response.body().getResult2());

                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                            responseMessage.postValue(errorMessage);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveBillResponse> call, Throwable t) {
                responseMessage.postValue(t.toString());
            }
        });
    }


    //------------------------Sales Return Bill Load-----------------------------//
    public void getSalesReturnBill(String companyId, String billNo) {
        ApiClient.getInstance().getSalesReturnBill(companyId, billNo).enqueue(new Callback<SalesReturnBillLoadResponse>() {
            @Override
            public void onResponse(Call<SalesReturnBillLoadResponse> call, Response<SalesReturnBillLoadResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getCode() == 200) {
                        if(response.body().getResult().size()>0){
                            for(int i=0;i<response.body().getResult().size();i++){
                                response.body().getResult().get(i).setRemainingQuantity(response.body().getResult().get(i).getSTOCK());
                            }
                            mutableSalesReturnResult.postValue(response.body().getResult());

                        }else{
                            responseMessage.postValue("Sorry No data available for the provided Bill Number");
                        }
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                            responseMessage.postValue(errorMessage);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesReturnBillLoadResponse> call, Throwable t) {
                responseMessage.postValue(t.toString());
            }
        });
    }

    public static void clearInstance() {
        instance = null;
    }
}
