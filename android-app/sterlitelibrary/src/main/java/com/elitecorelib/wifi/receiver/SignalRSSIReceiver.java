package com.elitecorelib.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.elitecore.wifi.api.CommonWiFiUtility;
import com.elitecorelib.andsf.utility.ANDSFUtility;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;


/**
 * Created by Harshesh Soni on 06-June-18.
 */

public class SignalRSSIReceiver extends BroadcastReceiver {

    private static final String MODULE = "SignalRSSIReceiver";
    private SharedPreferencesTask spTask = new SharedPreferencesTask();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            EliteSession.eLog.d(MODULE, "SignalRSSIReceiver invoked");

            if (CommonWiFiUtility.isAlreadyConnected(spTask.getString(SharedPreferencesConstant.ACTIVECONNECTION))) {

                if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getBoolean(SharedPreferencesConstant.ISSIGNALASSISTANCE)) {
                    int newRssi = intent.getIntExtra(WifiManager.EXTRA_NEW_RSSI, 0);

                    EliteSession.eLog.d(MODULE, "Device SignalRSSIReceiver is " + newRssi);

                    int wifiSignalLevel = -(spTask.getInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL) + Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER, SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER)));
                    EliteSession.eLog.d(MODULE, "Server SignalRSSIReceiver rssi with user set is " + wifiSignalLevel);
                    if (newRssi < wifiSignalLevel) {
                        ANDSFUtility.callClearWiFiConnectivity(context);
                    }
                } else {
                    EliteSession.eLog.d(MODULE, "SignalRSSIReceiver is disable");
                }
            } else {
                EliteSession.eLog.d(MODULE, "Active SSID not match to Connected SSID");
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }
}
