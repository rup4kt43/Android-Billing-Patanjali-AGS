package com.example.androidbilling.salse_return_component.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.bill_load.pojo.ProdListItem;
import com.example.androidbilling.billing_components.bill_save.dto.ProductDetails;
import com.example.androidbilling.billing_components.bill_save.dto.SaveRequest;
import com.example.androidbilling.salse_return_component.pojo.SalesReturnBillLoadResultItem;
import com.example.androidbilling.salse_return_component.repository.SalesReturnRepository;
import com.example.androidbilling.salse_return_component.view.SalesReturnView;
import com.example.androidbilling.utilities.global.GlobalValues;

import java.util.ArrayList;
import java.util.List;

public class SalesReturnViewModel extends ViewModel {
    private MutableLiveData<String> errorMessage;
    private SalesReturnRepository salesReturnRepository;
    private MutableLiveData<List<SalesReturnBillLoadResultItem>> getBillResultMutableLiveData;
    private List<ProdListItem> productList;

    private MutableLiveData<GetBillResult> salesReturnBillList;

    //Sales Return Response message
    private MutableLiveData<String> getResponseMessage;


    public SalesReturnViewModel() {
        productList = new ArrayList<>();

        salesReturnRepository = SalesReturnRepository.getInstance();
        getBillResultMutableLiveData = salesReturnRepository.returnSalesReturnBill();
        errorMessage = salesReturnRepository.getResponseMessage();
        salesReturnBillList = new MutableLiveData<>();
        getResponseMessage = salesReturnRepository.getResponseMessage();


    }


    //---------------Live Data for fetching---------------//

    public LiveData<String> fetchErrorMessage() {
        return getResponseMessage;
    }

    public LiveData<List<SalesReturnBillLoadResultItem>> fetchBillResult() {
        return getBillResultMutableLiveData;
    }

    public LiveData<GetBillResult> fetchReturnItemList() {
        return salesReturnBillList;
    }

    public LiveData<String> fetchResponseMessage() {
        return getResponseMessage;
    }


    //----------Functional Methods-----------------//
    public void loadBill(String bill_type, String bill_number, String division, String phiscalId) {
        clearList();
        clearBillData();
        String billNo = bill_type + bill_number + division + phiscalId;
        SalesReturnView.billNumber = billNo;
        if (bill_number.isEmpty()) {
            errorMessage.postValue("Sorry Bill Number cannot be empty! Please fill the bill number and proceed");

        } else {

            salesReturnRepository.getSalesReturnBill(billNo, GlobalValues.company.getCOMPANYID());
        }
    }

    public void addItemToResultList(ProdListItem prodListItem) {


        GetBillResult getBillResult = new GetBillResult();
        getBillResult.setBillto(getBillResult.getBillto());
        getBillResult.setBilltoadd(getBillResult.getBilltoadd());
        getBillResult.setBilltomob(getBillResult.getBilltomob());
        getBillResult.setTrnac(getBillResult.getTrnac());
        productList.add(prodListItem);
        getBillResult.setProdList(productList);
        salesReturnBillList.postValue(getBillResult);


    }

    public void clearList() {
        productList.clear();
    }

    public void clearBillData() {
        productList.clear();
        salesReturnBillList.postValue(null);
    }

    public void saveBill(GetBillResult getBillResult, String trnMode) {




        SaveRequest saveRequest = new SaveRequest();
        saveRequest.setCompanyId(GlobalValues.company.getCOMPANYID());
        saveRequest.setDeviceId(GlobalValues.deviceId);
        saveRequest.setGuid(GlobalValues.guid);
        saveRequest.setMode("New");





        saveRequest.setParac(getBillResult.getProdList().get(0).getParAc());


        saveRequest.setBillTo(getBillResult.getBillto());
        saveRequest.setBillToAdd(getBillResult.getBilltoadd());
        saveRequest.setTrnMode(getBillResult.getProdList().get(0).getTrnMode());
        saveRequest.setReferenceBillNumber(SalesReturnView.billNumber);

        saveRequest.setTrnac(getBillResult.getProdList().get(0).getTrnac());
        saveRequest.setVoucherAbbName("Sales Return");
        saveRequest.setReferenceBillNumber(SalesReturnView.billNumber);
        saveRequest.setTrnUser(GlobalValues.userInfo.getUNAME());
        List<ProductDetails> productDetailsList = new ArrayList<>();
        for (int i = 0; i < getBillResult.getProdList().size(); i++) {
            ProductDetails productDetails = new ProductDetails();
            productDetails.setGstRate(getBillResult.getProdList().get(i).getGstrate());
            productDetails.setTotalIndDiscount(getBillResult.getProdList().get(i).getTotalIntDiscount());
            productDetails.setOriginalQty(getBillResult.getProdList().get(i).getOriginalQty());
            productDetails.setAltIndDiscount(getBillResult.getProdList().get(i).getAltIndDiscount());
            productDetails.setBatch(getBillResult.getProdList().get(i).getBatch());
            productDetails.setWarehouse(getBillResult.getProdList().get(i).getWarehouse());
            productDetails.setExpDate(getBillResult.getProdList().get(i).getExpdate());
            productDetails.setIndDiscount(getBillResult.getProdList().get(i).getInddiscount());
            productDetails.setIndDiscountRate(getBillResult.getProdList().get(i).getInddiscountrate());
            productDetails.setMcode(getBillResult.getProdList().get(i).getMcode());
            productDetails.setMfgDate(getBillResult.getProdList().get(i).getMfgdate());
            productDetails.setMrp(getBillResult.getProdList().get(i).getMrp());
            productDetails.setWarehouse(getBillResult.getProdList().get(i).getWarehouse());
            productDetails.setpRate(getBillResult.getProdList().get(i).getPrate());

            productDetails.setQuantity(getBillResult.getProdList().get(i).getQuantity());
            productDetails.setRate(getBillResult.getProdList().get(i).getRate());

            productDetailsList.add(productDetails);
        }
        saveRequest.setProductDetailsList(productDetailsList);

        salesReturnRepository.saveReturnData(saveRequest);


    }


    public boolean checkIfItemExist(String mcode, String batch, String mfgDate, String expDate, double prate) {
        int matchPos = -1;
        if (salesReturnBillList.getValue() != null) {
            for (int i = 0; i < salesReturnBillList.getValue().getProdList().size(); i++) {
                if (salesReturnBillList.getValue().getProdList().get(i).getMcode().matches(mcode)
                        && salesReturnBillList.getValue().getProdList().get(i).getBatch().equalsIgnoreCase(batch)
                        && salesReturnBillList.getValue().getProdList().get(i).getMfgdate().equalsIgnoreCase(mfgDate)
                        && salesReturnBillList.getValue().getProdList().get(i).getExpdate().equalsIgnoreCase(expDate)
                        && salesReturnBillList.getValue().getProdList().get(i).getPrate() == prate
                ) {
                    matchPos = i;
                }
            }
        }

        if (matchPos != -1) {
            return true;
        } else {
            return false;
        }
    }
}
