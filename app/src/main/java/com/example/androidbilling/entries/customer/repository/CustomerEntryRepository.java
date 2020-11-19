package com.example.androidbilling.entries.customer.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.entries.customer.model.CategoryResponse;
import com.example.androidbilling.entries.customer.model.CategoryResultList;
import com.example.androidbilling.entries.customer.model.CustomerEntryRequest;
import com.example.androidbilling.entries.customer.model.CustomerEntryResponse;
import com.example.androidbilling.entries.customer.model.DistrictResponse;
import com.example.androidbilling.entries.customer.model.DistrictResultList;
import com.example.androidbilling.entries.customer.model.StateResponse;
import com.example.androidbilling.entries.customer.model.StateResultList;
import com.example.androidbilling.network.ApiClient;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerEntryRepository {

    private static CustomerEntryRepository instance;

    private MutableLiveData<String> errorMutableLiveData;

    private MutableLiveData<List<StateResultList>> stateListMutableLiveData;
    private MutableLiveData<List<String>> categoryListMutableLiveData;
    private MutableLiveData<List<String>> districtListMutableLiveData;

    private MutableLiveData<CustomerEntryResponse> customerEntryResponseMutableLiveData;

    private List<StateResultList> stateResultLists;

    public CustomerEntryRepository() {

        errorMutableLiveData = new MutableLiveData<>();
        stateListMutableLiveData = new MutableLiveData<>();
        categoryListMutableLiveData = new MutableLiveData<>();
        districtListMutableLiveData = new MutableLiveData<>();
        customerEntryResponseMutableLiveData = new MutableLiveData<>();

        stateResultLists = new ArrayList<>();

    }

    public static CustomerEntryRepository getInstance() {
        if (instance == null) {
            instance = new CustomerEntryRepository();
        }
        return instance;
    }

    public MutableLiveData<String> getErrorMutableLiveData() {
        return errorMutableLiveData;
    }

    public MutableLiveData<List<StateResultList>> getStateListMutableLiveData() {
        return stateListMutableLiveData;
    }

    public MutableLiveData<List<String>> getCategoryListMutableLiveData() {
        return categoryListMutableLiveData;
    }

    public MutableLiveData<List<String>> getDistrictListMutableLiveData() {
        return districtListMutableLiveData;
    }

    public MutableLiveData<CustomerEntryResponse> getCustomerEntryResponseMutableLiveData() {
        return customerEntryResponseMutableLiveData;
    }

    public void getStateList(String companyId) {
        ApiClient.getInstance().getStateData(companyId).enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                if (response.code() == 200) {

//                    List<String> list = new ArrayList<>();
//
//                    for (StateResultList item : response.body().getStateResultList()) {
//                        list.add(item.getStatename());
//                    }

                    stateListMutableLiveData.postValue(response.body().getStateResultList());

                } else if (response.code() == 400) {

                }

            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

            }
        });
    }

    public void getCategoryList(String companyId) {
        ApiClient.getInstance().getCategoryData(companyId).enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.code() == 200) {

                    List<String> list = new ArrayList<>();
                    for (CategoryResultList categoryResultList : response.body().getCategoryResultList()) {
                        list.add(categoryResultList.getOrgtypename());
                    }

                    categoryListMutableLiveData.postValue(list);

                } else if (response.code() == 400) {
                    try {
                        String error = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                        errorMutableLiveData.postValue(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMutableLiveData.postValue(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {

            }
        });
    }

    public void getDistrictList(String companyId, String stateName) {
        ApiClient.getInstance().getDistrictData(companyId, stateName).enqueue(new Callback<DistrictResponse>() {
            @Override
            public void onResponse(Call<DistrictResponse> call, Response<DistrictResponse> response) {
                if (response.code() == 200) {

                    List<String> list = new ArrayList<>();
                    for (DistrictResultList districtResultList : response.body().getDistrictResultList()) {
                        list.add(districtResultList.getDistrict());
                    }

                    districtListMutableLiveData.postValue(list);

                } else if (response.code() == 400) {

                }
            }

            @Override
            public void onFailure(Call<DistrictResponse> call, Throwable t) {

            }
        });
    }

    public void saveCustomerEntry(CustomerEntryRequest customerEntryRequest) {
        Log.e("REQ", new Gson().toJson(customerEntryRequest));
        ApiClient.getInstance().saveCustomerEntry(customerEntryRequest).enqueue(new Callback<CustomerEntryResponse>() {
            @Override
            public void onResponse(Call<CustomerEntryResponse> call, Response<CustomerEntryResponse> response) {
                Log.e("SAVE cuSTOmeR", new Gson().toJson(response.body()));

                System.out.println(response.code());

                if (response.code() == 200) {

                    customerEntryResponseMutableLiveData.postValue(response.body());

                } else if (response.code() == 400) {
                    try {
                        String error = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                        errorMutableLiveData.postValue(error);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMutableLiveData.postValue(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CustomerEntryResponse> call, Throwable t) {
                errorMutableLiveData.setValue(t.toString());
            }
        });
    }

    public static void clearInstance() {
        instance = null;
    }


}
