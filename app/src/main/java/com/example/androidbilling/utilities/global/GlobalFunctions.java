package com.example.androidbilling.utilities.global;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.androidbilling.billing_components.bill_save.provider.BillSaveViewModelProvider;
import com.example.androidbilling.billing_components.bill_save.repository.BillSaveRepository;
import com.example.androidbilling.billing_components.billing_preview.provider.BillingViewModelProvider;
import com.example.androidbilling.sales_menu_components.salesmenu.provider.SalesMenuViewModelProvider;
import com.example.androidbilling.sales_menu_components.salesmenu.repository.SalesRepository;
import com.example.androidbilling.utilities.scanner.MyApplication;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class GlobalFunctions {
    public static ProgressDialog progressDialog;

    public static Snackbar snackbar;

    //--------------Method for handling api error message ------------------//

    public static String getApiErrorMessage(String error) {
        JSONObject jsonObject = null;
        String errorMessage = null;
        try {
            jsonObject = new JSONObject(error);
            errorMessage = jsonObject.getString("message");
            return errorMessage;
        } catch (JSONException e) {
            return e.getLocalizedMessage();
        }

    }

    //------------------------------------------------------------------------//


    //----------------------------ProgressBar------------------------------------//

    public static void showProgressDialog(String message, Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    public static void dismissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    //--------------------------------------------------------------------------//


    //-----------------------Methods for Popup Message----------------------------------------------------------//
    public static void showSnackBar(CoordinatorLayout coordinatorLayout, String message, int color) {
        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);



        View snackBarView = snackbar.getView();

        if (color != 0) {
            snackBarView.setBackgroundColor(color);
        }
        snackbar.show();
    }

    public static void showSnackBar(CoordinatorLayout coordinatorLayout, String message, int color, int length) {
        snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
        View snackBarView = snackbar.getView();



        if (color != 0) {
            snackBarView.setBackgroundColor(color);
        }
        snackbar.show();
    }

    public static void showToast(String message) {
        Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }



    //-------------------------------------------------------------------------------------------------------------//


    //----------Methods for clearing the singleton instances------//
    public static void clearAllSingleTonInstances() {
        SalesRepository.clearSalesRepositoryInstanc();
        SalesMenuViewModelProvider.clearSalesMenuProviderInstance();
        BillSaveViewModelProvider.clearSalesMenuProviderInstance();
        BillSaveRepository.clearSalesMenuProviderInstance();
        BillingViewModelProvider.clearSalesMenuProviderInstance();
        SalesMenuViewModelProvider.getInstance().clearMcodeList();

    }
    //------------------------------------------------------------//


    //-------------Functions related to ID generator-----------------//
    public static void getDeviceId() {
        GlobalValues.deviceId = Settings.Secure.getString(MyApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static void getGUID() {

        GlobalValues.guid = UUID.randomUUID().toString();
    }
    //---------------------------------------------------------------//



    //--------------Keyboard Hide----------------------//
    public static void hideKeyboard(Activity context) {
        View view = context.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    //-------------Keyboard show-----------------------//
    public static void showKeyboard(Activity context){

        InputMethodManager imm = (InputMethodManager)  context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

}
