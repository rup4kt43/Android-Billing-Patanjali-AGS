package com.example.androidbilling.entries.customer.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.entries.customer.model.CustomerEntryRequest;
import com.example.androidbilling.entries.customer.model.CustomerEntryResponse;
import com.example.androidbilling.entries.customer.model.StateResultList;
import com.example.androidbilling.entries.customer.repository.CustomerEntryRepository;
import com.example.androidbilling.utilities.global.GlobalValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerEntryViewModel extends ViewModel {

    private CustomerEntryRepository customerEntryRepository;

    private LiveData<List<StateResultList>> stateListLiveData;
    private LiveData<List<String>> districtListLiveData;
    private LiveData<List<String>> categoryListLiveData;
    private LiveData<CustomerEntryResponse> customerEntryResponseLiveData;

    private LiveData<String> errorMessageLiveData;

    public CustomerEntryViewModel() {
        customerEntryRepository = CustomerEntryRepository.getInstance();
        stateListLiveData = customerEntryRepository.getStateListMutableLiveData();
        districtListLiveData = customerEntryRepository.getDistrictListMutableLiveData();
        categoryListLiveData = customerEntryRepository.getCategoryListMutableLiveData();
        customerEntryResponseLiveData = customerEntryRepository.getCustomerEntryResponseMutableLiveData();
        errorMessageLiveData = customerEntryRepository.getErrorMutableLiveData();
    }

    public LiveData<String> getErrorMessageLiveData() {
        return errorMessageLiveData;
    }

    public LiveData<List<StateResultList>> getStateListLiveData() {
        return stateListLiveData;
    }

    public LiveData<List<String>> getDistrictListLiveData() {
        return districtListLiveData;
    }

    public LiveData<List<String>> getCategoryListLiveData() {
        return categoryListLiveData;
    }

    public LiveData<CustomerEntryResponse> getCustomerEntryResponseLiveData() {
        return customerEntryResponseLiveData;
    }

    public void getStateList() {
        customerEntryRepository.getStateList(GlobalValues.company.getCOMPANYID());
    }

    public void getDistrictList(String state) {
        customerEntryRepository.getDistrictList(GlobalValues.company.getCOMPANYID(), state);
    }

    public void getCategoryList() {
        customerEntryRepository.getCategoryList(GlobalValues.company.getCOMPANYID());
    }


    public void saveCustomerEntry(String mode, String email, String customerName, String address, String panNo, String gstNo, String gstType, String state, String district, String mobile, String city, String postalCode, String customerId, String category, String integation, String payMode, String salesType, String acid) {

        CustomerEntryRequest customerEntryRequest = new CustomerEntryRequest();
        customerEntryRequest.setEmail(email);
        customerEntryRequest.setAcid(acid);
        customerEntryRequest.setAcname(customerName);
        customerEntryRequest.setAddress(address);
        customerEntryRequest.setCity(city);
        customerEntryRequest.setMode(mode);
        customerEntryRequest.setCompanyid(GlobalValues.company.getCOMPANYID());
        customerEntryRequest.setVatno(panNo);
        customerEntryRequest.setGstno(gstNo);
        customerEntryRequest.setGsttype(gstType);
        customerEntryRequest.setState(state);
        customerEntryRequest.setPricelevel(category);
        customerEntryRequest.setPstype(salesType);
        customerEntryRequest.setMailtype(integation);
        customerEntryRequest.setMobile(mobile);
        customerEntryRequest.setPostalcode(postalCode);
        customerEntryRequest.setPmode(payMode);
        customerEntryRequest.setCustomerid(customerId);
        customerEntryRequest.setDistrict(district);

        customerEntryRepository.saveCustomerEntry(customerEntryRequest);

    }

    public List<String> getStateName(List<StateResultList> stateResultLists) {
        List<String> list = new ArrayList<>();

        for (StateResultList data : stateResultLists) {
            list.add(data.getStatename());
        }

        return list;

    }

    public String getStateAreaCode(List<StateResultList> stateList, String stateName) {

        String stateCode = "";

        for (StateResultList list : stateList) {
            if (stateName.equals(list.getStatename())) {
                stateCode = list.getInputstatecode();
            }
        }

        return stateCode;
    }
}
