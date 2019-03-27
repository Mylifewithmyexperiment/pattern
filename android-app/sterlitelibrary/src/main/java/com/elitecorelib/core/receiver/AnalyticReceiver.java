package com.elitecorelib.core.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.services.AnalyticService;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesTask;

/**
 *
 * @author chirag  parmar
 * @v
 * <p>
 * Set the timer for the sync analytic data to server this receiver call.
 * here when we get the call back we check @EventAnalyticMode 1) Wifi Mode and
 * 2) Wifi & moblie data both
 * and call the sync data service
 */
public class AnalyticReceiver extends BroadcastReceiver {

    private final String MODULE = "AnalyticReceiver";
    private Context mContext;
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    @Override
    public void onReceive(Context context, Intent intent) {

        this.mContext = context;
        EliteSession.eLog.d(MODULE, "call analytic receiver");

        if (spTask.getString(CoreConstants.EVENTANALYTICSMODE).equals("Wifi Only")) {
            EliteSession.eLog.d(MODULE, "ANALYTICSMODE WIfi Only");

            if (ElitelibUtility.getConnectedWIFI()!=null && !ElitelibUtility.getConnectedWIFI().equals("")) {
                EliteSession.eLog.d(MODULE, "Wifi SSID connected ");
                anlyticCall();
            }

        } else if (spTask.getString(CoreConstants.EVENTANALYTICSMODE).equals("Wifi and Data")) {
            EliteSession.eLog.d(MODULE, "ANALYTICSMODE Wifi and Data");

            if(ElitelibUtility.isNetworkAvaliable(mContext)) {

                EliteSession.eLog.d(MODULE, "Network available");
                anlyticCall();
            }
        }
    }

    public void anlyticCall() {

        try {
            ComponentName comp = new ComponentName(mContext.getPackageName(), AnalyticService.class.getName());
            ComponentName service = mContext.startService(new Intent().setComponent(comp));

            if (null == service) {
                // something really wrong here
                EliteSession.eLog.d(MODULE, "Could not start service " + comp.toString());

            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }
}
