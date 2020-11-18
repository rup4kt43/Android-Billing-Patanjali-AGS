package com.example.androidbilling.utilities.global;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbilling.R;


public class SweetToast {

    // initializations
    private static View view, myView;
    private static LayoutInflater layoutInflater;
    private static Toast toast;

    /** For Default Toast */
    // for default short toast
    public static void defaultShort(Context context, String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
    // for default long toast
    public static void defaultLong(Context context, String string){
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    /** For SUCCESS Toast */
   /* public static void success(Context context, String string){
        myView = inflateMyLayout(context);
        setBackgroundLayout(R.drawable.round_shape_success);
        setToastText(string, Color.WHITE);
        setToastIcon(R.drawable.ic_done);
        toast = new Toast(context);
        toast.setView(myView);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
*/
    /** For INFO Toast */
    public static void info(Context context, String string){
        myView = inflateMyLayout(context);
        setBackgroundLayout(R.drawable.toast_background);
        setToastText(string, Color.WHITE);
        setToastIcon(R.drawable.ic_info_outline);
        toast = new Toast(context);
        toast.setView(myView);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    /** For WARNING Toast */
//    public static void warning(Context context, String string){
//        myView = inflateMyLayout(context);
//        setBackgroundLayout(R.drawable.round_shape_warning);
//        setToastText(string, Color.WHITE);
//        setToastIcon(R.drawable.ic_info);
//        toast = new Toast(context);
//        toast.setView(myView);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.show();
//    }

    /** For ERROR Toast */
 /*   public static void error(Context context, String string){
        myView = inflateMyLayout(context);
        setBackgroundLayout(R.drawable.round_shape_error);
        setToastText(string, Color.WHITE);
        setToastIcon(R.drawable.ic_close);
        toast = new Toast(context);
        toast.setView(myView);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }*/


    // inflate for separate layout
    private static View inflateMyLayout(Context context) {
        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.layout_error_toast, null);
        return view;
    }
    private static void setBackgroundLayout(int resId){
        LinearLayout layout = view.findViewById(R.id.toastLay);
        layout.setBackgroundResource(resId);
    }
    private static void setToastText(String string, int textColor) {
        TextView toastTitle = view.findViewById(R.id.toastTitle);
        toastTitle.setText(string);
        toastTitle.setTextColor(textColor);
    }
    private static ImageView setToastIcon(int resId) {
        ImageView toastIcon = view.findViewById(R.id.toastIcon);
        toastIcon.setImageResource(resId);
        return toastIcon;
    }

}
