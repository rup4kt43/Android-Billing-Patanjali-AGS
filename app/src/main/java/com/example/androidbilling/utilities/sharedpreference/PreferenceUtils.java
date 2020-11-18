package com.example.androidbilling.utilities.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.androidbilling.login.model.pojo.Company;
import com.example.androidbilling.login.model.pojo.LoginResponse;
import com.example.androidbilling.login.model.pojo.UserInfo;
import com.example.androidbilling.utilities.global.GlobalValues;

public class PreferenceUtils {
    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    public PreferenceUtils() {

    }


    //-------------------Initial Comapny id set------------------------//

    public static String saveCompanyId(String companyId, Context context) {
        prefs = context.getSharedPreferences(Constants.INITIAL_COMPANY_ID, Context.MODE_PRIVATE);
        editor = context.getSharedPreferences(Constants.INITIAL_COMPANY_ID, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.COMPANYID, companyId);
        editor.apply();
        return "Company Id has been set.";


    }

    public static String retriveInitialCompanyId(Context context) {
        prefs = context.getSharedPreferences(Constants.INITIAL_COMPANY_ID, Context.MODE_PRIVATE);
        String companyId = prefs.getString(Constants.COMPANYID, null);
        return companyId;

    }


    //------------Login Shared Pref---------------------------//


    public static void saveLoginCredentials(LoginResponse loginResponse, Context context) {
        editor = context.getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE).edit();
        //company details
        editor.putString(Constants.COMPANYNAME, loginResponse.getResult().getCompany().getCOMPANYNAME());
        editor.putString(Constants.COMPANYID, loginResponse.getResult().getCompany().getCOMPANYID());
        editor.putString(Constants.INITIAL, loginResponse.getResult().getCompany().getINITIAL());
        editor.putString(Constants.PHISCALID, loginResponse.getResult().getCompany().getPhiscalID());

        //user details
        editor.putString(Constants.USERNAME, loginResponse.getResult().getUserInfo().getUNAME());
        editor.putString(Constants.ROLE, loginResponse.getResult().getUserInfo().getROLE());
        editor.apply();

    }

    public static void retriveLoginCredentials(Context context) {
        prefs = context.getSharedPreferences(Constants.LOGIN_PREF, Context.MODE_PRIVATE);
        String username = prefs.getString(Constants.USERNAME, null);
        String role = prefs.getString(Constants.ROLE, null);

        String companyName = prefs.getString(Constants.COMPANYNAME, null);
        String companyId = prefs.getString(Constants.COMPANYID, null);
        String initial = prefs.getString(Constants.INITIAL, null);
        String phiscalId = prefs.getString(Constants.PHISCALID, null);

        GlobalValues.userInfo = new UserInfo(role, username);
        GlobalValues.company = new Company(initial, companyName, companyId, phiscalId);
    }


    public static void saveDataDownloadStatus(boolean status, Context context) {
        editor = context.getSharedPreferences(Constants.DATA_DOWNLOAD_STATUS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.ISDATADOWNLOADED, status);
        editor.apply();


    }

    public static boolean retriveDataDownloadStatus(Context context) {
        prefs = context.getSharedPreferences(Constants.DATA_DOWNLOAD_STATUS, Context.MODE_PRIVATE);
        return prefs.getBoolean(Constants.ISDATADOWNLOADED, false);
    }

    //----------------------Setting shared pref-----------------------//


    //Printing PREF
    public static void savePrintingDetails(Context context, boolean status) {
        editor = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.DO_PRINT, status);
        editor.apply();
    }

    public static boolean retrivePrintintDetails(Context context) {
        prefs = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE);
        return prefs.getBoolean(Constants.DO_PRINT, false);
    }


    //Bill Edit PREF
    public static void saveBillEditDetails(Context context, boolean status) {
        editor = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.BILL_EDIT, status);
        editor.apply();
    }


    public static boolean retriveBillEditOption(Context context) {
        prefs = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE);
        return prefs.getBoolean(Constants.BILL_EDIT, false);

    }

    public static void lockEditMode(Context context, boolean status) {
        editor = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.LOCK_EDIT_MODE, status);
        editor.apply();
    }

    public static boolean isEditLocked(Context context) {
        prefs = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE);
        return prefs.getBoolean(Constants.LOCK_EDIT_MODE, false);
    }

    //force to load bill before adding the item to the bill  -> edit mode option
    public static void isBillLoaded(Context context, boolean status) {
        editor = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.ALLOW_ITEM_ADD, status);
        editor.apply();
    }

    public static boolean isBillLoadedStatus(Context context) {
        prefs = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE);
        return prefs.getBoolean(Constants.ALLOW_ITEM_ADD, false);
    }

    public static void allowBillSettingChange(Context context, boolean status) {
        editor = context.getSharedPreferences(Constants.SETTING_PREF, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.BILL_SETTING_CHANGE, status);
        editor.apply();
    }



    //Login remember pref
    public static void updateKeepMeLoggedInStatus(Context context , boolean status){
        editor = context.getSharedPreferences(Constants.REMEMBER_LOGIN, Context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.KEEP_ME_LOGGED_IN, status);
        editor.apply();
    }

    public static boolean retriveKeepMeLoggedInStatus(Context context){
        prefs = context.getSharedPreferences(Constants.REMEMBER_LOGIN, Context.MODE_PRIVATE);
        return prefs.getBoolean(Constants.KEEP_ME_LOGGED_IN, false);
    }


    public static void saveAutoLoginDetails(Context context , String username,String password){
        editor = context.getSharedPreferences(Constants.REMEMBER_LOGIN, Context.MODE_PRIVATE).edit();
        editor.putString(Constants.USERNAME, username);
        editor.putString(Constants.PASSWORD, password);
        editor.apply();
    }

    public static void retriveAutoLoginDetails(Context context){
        prefs = context.getSharedPreferences(Constants.REMEMBER_LOGIN, Context.MODE_PRIVATE);
        GlobalValues.username = prefs.getString(Constants.USERNAME,"");
        GlobalValues.password = prefs.getString(Constants.PASSWORD,"");
    }




}
