package com.elitecorelib.core.fcm;

import android.util.Log;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chirag Parmar on 12-Jul-16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService implements ConnectionManagerCompleteListner {

    private static final String MODULE = "MyFirebaseIIDService";
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        EliteSession.eLog.d(MODULE, "Refreshed token: " + refreshedToken);
        Log.d(MODULE, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {

        // Add custom implementation, as needed.
        EliteSession.eLog.d(MODULE, "Store GCM Token in Share Preference.");
        spTask.saveString(CoreConstants.DEVICEID, token);


        try {

            JSONObject jsonObject = new JSONObject();
            if (spTask.getString(CoreConstants.USERIDENTITY) != null &&
                    !spTask.getString(CoreConstants.USERIDENTITY).equals("") && spTask.getInt(CoreConstants.KEY_IS_REGISTRAION) != 0) {
                try {

                    jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                    jsonObject.put(CoreConstants.REGISTEREDDEVICEID,token);
                    jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                    jsonObject.put(CoreConstants.imsi, spTask.getString(CoreConstants.imsi));
                    jsonObject.put(CoreConstants.imei, spTask.getString(CoreConstants.imei));
                    jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));
                    ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_UPDATEDEVICETOKEN_REQUESTID);
                    task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_UPDATEDEVICETOKEN);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service Device ID update");
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            EliteSession.eLog.e(MODULE, " Main Error onHandleIntent invoking service Device ID update");
            e.printStackTrace();
        }

    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestId) {
        EliteSession.eLog.d(MODULE,"Update FCM Token Result - "+ result);
    }
}
