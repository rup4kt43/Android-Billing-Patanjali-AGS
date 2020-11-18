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
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.items_details_pojo.ItemDetailsResponse;
import com.example.androidbilling.sales_menu_components.salesmenu.model.pojo.ItemListResponse;
import com.example.androidbilling.salse_return_component.pojo.SalesReturnBillLoadResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiGateway {

    //End points
    String login = "login";
    String getItemList = "getItemsList";
    String getItemDetails = "getItemDetails";
    String saveBill = "saveBillFromMobile";
    String getCustomerList = "getCustomerList";
    String getBill = "getAndroidSalesBill";
    String getPaymentModes = "gettransactionmodes";
    String getDashboardData = "dashboard";
    String getSalesReturnBIll = "getAndroidSalesBillStockForReturn";

    String recalculateBill = "recalculatebill";





    //-----------Login-----------//
    @POST(login)
    Call<LoginResponse> validateLogin(@Body LoginRequestDTO loginRequestDTO);


    //-----------Items----------//
    @GET(getItemList)
    Call<ItemListResponse> getItemList(@Query("companyId") String companyId);

    @GET(getItemDetails)
    Call<ItemDetailsResponse> getItemDetails(@Query("companyId") String companyId , @Query("mcode") String mcode, @Query("division") String division);



    //-----------Bill-----------//
    @POST(saveBill)
    Call<SaveBillResponse> saveBilling(@Body SaveRequest saveRequest);

    @GET(getCustomerList)
    Call<GetCustomerResponse> getCustomer (@Query("companyId") String companyId);

    @GET(getBill)
    Call<GetBillResponse> getBill (@Query("billno") String billNumber, @Query("companyId") String companyId);

    @GET(getPaymentModes)
    Call<PaymentModeResponse> getPaymentModes(@Query("companyId") String companyId);

    @GET(getSalesReturnBIll)
    Call<SalesReturnBillLoadResponse> getSalesReturnBill(@Query("companyId") String companyId, @Query("billno") String billNo);



    //------------------------Dashboard Data----------------------//

    @GET(getDashboardData)
    Call<DashboardResponse> getDashboardData ( @Query("companyId") String companyId, @Query("deviceid") String deviceId);

    @POST(recalculateBill)
    Call<CalculationResponse> recalculateData(@Body SaveRequest saveRequest);




}
