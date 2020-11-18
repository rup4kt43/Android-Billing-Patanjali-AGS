package com.example.androidbilling.Settings.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidbilling.databinding.ActivitySettingsBinding;
import com.example.androidbilling.login.view.LoginView;
import com.example.androidbilling.utilities.global.GlobalFunctions;
import com.example.androidbilling.utilities.global.GlobalValues;
import com.example.androidbilling.utilities.sharedpreference.PreferenceUtils;

import java.util.Objects;

public class Settings extends AppCompatActivity {
    ActivitySettingsBinding settingsBinding;

    boolean do_print, bill_edit;
    boolean isCompanyIdExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpBinding();
        settingsBinding.setCompanyDetails(GlobalValues.company);


        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setTitle("SETTINGS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        retriveSettingDetails();

        //Locking the edit switch if the bill is loaded previously
       /* if(PreferenceUtils.editModeStatus(this)){
            settingsBinding.swBill.setEnabled(false);
        }*/

        setBillSwitchvalidation();

        initListener();
    }

    private void initListener() {
        settingsBinding.llCompanyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompanyIdExpanded) {
                    isCompanyIdExpanded = false;
                    settingsBinding.llEditCompanyId.setVisibility(View.GONE);
                } else {
                    isCompanyIdExpanded = true;
                    settingsBinding.llEditCompanyId.setVisibility(View.VISIBLE);
                    settingsBinding.etCompanyId.requestFocus();
                }
            }
        });

        settingsBinding.btnSaveCompanyIdEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settingsBinding.etCompanyId.getText().toString().isEmpty()) {
                    GlobalFunctions.showToast("Please fill the company id field first!");

                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setMessage("Saving this info will redirect you to login page. Are you sure you want to save?")
                            .setCancelable(false)
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String message = PreferenceUtils.saveCompanyId(settingsBinding.etCompanyId.getText().toString(), Settings.this);
                            Intent i = new Intent(Settings.this, LoginView.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            GlobalFunctions.showToast(message);
                        }
                    });
                    builder.show();

                }
            }
        });
    }

    private void clearAllIntentFlag() {

    }

    private void setBillSwitchvalidation() {
        if (PreferenceUtils.isEditLocked(this)) {
            settingsBinding.swBill.setEnabled(false);
        } else
            settingsBinding.swBill.setEnabled(true);
    }


    private void retriveSettingDetails() {
        do_print = PreferenceUtils.retrivePrintintDetails(this);
        settingsBinding.swPrinting.setChecked(do_print);


        bill_edit = PreferenceUtils.retriveBillEditOption(this);
        settingsBinding.swBill.setChecked(bill_edit);


    }

    @SuppressLint("RtlHardcoded")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            updateSharedPref();
            Settings.this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

    private void updateSharedPref() {

        //Saving Printing details
        PreferenceUtils.savePrintingDetails(this, settingsBinding.swPrinting.isChecked());
        PreferenceUtils.saveBillEditDetails(this, settingsBinding.swBill.isChecked());


    }


    @Override
    public void onBackPressed() {
        updateSharedPref();
        super.onBackPressed();
    }


    private void setUpBinding() {
        settingsBinding = com.example.androidbilling.databinding.ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = settingsBinding.getRoot();
        setContentView(view);


    }

}