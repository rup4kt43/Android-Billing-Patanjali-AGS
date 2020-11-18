package com.example.androidbilling.billing_components.billing_preview.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.billing_components.bill_save.dto.SaveRequest;
import com.example.androidbilling.billing_components.billing_preview.dto.BillingCalculationDTO;
import com.example.androidbilling.billing_components.billing_preview.pojo.GetCustomerResultItem;
import com.example.androidbilling.billing_components.billing_preview.pojo.calculation_pojo.CalculationResponse;
import com.example.androidbilling.billing_components.billing_preview.repository.BillingRepository;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.utilities.global.DecimalRoundOffClass;
import com.example.androidbilling.utilities.global.GlobalValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BillingViewModel extends ViewModel {

    BillingRepository billingRepository;

    private MutableLiveData<List<GetCustomerResultItem>> mutableCustomerDetails;
    private List<HashMap<String,String>> customerList;
    private MutableLiveData<List<HashMap<String,String>>> mutableCustomerList;
    private MutableLiveData<CalculationResponse> recalculatedData;


    private MutableLiveData<BillingCalculationDTO> billingCalculationDTOMutableLiveData;


    public BillingViewModel() {
        billingCalculationDTOMutableLiveData = new MutableLiveData<>();
        billingRepository = BillingRepository.getInstance();
        mutableCustomerDetails = billingRepository.getCustomerDetails();
        mutableCustomerList = new MutableLiveData<>();
        customerList= new ArrayList<>();
        recalculatedData = billingRepository.getRecalculatedData();

    }


    //---------------Return Method------------------------------//
    public LiveData<BillingCalculationDTO> billingCalculationDTOLiveData() {
        return billingCalculationDTOMutableLiveData;
    }

    public LiveData<List<GetCustomerResultItem>>  fetchCustomerDetails(){
        return mutableCustomerDetails;
    }

    public LiveData<CalculationResponse> fetchRecalculatedData(){
        return recalculatedData;
    }





    //-------------------Functional Methods------------------------------//
    public void calculateBill(ItemDetailsResult itemDetailsResult) {
        double totalGST = 0.0;
        double totalNetAmount = 0.0;
        double totalGrossAmount = 0.0;
        double totalIndividualDiscount = 0.0;
        double itemRate;
        double disWithoutGst;






        double gst, netAmount, grossAmount, individualAmount,taxableAmount=0.00;


        for (int i = 0; i < itemDetailsResult.getBatches().size(); i++) {



            itemRate = (itemDetailsResult.getBatches().get(i).getMrp() / (1+(itemDetailsResult.getBatches().get(i).getGst()/100)));
            Log.e("itemRate",itemRate+"");
            Log.e("itemRate",itemRate+"");

            individualAmount = itemDetailsResult.getBatches().get(i).getAltIndDiscount();           //individual discount
            disWithoutGst = individualAmount - (individualAmount*itemDetailsResult.getBatches().get(i).getGst()/100);
            taxableAmount = itemRate * itemDetailsResult.getBatches().get(i).getQuantity()-disWithoutGst;
            gst = taxableAmount * (itemDetailsResult.getBatches().get(i).getGst()/100);
          //  grossAmount = taxableAmount-gst;
            netAmount = taxableAmount+gst;

            totalIndividualDiscount += disWithoutGst;

            totalGST += gst;
            totalNetAmount += netAmount;
        }


        BillingCalculationDTO billingCalculationDTO = new BillingCalculationDTO(
                Double.parseDouble(DecimalRoundOffClass.roundOff(taxableAmount)),
                Double.parseDouble(DecimalRoundOffClass.roundOff(totalNetAmount)),
                Double.parseDouble(DecimalRoundOffClass.roundOff(totalIndividualDiscount)),
                Double.parseDouble(DecimalRoundOffClass.roundOff(totalGST)));
        billingCalculationDTOMutableLiveData.postValue(billingCalculationDTO);
    }

    public void getCustomerList() {
        billingRepository.retriveCustomerList(GlobalValues.company.getCOMPANYID());

    }

    public void recalculateData(SaveRequest saveRequest){
        billingRepository.recalculateData(saveRequest);
    }
}
