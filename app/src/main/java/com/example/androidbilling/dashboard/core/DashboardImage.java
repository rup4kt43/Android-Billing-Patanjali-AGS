package com.example.androidbilling.dashboard.core;

import com.example.androidbilling.R;

import java.util.HashMap;

public class DashboardImage {

    public static HashMap<String,String > imageHashMap;

    public static void setImageHashMap(){
        imageHashMap = new HashMap<>();
        imageHashMap.put("SALES INVOICE",String.valueOf(R.drawable.ic_sales));
        imageHashMap.put("SALES RETURN", String.valueOf(R.drawable.ic_dashboard_sales_return));
    }
}
