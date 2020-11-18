package com.example.androidbilling.utilities.Printing;

import android.app.Activity;

import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.printing.PrintWrapper;

public class PrintActivity {

    private final Activity context;
    private final String data;
    PrintWrapper mprintWrapper;

    public PrintActivity(Activity context , String data){
        this.context = context;
        this.data= data;
    }


    public void printData() {
        mprintWrapper = new PrintWrapper(context);

        if(!data.isEmpty()){
            mprintWrapper.printText(context,data);
        }else{
            GlobalFunctions.showToast("Sorry No Bill to print.");
        }


    }


}
