package com.example.androidbilling.network;


import com.example.androidbilling.bill_load.pojo.GetBillResponse;
import com.example.androidbilling.billing_components.bill_save.dto.SaveRequest;
import com.example.androidbilling.billing_components.bill_save.pojo.SaveBillResponse;
import com.example.androidbilling.billing_components.bill_save.pojo.paymentmode.PaymentModeResponse;
import com.example.androidbilling.billing_components.billing_preview.pojo.GetCustomerResponse;
import com.example.androidbilling.billing_components.billing_preview.pojo.calculation_pojo.CalculationResponse;
import com.example.androidbilling.dashboard.model.pojo.DashboardResponse;
import com.example.androidbilling.login.model.dto.LoginRequestDTO;
import com.example.androidbilling.login.model.pojo.LoginResponse;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListResponse;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResponse;
import com.example.androidbilling.salse_return_component.pojo.SalesReturnBillLoadResponse;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static ApiClient apiClient;
    private static ApiGateway apiGateway;

    public static final String BASE_URL = "http://app.sahakari.patanjaliayurved.org:8030/androidBilling/api/";


    public ApiClient() {



        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("api-key", GlobalValues.apikey)

                        .build();
                return chain.proceed(request);
            }
        });
        builder.followRedirects(false);
        OkHttpClient client = builder.build();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())

                .client(client)
                .build();
        apiGateway = retrofit.create(ApiGateway.class);


    }

    public static ApiClient getInstance() {

        if (apiClient == null) {
            apiClient = new ApiClient();

        }

        return new ApiClient();
    }


    //---------------Login----------------------//
    public Call<LoginResponse> getKot(LoginRequestDTO loginRequestDTO) {
        return apiGateway.validateLogin(loginRequestDTO);
    }

    //---------------Item---------------------..
    public Call<ItemListResponse> getItemList() {
        return apiGateway.getItemList(GlobalValues.company.getCOMPANYID());

    }

    public Call<ItemDetailsResponse> getItemDetails(String mcode) {             //getting item details for recycler view item click
        return apiGateway.getItemDetails(GlobalValues.company.getCOMPANYID(), mcode, "AAA");
    }

    //--------------Bill Save --------------//
    public Call<SaveBillResponse> saveBilling(SaveRequest saveRequest) {
        return apiGateway.saveBilling(saveRequest);
    }


    //-----------------------Customer Info---------------------//
    public Call<GetCustomerResponse> getCustomer(String companyId) {
        return apiGateway.getCustomer(companyId);
    }

    //------------------Get Saved Bill for bill preview-------------------------//
    public Call<GetBillResponse> getBill(String billNumber, String companyId){

        return apiGateway.getBill(billNumber,companyId);
    }

    //----------------Get Saved bill for bill return----------------------//
    public Call<SalesReturnBillLoadResponse> getSalesReturnBill(String companyId, String billNo){
        return apiGateway.getSalesReturnBill(billNo,companyId);
    }



    //-----------------Get Payment Modes-------------------------------/
    public Call<PaymentModeResponse> getPaymentModes(String companyId){
        return apiGateway.getPaymentModes(companyId);
    }


    //---------------Get dashboard data----------------------//

    public Call<DashboardResponse> getDashboardData(){
        GlobalFunctions.getDeviceId();
        return apiGateway.getDashboardData(GlobalValues.company.getCOMPANYID(), GlobalValues.deviceId);
    }


    public Call<CalculationResponse> recalculateData(SaveRequest saveRequest){
        return apiGateway.recalculateData(saveRequest);
    }


}
