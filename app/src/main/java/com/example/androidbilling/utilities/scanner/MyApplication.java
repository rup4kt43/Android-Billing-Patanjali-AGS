package com.example.androidbilling.utilities.scanner;

import android.app.Application;
import android.content.Context;

import com.example.androidbilling.utilities.global.GlobalFunctions;


public class MyApplication extends Application {
    public static Context context;




    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        GlobalFunctions.getDeviceId();
        GlobalFunctions.getGUID();

    }

    public static Context getContext() {
        return context;
    }


}
