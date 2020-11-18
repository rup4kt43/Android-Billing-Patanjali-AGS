package com.example.androidbilling.login.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.login.model.dto.LoginRequestDTO;
import com.example.androidbilling.login.model.pojo.LoginResponse;
import com.example.androidbilling.network.ApiClient;
import com.example.androidbilling.utilities.scanner.MyApplication;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private MutableLiveData<String> errorMessageMutable = new MutableLiveData<>();
    private static LoginRepository instance;

    private MutableLiveData<LoginResponse> loginResponseMutableLiveData = new MutableLiveData<>();


    private LoginRepository() {

    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();

        }
        return instance;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessageMutable;
    }


    //----------------- Login User------------------------------//
    public LiveData<LoginResponse> loginValidation() {
        return loginResponseMutableLiveData;
    }

    public void validateLogin(LoginRequestDTO loginRequestDTO) {
        ApiClient.getInstance().getKot(loginRequestDTO).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {

                        PreferenceUtils.saveLoginCredentials(response.body(), MyApplication.getContext());
                        loginResponseMutableLiveData.postValue(response.body());
                    }
                } else if (response.code() == 400) {

                    try {
                        String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                        errorMessageMutable.postValue(errorMessage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    errorMessageMutable.postValue(response.message());
                }


            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                errorMessageMutable.setValue(t.toString());
            }
        });


    }
    //=====================================================================================================//



    public void clearLoginResponseData(){
        instance = null;
    }


}
