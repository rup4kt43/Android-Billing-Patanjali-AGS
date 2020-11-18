package com.example.androidbilling.sales_menu_components.salesmenu.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.database.DatabaseHelper;
import com.example.androidbilling.sales_menu_components.categorySelection.dto.VerticalDTO;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListItem;
import com.example.androidbilling.utilities.scanner.MyApplication;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

import java.util.List;

public class SalesDatabaseRepository {
    private DatabaseHelper databaseHelper;
    private MutableLiveData<List<VerticalDTO>> verticalList;
    private MutableLiveData<List<ItemListItem>> itemList;
    private MutableLiveData<List<String>> mutableMcodeList;

    private static SalesDatabaseRepository databaseRepoInstance;

    private SalesDatabaseRepository() {
        databaseHelper = new DatabaseHelper(MyApplication.getContext());
        itemList = new MutableLiveData<>();
        verticalList = new MutableLiveData<>();
        mutableMcodeList = new MutableLiveData<>();

    }

    public static SalesDatabaseRepository getInstance() {
        if (databaseRepoInstance == null) {
            databaseRepoInstance = new SalesDatabaseRepository();
        }
        return databaseRepoInstance;
    }


    //return methods
    public MutableLiveData<List<VerticalDTO>> fetchVerticalList() {
        return verticalList;
    }

    public MutableLiveData<List<ItemListItem>> fetchItemList() {
        return itemList;
    }

    public MutableLiveData<List<String>> fetchScannedMcodeList() {
        return mutableMcodeList;
    }

    public String insertDataIntoDatabase(List<String> category, List<ItemListItem> itemList) {

        long result = databaseHelper.insertVertical(category);
        long result1 = databaseHelper.insertMenuItem(itemList);

        if (result == -1 || result1 == -1) {
            PreferenceUtils.saveDataDownloadStatus(false, MyApplication.getContext());
            return "Failed to save the category data into database";
        } else {
            PreferenceUtils.saveDataDownloadStatus(true, MyApplication.getContext());
            return "Category data downloaded and saved successfully";
        }


    }

    public void getVertical() {
        List<VerticalDTO> verticalData = databaseHelper.retriveVertical();
        verticalList.setValue(verticalData);


    }


    public void getAllItems() {

        List<ItemListItem> list = databaseHelper.retriveAllItems();
        itemList.setValue(list);
    }

    public void retriveItemFromCategory(String category) {

        List<ItemListItem> list = databaseHelper.retriveItemFromCategory(category);
        itemList.setValue(list);
    }

    public void updateCategorySataus(String categoryName, int status) {
        databaseHelper.updateCategoryStatus(categoryName, status);
    }

    public void searchItemWithBarcode(String result) {
        List<String> mcodeList = databaseHelper.searchItemWithBarcode(result);

        mutableMcodeList.postValue(mcodeList);
    }

    public void clearInstance() {
        databaseRepoInstance = null;
    }
}
