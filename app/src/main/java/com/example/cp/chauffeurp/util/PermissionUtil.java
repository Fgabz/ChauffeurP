package com.example.cp.chauffeurp.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public class PermissionUtil {

    private static final int MY_PERMISSION_REQUEST_APP = 0;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;


    public static void requestFineLocationPermission(Activity activity) {
        if (!hasFineLocationPermission(activity)) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
    }

    public static void requestAppPermission(Activity activity) {
        boolean locationPermission = hasFineLocationPermission(activity);

        final List<String> permissionsList = new ArrayList<>();

        if (!locationPermission) {
            permissionsList.add(FINE_LOCATION);
        }

        if (permissionsList.size() > 0) {
            ActivityCompat.requestPermissions(activity,
                    permissionsList.toArray(new String[permissionsList.size()]), MY_PERMISSION_REQUEST_APP);
        }
    }

    public static boolean hasFineLocationPermission(Activity activity) {
        return (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED);
    }
}
