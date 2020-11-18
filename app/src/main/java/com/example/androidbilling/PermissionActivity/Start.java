package com.example.androidbilling.PermissionActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basewin.services.ServiceManager;
import com.example.androidbilling.R;
import com.example.androidbilling.login.view.LoginView;
import com.smartpeak.GlobalData;
import com.smartpeak.PinpadInterfaceVersion;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class Start extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final int REQUEST_PERMISSION = 0x01;
    String[] perms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        permission();
    }

    @AfterPermissionGranted(REQUEST_PERMISSION)
    private void permission() {
        perms = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                "com.pos.permission.SECURITY",
                "com.pos.permission.ACCESSORY_DATETIME",
                "com.pos.permission.ACCESSORY_LED",
                "com.pos.permission.ACCESSORY_BEEP",
                "com.pos.permission.ACCESSORY_RFREGISTER",
                "com.pos.permission.CARD_READER_ICC",
                "com.pos.permission.CARD_READER_PICC",
                "com.pos.permission.CARD_READER_MAG",
                "com.pos.permission.COMMUNICATION",
                "com.pos.permission.PRINTER",
                "com.pos.permission.EMVCORE",
                Manifest.permission.READ_PHONE_STATE
        };
        if (EasyPermissions.hasPermissions(this, perms)) {
            lauchMainActivity();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(
                    new PermissionRequest
                            .Builder(this, REQUEST_PERMISSION, perms)
                            .setRationale("Dear users\nneed required permissions for better use of this application")
                            .setNegativeButtonText("NO")
                            .setPositiveButtonText("YES")
                            .build()
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("Granted", "onRequestPermissionsResult:" + requestCode);
        if (requestCode == 1) {
//            lauchMainActivity();
        }
        if (EasyPermissions.hasPermissions(this, perms)) {
            lauchMainActivity();
        } else {
            new AppSettingsDialog
                    .Builder(this)
                    .setTitle("Attention")
                    .setRationale("Dear users, in order to make better use of this application, you need to allow required permissions.")
                    .setNegativeButton("Refuse")
                    .setPositiveButton("Go To Set")
                    .setRequestCode(0x001)
                    .build()
                    .show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.e("Granted", "onPermissionsGranted:" + requestCode + ":" + perms.toString());
    }


    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.e("Denied", "onPermissionsDenied:" + requestCode + ":" + perms.toString());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog
                    .Builder(this)
                    .setTitle("Attention")
                    .setRationale("Dear users, in order to make better use of this application, you need to allow required permissions.")
                    .setNegativeButton("Refuse")
                    .setPositiveButton("Go To Set")
                    .setRequestCode(0x001)
                    .build()
                    .show();
        }
    }


    private void lauchMainActivity() {
        ServiceManager.getInstence().init(getApplicationContext());
        GlobalData.getInstance().init(this);
        GlobalData.getInstance().setPinpadVersion(PinpadInterfaceVersion.PINPAD_INTERFACE_DUKPT);

        Intent mainIntent = new Intent(Start.this, LoginView.class);
        startActivity(mainIntent);
        finish();
    }
}

