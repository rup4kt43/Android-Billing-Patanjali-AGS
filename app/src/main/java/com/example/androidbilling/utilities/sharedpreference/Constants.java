package com.example.androidbilling.utilities.sharedpreference;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Constants {





    //----Shared Pref Name----//
    public static String LOGIN_PREF = "login_details";
    public static String DATA_DOWNLOAD_STATUS = "data_download_status";
    public static String SETTING_PREF = "setting_preference";
    public static String INITIAL_COMPANY_ID = "initialCompanyId";
    public static String REMEMBER_LOGIN = "remember_login";



    //--------------Login remember pref-----------------//
    public static String PASSWORD = "password";
    public static String KEEP_ME_LOGGED_IN = "KeepMeLoggedIn";


    //------------Login SP KEYS------------------//
    //user keys
    public static String USERNAME = "username";     //used in login remember
    public static String ROLE = "role";

    //company keys
    public static String COMPANYID = "companyId";
    public static String COMPANYNAME = "companyName";
    public static  String INITIAL = "initial";
    public static String PHISCALID = "phiscalId";


    //-----------Data Download Status----------//
    public static String ISDATADOWNLOADED = "isDataDownloaded";

    //------------Setting SP keys--------------------//
    public static String DO_PRINT = "do_print";
    public static String BILL_EDIT  = "bill_edit";
    public static String LOCK_EDIT_MODE= "lock_edit_mode";
    public static String ALLOW_ITEM_ADD= "add_item_to_bill_edit";
    public static String BILL_SETTING_CHANGE = "bill_setting_change";
    public static String ENABLE_SAMRIDHI_CARD_PAYMENT = "enable_samridhi_card_payment";




}
