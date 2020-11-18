package com.example.androidbilling.utilities.global;

import com.example.androidbilling.login.model.pojo.Company;
import com.example.androidbilling.login.model.pojo.UserInfo;

public class GlobalValues {

    public static String apikey = "i0gtdrg8mVnLWwsAawjYr4Rx-Af50DWsTh5$uj$$p";
    public static int SAMRIDHI_MODE = 1;
    public static int DEFAULT_MODE = 0;


    //Login Credentials
    public static UserInfo userInfo;
    public static Company company;

    public static String guid;
    public static String deviceId;


    //Global values ofr cateogory selection
    public static int SELECT_ALL_CATEGORY = -2;
    public static int categorySelectedPosition = SELECT_ALL_CATEGORY;

    public static String selectedCategory;

    //Global values for payment mode
    public static int PAYMENT_MODE = 1;
    public static int PAYMENT_MODE_NAME = 2;
    public static int PAYMENT_ACID = 3;

    public static int Payment_options = 1;

    public static int mode_selected_position= 0;
    public static String selected_payment_mode = "CASH";


    //List of payment modes

    public static String[] paymentList = new String[]{"Cash","SamridhiCard","Card","Wallet"};

    //mode for validation for samridhi card
    public static int mode_validation = DEFAULT_MODE;


    public static boolean isOrderProceed = false;              //tab bar position


    public static String username = "";
    public static String password = "";


    public static String loadedBill;
    public static String chalanNo="";
    public static boolean isEditBillLoaded=false;

    public static String scannedBarcode;
}
