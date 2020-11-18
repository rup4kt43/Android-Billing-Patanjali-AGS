package com.example.androidbilling.bill_load.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.bill_load.repository.BillLoadRepository;
import com.example.androidbilling.billing_components.billing_preview.dto.BillingCalculationDTO;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.google.gson.Gson;

public class BillLoadViewModel extends ViewModel {
    private MutableLiveData<String> message;
    private MutableLiveData<GetBillResult> mutableBillResult;
    BillLoadRepository repository;
    private MutableLiveData<BillingCalculationDTO> billingCalculationDTOMutableLiveData;

    public BillLoadViewModel(){
        billingCalculationDTOMutableLiveData = new MutableLiveData<>();
        message = new MutableLiveData<>();
        repository = BillLoadRepository.getInstance();

        message = repository.getResponseMessage();      //Getting the message from the repository
        mutableBillResult = repository.getBillResult();     //Getting the result from the repository
    }


    //--------------------Live methods that view is observing---------------------//
    public LiveData<String> fetchMessage(){
        return message;
    }

    public LiveData<GetBillResult> fetchBillResult(){
        return mutableBillResult;
    }

    public LiveData<BillingCalculationDTO> billingCalculationDTOLiveData() {
        return billingCalculationDTOMutableLiveData;
    }
    //-------------------------------------------------------------------------------//\




    public void getBill(String bill_type,String bill_number,String division,String phiscalId) {

        String billNo = bill_type+bill_number+division+phiscalId;
        if(billNo.isEmpty()){
            message.postValue("Sorry! Please enter the bill number to proceed!!");
        }else{
            repository.getBill(billNo, GlobalValues.company.getCOMPANYID());

        }
    }






}
