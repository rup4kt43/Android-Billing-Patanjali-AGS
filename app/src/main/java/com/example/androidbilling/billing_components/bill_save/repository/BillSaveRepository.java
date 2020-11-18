package com.example.androidbilling.billing_components.bill_save.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.billing_components.bill_save.dto.SaveRequest;
import com.example.androidbilling.billing_components.bill_save.pojo.SaveBillResponse;
import com.example.androidbilling.billing_components.bill_save.pojo.paymentmode.PaymentModeResponse;
import com.example.androidbilling.billing_components.bill_save.pojo.paymentmode.PaymentModeResultItem;
import com.example.androidbilling.network.ApiClient;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.global.SweetToast;
import com.example.androidbilling.utilities.scanner.MyApplication;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillSaveRepository {
    private static BillSaveRepository instance;
    private MutableLiveData<String> errorMessageMutable;
    private MutableLiveData<SaveBillResponse> printResponse;
    private MutableLiveData<List<PaymentModeResultItem>> paymentModeResponse;


    private BillSaveRepository() {
        printResponse = new MutableLiveData<>();
        errorMessageMutable = new MutableLiveData<>();
        paymentModeResponse = new MutableLiveData<>();
    }

    public static BillSaveRepository getInstance() {
        if (instance == null) {
            instance = new BillSaveRepository();
        }
        return instance;
    }

    public static void clearSalesMenuProviderInstance() {
        instance = null;
    }


    //----------------return Methods-------------------------//

    public MutableLiveData<String> getErrorMessage() {
        return errorMessageMutable;
    }

    public MutableLiveData<SaveBillResponse> getPrintResponse() {
        return printResponse;
    }

    public MutableLiveData<List<PaymentModeResultItem>> getPaymentModeResponse() {
        return paymentModeResponse;
    }

    //---------------------------------------------------------//


    public void saveData(SaveRequest saveRequest) {
        Log.e("JSON Response", new Gson().toJson(saveRequest));
        Log.e("JSON Response", new Gson().toJson(saveRequest));
        ApiClient.getInstance().saveBilling(saveRequest).enqueue(new Callback<SaveBillResponse>() {
            @Override
            public void onResponse(Call<SaveBillResponse> call, Response<SaveBillResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200) {

                        SweetToast.info(MyApplication.getContext(),response.body().getResult2());

                        GlobalFunctions.getGUID();
                        printResponse.postValue(response.body());
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                            errorMessageMutable.postValue(errorMessage);
                            GlobalFunctions.getGUID();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveBillResponse> call, Throwable t) {
                errorMessageMutable.postValue(t.toString());
            }
        });
    }

    public void getPaymentModes() {
        ApiClient.getInstance().getPaymentModes(GlobalValues.company.getCOMPANYID()).enqueue(new Callback<PaymentModeResponse>() {
            @Override
            public void onResponse(Call<PaymentModeResponse> call, Response<PaymentModeResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().getCode() == 200) {

                        paymentModeResponse.postValue(response.body().getResult());
                    }

                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                            errorMessageMutable.postValue(errorMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentModeResponse> call, Throwable t) {
                errorMessageMutable.postValue(t.toString());
            }
        });
    }
}
