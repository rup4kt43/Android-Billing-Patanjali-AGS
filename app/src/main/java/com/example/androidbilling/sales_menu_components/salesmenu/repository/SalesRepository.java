package com.example.androidbilling.sales_menu_components.salesmenu.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.androidbilling.bill_load.pojo.GetBillResponse;
import com.example.androidbilling.bill_load.pojo.GetBillResult;
import com.example.androidbilling.network.ApiClient;
import com.example.androidbilling.sales_menu_components.salesmenu.adapter.ItemListAdapter;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListResponse;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResponse;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResult;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesRepository {
    private static SalesRepository instance;
    private MutableLiveData<String> errorMessageMutable = new MutableLiveData<>();
    private SalesDatabaseRepository salesDatabaseRepository;

    private MutableLiveData<ItemDetailsResult> itemDetailsResultMutableLiveData;

    private MutableLiveData<String> saveVerticalIntoDatabaseResponse;

    private MutableLiveData<GetBillResult> billItems;

    private SalesRepository() {
        saveVerticalIntoDatabaseResponse = new MutableLiveData<>();
        itemDetailsResultMutableLiveData = new MutableLiveData<>();
        billItems = new MutableLiveData<>();
        salesDatabaseRepository = SalesDatabaseRepository.getInstance();
    }

    public static SalesRepository getInstance() {
        if (instance == null) {
            instance = new SalesRepository();

        }
        return instance;
    }

    public static void clearSalesRepositoryInstanc() {
        instance = null;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessageMutable;
    }


    //-------------------Item List---------------------------------------//
    public LiveData<String> downloadVertical() {
        return saveVerticalIntoDatabaseResponse;
    }

    public LiveData<GetBillResult> fetchBillItems() {
        return billItems;
    }

    //------------------Item Details List-------------------------------//
    public LiveData<ItemDetailsResult> fetchItemDetails() {
        return itemDetailsResultMutableLiveData;
    }

    public void getItemList() {
        ApiClient.getInstance().getItemList().enqueue(new Callback<ItemListResponse>() {
            @Override
            public void onResponse(@NonNull Call<ItemListResponse> call, @NonNull Response<ItemListResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    String saveDataResult = salesDatabaseRepository.insertDataIntoDatabase(response.body().getItemListResult().getCategory(), response.body().getItemListResult().getItemList());

                    saveVerticalIntoDatabaseResponse.postValue(saveDataResult);


                } else if (response.code() == 400) {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                            errorMessageMutable.postValue(errorMessage);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMessageMutable.setValue("Unknown error");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ItemListResponse> call, @NonNull Throwable t) {
                errorMessageMutable.setValue(t.toString());
            }
        });
    }

    public void getItemDetails(String mcode) {
        ApiClient.getInstance().getItemDetails(mcode).enqueue(new Callback<ItemDetailsResponse>() {
            @Override
            public void onResponse(Call<ItemDetailsResponse> call, Response<ItemDetailsResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getCode().matches("200")) {
                        assert response.body() != null;
                        ItemDetailsResult itemDetailsResult = response.body().getItemDetailsResult();
                        itemDetailsResultMutableLiveData.postValue(itemDetailsResult);
                    } else if (response.body().getCode().matches("201")) {
                        errorMessageMutable.postValue(response.body().getMessage());
                    }
                    ItemListAdapter.isItemClicked = false;


                } else if (response.code() == 400) {
                    ItemListAdapter.isItemClicked = false;
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = GlobalFunctions.getApiErrorMessage(response.errorBody().string());
                            errorMessageMutable.postValue(errorMessage);

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    errorMessageMutable.setValue("Unknown error");
                }
            }

            @Override
            public void onFailure(Call<ItemDetailsResponse> call, Throwable t) {
                errorMessageMutable.setValue(t.toString());
            }
        });

    }

    public void loadBill(String billNo) {
        ApiClient.getInstance().getBill(billNo, GlobalValues.company.getCOMPANYID()).enqueue(new Callback<GetBillResponse>() {
            @Override
            public void onResponse(Call<GetBillResponse> call, Response<GetBillResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getCode() == 200) {
                        if(response.body().getGetBillResult().getStatus()==1){
                            //if status is 1 the bill is already cancelled!!
                            errorMessageMutable.postValue(String.valueOf(1));
                        }else{
                            GlobalValues.chalanNo = response.body().getGetBillResult().getChalanno();
                            GlobalValues.loadedBill = response.body().getGetBillResult().getVchrno();
                            billItems.postValue(response.body().getGetBillResult());
                        }

                    }

                }

            }

            @Override
            public void onFailure(Call<GetBillResponse> call, Throwable t) {

            }
        });
    }


    public void clearBillEditData() {
        billItems.postValue(null);
    }
}
