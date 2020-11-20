package com.example.androidbilling.sales_menu_components.salesmenu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.sales_menu_components.categorySelection.dto.VerticalDTO;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListItem;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.BatchesItem;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.sales_menu_components.salesmenu.repository.SalesDatabaseRepository;
import com.example.androidbilling.sales_menu_components.salesmenu.repository.SalesRepository;
import com.example.androidbilling.utilities.global.SweetToast;
import com.example.androidbilling.utilities.scanner.MyApplication;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class SalesViewModel extends ViewModel {

    private List<BatchesItem> batchList;
    int matchPos = -1;

    //For clearing search view data
    private MutableLiveData<Boolean> searchViewClearStatus ;

    //Repositories
    private SalesRepository salesRepository;
    private SalesDatabaseRepository salesDatabaseRepository;

    //Vertical Live Data
    private LiveData<String> saveVertical;
    private LiveData<List<VerticalDTO>> verticalData;

    //ItemList Live Data
    private LiveData<List<ItemListItem>> itemList;

    //ItemDetails Data
    private LiveData<ItemDetailsResult> itemDetailsResultLiveData;

    //Cart item
    private MutableLiveData<List<BatchesItem>> batchListMutableLiveData;

    // Mutable object for billPreviewItemList
    private MutableLiveData<List<BatchesItem>> billPreivewItemList;

    //Error Message Live Data
    private MutableLiveData<String> errorMessage;

    //Bill Load Items
    private LiveData<GetBillResult> billItems;

    //Scanned item mcode
    private LiveData<List<String>> liveMcodeList;


    public SalesViewModel() {
        batchList = new ArrayList<>();


        searchViewClearStatus = new MutableLiveData<>();
        searchViewClearStatus.postValue(false);

        //Repository
        salesRepository = SalesRepository.getInstance();
        salesDatabaseRepository = SalesDatabaseRepository.getInstance();


        //Vertical
        saveVertical = salesRepository.downloadVertical();
        verticalData = salesDatabaseRepository.fetchVerticalList();
        errorMessage = salesRepository.getErrorMessage();

        //itemlist

        itemList = salesDatabaseRepository.fetchItemList();
        itemDetailsResultLiveData = salesRepository.fetchItemDetails();

        //Cart item
        batchListMutableLiveData = new MutableLiveData<>();

        //Billing preview item list
        billPreivewItemList = new MutableLiveData<>();

        //Bill Load items
        billItems = salesRepository.fetchBillItems();

        //Getting the mcode of the scanned item
        liveMcodeList = salesDatabaseRepository.fetchScannedMcodeList();


    }

    //------------------------------method for returning data or message---------------------//
    public LiveData<List<VerticalDTO>> fetchVertical() {
        return verticalData;
    }

    public LiveData<String> fetchDownloadResponse() {
        return saveVertical;
    }

    public LiveData<String> fetchErrorMessage() {
        return errorMessage;
    }

    public LiveData<List<ItemListItem>> fetchAllItemList() {
        return itemList;
    }

    public LiveData<ItemDetailsResult> fetchItemDetails() {
        return itemDetailsResultLiveData;
    }

    public LiveData<List<BatchesItem>> fetchBatchSelectedItemToCart() {
        return batchListMutableLiveData;
    }

    public LiveData<List<BatchesItem>> fetchItemForBillPreview() {
        return billPreivewItemList;
    }

    public LiveData<GetBillResult> fetchBillItemList() {
        return billItems;
    }

    public LiveData<List<String>> fetchItemScannedMcodeList() {
        return liveMcodeList;
    }

    //------------------------------------------------------------------------------//

    //---------------------Normal methods for calling the method of repository----------------------//

    public void getItemList() {
        salesRepository.getItemList();
    }

    public void getAllItemFromDatabase() {
        salesDatabaseRepository.getAllItems();
        ;
    }


    public void retriveVertical() {
        salesDatabaseRepository.getVertical();
    }


    public void retriveItemFromCategory(String category) {
        salesDatabaseRepository.retriveItemFromCategory(category);
    }


    //retriving details for recycler view item
    public void retriveItemDetails(String mcode) {
        salesRepository.getItemDetails(mcode);
    }


    public void addItemToCart(BatchesItem batchesItem) {
        if (PreferenceUtils.retriveBillEditOption(MyApplication.getContext())) {
            if (!GlobalValues.isEditBillLoaded) {
                GlobalFunctions.showToast("Sorry please load the bill first in order to edit the bill!!");
                return;
            }
        }
        if (batchList.size() == 0) {
            SweetToast.info(MyApplication.getContext(),batchesItem.getDesca() + " added successfully.");
            batchList.add(batchesItem);

        } else {
            for (int i = 0; i < batchList.size(); i++) {
                if (batchList.get(i).getBatch().matches(batchesItem.getBatch()) && batchList.get(i).getMcode().matches(batchesItem.getMcode())) {
                    matchPos = i;
                }
            }

            if (matchPos != -1) {
                batchList.get(matchPos).setQuantity(batchList.get(matchPos).getQuantity() + 1);
                matchPos = -1;
                SweetToast.info(MyApplication.getContext(),batchesItem.getDesca() + " quantity increased.");


            } else {
                SweetToast.info(MyApplication.getContext(),batchesItem.getDesca() + " added successfully.");
                batchList.add(batchesItem);

            }
        }

        batchListMutableLiveData.postValue(batchList);
    }

    public void createFinalDataForBillPreview(List<BatchesItem> itemList) {
        billPreivewItemList.postValue(itemList);
    }

    public void loadBill(String bill_type, String bill_number, String division, String phiscalId) {
        batchList.clear();
        salesRepository.clearBillEditData();

        String billNo = bill_type + bill_number + division + phiscalId;

        if (billNo.isEmpty()) {
            errorMessage.postValue("Sorry! Please enter the bill number to proceed!!");
        } else {
            salesRepository.loadBill(billNo);


        }

    }


    public void cancelEdit() {
        batchList.clear();
        salesRepository.clearBillEditData();
        batchListMutableLiveData.postValue(batchList);
    }

    public void clearList() {
        batchList.clear();
    }



    public void searchItemWithBarcode(String result) {

        salesDatabaseRepository.searchItemWithBarcode(result);
    }

    public void clearMcodeList(){
        salesDatabaseRepository.clearInstance();
    }


    public void updateSearchStatus(boolean b) {
        searchViewClearStatus.postValue(b);
    }

}
