package com.example.androidbilling.login.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.login.model.dto.LoginRequestDTO;
import com.example.androidbilling.login.model.pojo.LoginResponse;
import com.example.androidbilling.login.repository.LoginRepository;


public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;
    private LiveData<LoginResponse> loginResponseLiveData;
    private MutableLiveData<String> errorMessage;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();


    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
        loginResponseLiveData = loginRepository.loginValidation();

        errorMessage = loginRepository.getErrorMessage();
        isLoading.setValue(false);

    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<LoginResponse> getLoginResponse() {

        return loginResponseLiveData;
    }

    public void validateLogin(String username, String password,String companyId) {

        if (!username.isEmpty() || !password.isEmpty()) {
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password,companyId);

            loginRepository.validateLogin(loginRequestDTO);
            isLoading.setValue(true);


        } else {
            isLoading.setValue(false);
            errorMessage.setValue("Username or Password cannot be empty!");
        }

    }

    public void clearLoginResponseData(){
       loginRepository.clearLoginResponseData();

    }



}
