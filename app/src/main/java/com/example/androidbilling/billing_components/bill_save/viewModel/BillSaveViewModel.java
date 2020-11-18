package com.example.androidbilling.billing_components.bill_save.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.billing_components.bill_save.dto.ProductDetails;
import com.example.androidbilling.billing_components.bill_save.dto.SaveRequest;
import com.example.androidbilling.billing_components.bill_save.dto.trnmode.PaymentDetails;
import com.example.androidbilling.billing_components.bill_save.pojo.SaveBillResponse;
import com.example.androidbilling.billing_components.bill_save.pojo.paymentmode.PaymentModeResultItem;
import com.example.androidbilling.billing_components.bill_save.repository.BillSaveRepository;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;

import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.scanner.MyApplication;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class BillSaveViewModel extends ViewModel {
    private BillSaveRepository billSaveRepository;

    private MutableLiveData<String> errorMessage;
    private MutableLiveData<SaveBillResponse> printResponse;
    private MutableLiveData<List<PaymentModeResultItem>> paymentModeResponse;
    private MutableLiveData<List<PaymentModeResultItem>> filteredMutableList;

    public BillSaveViewModel() {
        billSaveRepository = BillSaveRepository.getInstance();
        errorMessage = billSaveRepository.getErrorMessage();
        printResponse = billSaveRepository.getPrintResponse();
        paymentModeResponse = billSaveRepository.getPaymentModeResponse();
        filteredMutableList = new MutableLiveData<>();


    }


    //--------------------------Fetch Methods---------------------------------------//

    public LiveData<String> fetchErrorMessage() {
        return errorMessage;

    }

    public LiveData<SaveBillResponse> fetchPrintResponse() {
        return printResponse;
    }

    public LiveData<List<PaymentModeResultItem>> fetchPaymentModes() {
        return paymentModeResponse;
    }

    public LiveData<List<PaymentModeResultItem>> fetchPaymentModeName() {
        return filteredMutableList;
    }

    //-------------------------------------------------------------------------------//


    //----------------------------------Functional Methods-----------------------------------------------//

    public void saveData(ItemDetailsResult itemDetailsResult, String customerName, String customerAddress, String customerPhone, List<PaymentDetails> paymentDetailsList) {
        String trnMode = "";
        String trnac = "";
        if (paymentDetailsList.size() > 1) {
            trnMode = "MixedMode";
        } else if (paymentDetailsList.size() == 1) {
            trnMode = paymentDetailsList.get(0).getPaymentMode();
        }


        if (trnMode.equalsIgnoreCase("MixedMode")) {
            for (int i = 0; i < paymentDetailsList.size(); i++) {
                if (paymentDetailsList.get(i).getPaymentMode().equalsIgnoreCase("cash")) {
                    trnac = paymentDetailsList.get(i).getTrnac();
                }else if(paymentDetailsList.get(i).getPaymentMode().equalsIgnoreCase("Card")){
                    trnac = paymentDetailsList.get(i).getTrnac();
                }

            }
        } else {
            trnac = paymentDetailsList.get(0).getTrnac();
        }


        double tenderAmount = 0.0;
        for (int i = 0; i < paymentDetailsList.size(); i++) {
            tenderAmount += paymentDetailsList.get(i).getAmount();
        }


        SaveRequest saveRequest = new SaveRequest();

        Log.e("Mwarehouse", itemDetailsResult.getBatches().get(0).getWarehouse());
        Log.e("Mwarehouse", itemDetailsResult.getBatches().get(0).getWarehouse());


        saveRequest.setBillToAdd(customerAddress);
        saveRequest.setBillToTel(customerPhone);
        saveRequest.setBillTo(customerName);          //customer name field bata and add BillToAdd
        saveRequest.setCompanyId(GlobalValues.company.getCOMPANYID());
        saveRequest.setDeviceId(GlobalValues.deviceId);
        saveRequest.setGuid(GlobalValues.guid);
        saveRequest.setTender(tenderAmount);
        saveRequest.setTrnac(trnac);



        //Saving the mode for new or edit
        if (PreferenceUtils.retriveBillEditOption(MyApplication.getContext())) {
            saveRequest.setMode("Edit");
            saveRequest.setVchrno(GlobalValues.loadedBill);
            saveRequest.setChalanNo(GlobalValues.chalanNo);

        } else {
            saveRequest.setMode("New");
        }


        saveRequest.setMwarehouse(itemDetailsResult.getBatches().get(0).getWarehouse());
        saveRequest.setTrnMode(trnMode);
        saveRequest.setVoucherAbbName("Sales");
        saveRequest.setBillTender(paymentDetailsList);
        saveRequest.setTrnUser(GlobalValues.userInfo.getUNAME());
        List<ProductDetails> productDetailsList = new ArrayList<>();
        for (int i = 0; i < itemDetailsResult.getBatches().size(); i++) {
            ProductDetails productDetails = new ProductDetails();

            productDetails.setBatch(itemDetailsResult.getBatches().get(i).getBatch());
            productDetails.setWarehouse(itemDetailsResult.getBatches().get(i).getWarehouse());
            productDetails.setExpDate(itemDetailsResult.getBatches().get(i).getExpiry());
            productDetails.setAltIndDiscount(itemDetailsResult.getBatches().get(i).getAltIndDiscount());
            productDetails.setIndDiscount(itemDetailsResult.getBatches().get(i).getAltIndDiscount());
            productDetails.setIndDiscountRate(itemDetailsResult.getBatches().get(i).getIndividualDiscountRate());
            productDetails.setMcode(itemDetailsResult.getBatches().get(i).getMcode());
            productDetails.setMfgDate(itemDetailsResult.getBatches().get(i).getMfgDate());
            productDetails.setMrp(itemDetailsResult.getBatches().get(i).getMrp());
            productDetails.setpRate(itemDetailsResult.getBatches().get(i).getPrate());
            productDetails.setQuantity(Double.parseDouble(String.valueOf(itemDetailsResult.getBatches().get(i).getQuantity())));
            productDetails.setRate(Double.parseDouble(String.valueOf(itemDetailsResult.getBatches().get(i).getsRate())));
            productDetailsList.add(productDetails);
        }
        saveRequest.setProductDetailsList(productDetailsList);


        billSaveRepository.saveData(saveRequest);


    }

    public void getPaymentModes() {
        billSaveRepository.getPaymentModes();

    }

    public void filterPaymentMode() {
        List<PaymentModeResultItem> filteredList = new ArrayList<>();


        for (int i = 0; i < paymentModeResponse.getValue().size(); i++) {
            if (paymentModeResponse.getValue().get(i).getMODE().equalsIgnoreCase(GlobalValues.selected_payment_mode)) {
                filteredList.add(paymentModeResponse.getValue().get(i));
            }
        }
        filteredMutableList.postValue(filteredList);
    }

    //-------------------------------------------------------------------------------------------------------//
}
