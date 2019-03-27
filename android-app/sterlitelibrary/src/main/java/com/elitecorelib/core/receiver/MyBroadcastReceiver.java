package com.elitecorelib.core.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.services.BackgroundLocationService;
import com.elitecorelib.core.utility.SharedPreferencesTask;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private final static String MODULE = "MyBroadcastReceiver";

    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    @Override
    public void onReceive(Context context, Intent intent) {
        EliteSession.eLog.d(MODULE,"MyBroadcastReceiver class invoke");
        //For BackgroundLocationService Class
        BackgroundLocationService.startDistanceCheck = true;

        try {
            if (spTask.getString(CoreConstants.TRACK_LOCATION).equals("")
                    || spTask.getString(CoreConstants.TRACK_LOCATION).equals(CoreConstants.ENABLE)) {
                ComponentName comp = new ComponentName(context.getPackageName(), BackgroundLocationService.class.getName());
                ComponentName service = context.startService(new Intent().setComponent(comp));
                if (null == service) {
                    // something really wrong here
                    EliteSession.eLog.d(MODULE, "Could not start service " + comp.toString());
                }
                EliteSession.eLog.d(MODULE, "Application Track Location set Enable.");
            } else {
                EliteSession.eLog.d(MODULE, "Application Track Location set Disable.");
            }
        }catch (Exception e){
            EliteSession.eLog.e(MODULE, "MyBroadcastReceiver onReceive error:: " + e.getMessage());
        }
    }
}