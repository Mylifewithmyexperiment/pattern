package com.elitecorelib.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elitecore.wifi.api.WifiConnectionManager;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;

public class NotificationReceiver extends BroadcastReceiver {

    private final String MODULE = "NotificationReceiver";
    private final SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    @Override
    public void onReceive(Context context, Intent intent) {
        EliteSession.eLog.i(MODULE, "Invoke Notification Client");

        if (spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER)) {
            EliteSession.eLog.i(MODULE, "Invoke WiFi Connection Manager");

            new WifiConnectionManager(context);

        } else {
            EliteSession.eLog.i(MODULE, "Please call invokeANDSFClient Method before Silent Push Notification");
        }
    }
}
