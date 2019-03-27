package com.elitecorelib.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

import com.elitecore.wifi.api.CommonWiFiUtility;
import com.elitecorelib.andsf.utility.ANDSFUtility;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferenceConstants;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;

public class BatteryReceiver extends BroadcastReceiver {

    private static final String MODULE = "BatteryReceiver";
    SharedPreferencesTask spTask = new SharedPreferencesTask();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            EliteSession.eLog.d(MODULE, "Battery Receiver invoked");

            if (CommonWiFiUtility.isAlreadyConnected(spTask.getString(SharedPreferencesConstant.ACTIVECONNECTION))) {
                if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getBooleanFirstFalse(SharedPreferenceConstants.ISBETTERYTHRESHOLDENABLE)) {

                    int currentBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                    int maximumBatteryLevel = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                    EliteSession.eLog.d(MODULE, "currentBatteryLevel - "+currentBatteryLevel);
                    EliteSession.eLog.d(MODULE, "maximumBatteryLevel - "+maximumBatteryLevel);

                    int level = -1;
                    if (currentBatteryLevel >= 0 && maximumBatteryLevel > 0) {
                        level = (currentBatteryLevel * 100) / maximumBatteryLevel;
                    }

                    if (level <= spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY)) {
                        spTask.saveBoolean(SharedPreferenceConstants.ISBATTERYLEVELLOW, true);
                        EliteSession.eLog.d(MODULE, "Battery Lavel is - "+level+" Less Than Server Battery Value -"+spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY));
                        ANDSFUtility.callClearWiFiConnectivity(context);
                    } else {
                        spTask.saveBoolean(SharedPreferenceConstants.ISBATTERYLEVELLOW, false);
                        EliteSession.eLog.d(MODULE, "Battery Lavel is - "+level+" Grater Than Server Battery Value -"+spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY));
                    }
                }
            } else {
                Log.d(MODULE, "Acrive SSID not match to Connected SSID");
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());

        }
    }
}
