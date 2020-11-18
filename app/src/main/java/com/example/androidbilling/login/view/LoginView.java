package com.example.androidbilling.login.view;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidbilling.R;
import com.example.androidbilling.dashboard.view.DashboardView;
import com.example.androidbilling.databinding.ActivityLoginViewBinding;
import com.example.androidbilling.login.viewModel.LoginViewModel;
import com.example.androidbilling.login.viewModel.LoginViewModelFactory;
import com.example.androidbilling.sales_menu_components.salesmenu.view.SalesMenuView;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

public class LoginView extends AppCompatActivity {
    private ActivityLoginViewBinding mainBinding;
    private ProgressDialog progressDialog;
    private LoginViewModel viewModel;
    private int alertColor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        alertColor = getResources().getColor(R.color.alert);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing In. Please Wait...");
        progressDialog.setCancelable(false);

        String companyId = PreferenceUtils.retriveInitialCompanyId(this);
        if (companyId == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Company Id is missing. Would you like to set it now?")
                    .setCancelable(false)
                    .setPositiveButton("No", (dialog, which) -> dialog.dismiss())
                    .setNegativeButton("Yes", (dialog, which) -> {

                        dialog.dismiss();
                        showSettingDialog();

                    });
            builder.show();

        }


        setUpBinding();
        initViewModel();
        initObserver();
        setUpOnClickListener();

        if(PreferenceUtils.retriveKeepMeLoggedInStatus(this)){
            mainBinding.cbRemember.setChecked(true);
            PreferenceUtils.retriveAutoLoginDetails(this);
            GlobalFunctions.showProgressDialog("Signing In.Please Wait..",this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewModel.validateLogin(GlobalValues.username,GlobalValues.password,PreferenceUtils.retriveInitialCompanyId(LoginView.this));
                }
            },1000);
        }


      //  String message = PreferenceUtils.saveCompanyId("666666", LoginView.this);
       // GlobalFunctions.showSnackBar(mainBinding.coordinatorLayout,message,0,1);




    }

    private void initObserver() {

        viewModel.getErrorMessage().observe(this, s -> {
            viewModel.getIsLoading().setValue(false);
            GlobalFunctions.showSnackBar(mainBinding.coordinatorLayout, s, alertColor);
            GlobalFunctions.dismissProgressDialog();
        });


        viewModel.getLoginResponse().observe(this, loginResponse -> {
           if(loginResponse!=null){
               viewModel.getIsLoading().setValue(false);
                GlobalFunctions.dismissProgressDialog();
               //  PreferenceUtils.saveLoginCredentials(loginResponse, this);
               GlobalFunctions.showSnackBar(mainBinding.coordinatorLayout, "Successfully Logged In", 0);
               startActivity(new Intent(LoginView.this, DashboardView.class));
               viewModel.clearLoginResponseData();
           }

        });

        viewModel.getIsLoading().observe(this, aBoolean -> {
            if (aBoolean) {
                progressDialog.show();
            } else {

                progressDialog.dismiss();
            }
        });
    }


    private void initViewModel() {
        viewModel = new ViewModelProvider(this, new LoginViewModelFactory()).get(LoginViewModel.class);
    }

    private void setUpOnClickListener() {
        mainBinding.btnLogin.setOnClickListener(v -> {
            String username = mainBinding.etUsername.getText().toString();
            String password = mainBinding.etPassword.getText().toString();
            boolean isRememberLogin = mainBinding.cbRemember.isChecked();
            PreferenceUtils.updateKeepMeLoggedInStatus(LoginView.this,isRememberLogin);
            if(isRememberLogin){
                PreferenceUtils.saveAutoLoginDetails(LoginView.this,username,password);
            }
            GlobalFunctions.showProgressDialog("Signing In.Please Wait..",this);
            viewModel.validateLogin(username, password, PreferenceUtils.retriveInitialCompanyId(this));

        });

        mainBinding.ivSettings.setOnClickListener(v -> {
           /* DatabaseHelper databaseHelper = new DatabaseHelper(GlobalContext.getContext());
            databaseHelper.clearVertical();

            PreferenceUtils.saveDataDownloadStatus(false,GlobalContext.getContext());*/

            showSettingDialog();
        });

    }

    private void showSettingDialog() {
        Dialog dialog = new Dialog(LoginView.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_login_setting);
        EditText et_companyId = dialog.findViewById(R.id.et_companyId);
        Button btn_cancel, btn_apply;
        btn_apply = dialog.findViewById(R.id.btn_apply);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_companyId.getText().toString().isEmpty()) {
                    GlobalFunctions.showToast("Please fill the company id field.");
                } else {
                    String message = PreferenceUtils.saveCompanyId(et_companyId.getText().toString(), LoginView.this);
                   GlobalFunctions.showSnackBar(mainBinding.coordinatorLayout,message,0,1);
                    dialog.dismiss();
                }
            }
        });

        Window dialogWindow = dialog.getWindow();
        WindowManager m = LoginView.this.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = d.getWidth() * 1;
        dialog.show();
    }


    private void setUpBinding() {
        mainBinding = ActivityLoginViewBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);
    }


}