package com.example.bluetoothtest.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

public class PermissionUtility {
    Context context;
    Activity activity;

    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int BLUETOOTH_REQUEST_CODE = 2;

    public static final int BUILD_VERSION = Build.VERSION.SDK_INT;

    public PermissionUtility(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }

    public boolean checkForPermission(String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == 0;
    }

    public void requestPermission(String permission, int requestCode) {
        if (BUILD_VERSION >= Build.VERSION_CODES.M)
            activity.requestPermissions(new String[]{permission}, requestCode);


    }


}
