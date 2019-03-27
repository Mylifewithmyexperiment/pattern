package com.elitecorelib.core.services;

import android.app.IntentService;
import android.content.Intent;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Chirag Parmar on 01-Sep-16.
 */
public class AnalyticService extends IntentService implements ConnectionManagerCompleteListner {


    private final String MODULE = "AnalyticService";
    private SharedPreferencesTask task = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public AnalyticService() {
        super("");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        EliteSession.eLog.d(MODULE, "Call Analytic Sync To Server");

        JsonArray analyticData = LibraryApplication.getLibraryApplication().getEliteAnalytics().getAnalyticData();

        if (analyticData != null && !analyticData.equals("")) {
            syncAnalyticData(analyticData);
        } else {
            EliteSession.eLog.d(MODULE, "Analytic data not available");
        }
        return START_STICKY;
    }

    /**
     * Get the Analytic data to insert activity perform from application
     * to database.
     *
     * @param analyticData This is the analytic data Json whose
     *                     create by GetAnalytic method;
     */
    private void syncAnalyticData(JsonArray analyticData) {

        try {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty(CoreConstants.USERIDENTITY, task.getString(CoreConstants.USERIDENTITY));
            jsonObject.addProperty(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);

            if (task.getString(CoreConstants.SECRETKEY) != null) {
                jsonObject.addProperty(CoreConstants.SECRETKEY, task.getString(CoreConstants.SECRETKEY));
            }
            jsonObject.add(CoreConstants.EVENTDATA, analyticData);

            ConnectionManagerTaskNew connectionTask = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_ANALYTICSYNC_REQUESTID);
            connectionTask.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_ADDEVENT);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error when call Analyic sync service : " + e.getMessage());
        }

    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestId) {

        EliteSession.eLog.d(MODULE, "Responce - " + result);

        try {

            if (result != null && !result.equals("") && requestId == CoreConstants.MONETIZATION_ANALYTICSYNC_REQUESTID) {

                JSONObject object = new JSONObject(result);

                if(object.getInt("responseCode") == 1) {

                    int i = LibraryApplication.getLibraryApplication().getEliteAnalytics().deleteAnalytic();

                    if(i>0) {
                        EliteSession.eLog.d(MODULE, "Successully sync data, "+i+" row clear from database");
                    }
                }
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error in analytic response" + e.getMessage());
        }
    }
}
