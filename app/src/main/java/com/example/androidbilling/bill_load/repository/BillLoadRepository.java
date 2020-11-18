package com.example.androidbilling.bill_load.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.bill_load.pojo.GetBillResponse;
import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.network.ApiClient;
import com.example.androidbilling.utilities.global.GlobalFunctions;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillLoadRepository {
    private static BillLoadRepository instance;

    //Mutable LiveData
    private MutableLiveData<GetBillResult> mutableBillResult;
    private MutableLiveData<String> message;

    private BillLoadRepository() {
        //Private Default constructor to avoid making multiple object in different classes
        mutableBillResult = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }


    //Providing the instance of the repository
    public static BillLoadRepository getInstance() {
        if (instance == null) {
            instance = new BillLoadRepository();
        }
        return instance;
    }

    public static void clearSalesRepositoryInstanc() {
        instance = null;
    }

    //-------------Methods that provide data to viewModel-------------------//
    public MutableLiveData<GetBillResult> getBillResult() {
        return mutableBillResult;
    }

    public MutableLiveData<String> getResponseMessage() {
        return message;
    }


    //-------------------Methods that handle the api Call---------------------------//
    public void getBill(String billNo, String companyid) {
        ApiClient.getInstance().getBill(billNo, companyid).enqueue(new Callback<GetBillResponse>() {
            @Override
            public void onResponse(Call<GetBillResponse> call, Response<GetBillResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getCode() == 200) {
                        mutableBillResult.postValue(response.body().getGetBillResult());

                    }


                } else if (response.code() == 400) {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                            message.postValue(errorMessage);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<GetBillResponse> call, Throwable t) {
                message.postValue(t.toString());
            }
        });

    }

    public static void clearInstance(){
        instance= null;
    }


}
