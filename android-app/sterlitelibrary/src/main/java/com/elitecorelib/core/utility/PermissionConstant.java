package com.elitecorelib.core.utility;

import android.Manifest;

/**
 * Created by Chirag Parmar on 24-Jul-17.
 */

public interface PermissionConstant {

    String PERMISSION_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    String PERMISSION_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    String PERMISSION_SENSORS = Manifest.permission.BODY_SENSORS;
    String PERMISSION_CONTACTS = Manifest.permission.READ_CONTACTS;
    String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    String PERMISSION_STORAGE_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    String PERMISSION_MICROPHONE = Manifest.permission.RECORD_AUDIO;
    String PERMISSION_SMS = Manifest.permission.READ_SMS;
    String PERMISSION_CALENDAR = Manifest.permission.WRITE_CALENDAR;
    String PERMISSION_CAMERA = Manifest.permission.CAMERA;
}
