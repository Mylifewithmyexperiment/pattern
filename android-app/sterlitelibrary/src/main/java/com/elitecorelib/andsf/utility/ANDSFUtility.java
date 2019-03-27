package com.elitecorelib.andsf.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.elitecorelib.andsf.api.DeviceDetail;
import com.elitecorelib.andsf.pojo.ANDSFCircular;
import com.elitecorelib.andsf.pojo.ANDSFLocation3GPP;
import com.elitecorelib.andsf.pojo.ANDSFWLANLocation;
import com.elitecorelib.andsf.pojo.ANDSFwiMAXLocation;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.pojo.PojoWiFiConnection;
import com.elitecorelib.core.pojo.PojoWiFiProfile;
import com.elitecorelib.core.realm.RealmOperation;
import com.elitecorelib.core.services.ConnectionManagerClass;
import com.elitecorelib.core.services.ConnectionManagerListner;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.elitecorelib.wifi.receiver.ANDSFPolicyEvaluateReceiver;
import com.elitecorelib.wifi.receiver.ANDSFPolicyPullReceiver;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.elitecorelib.andsf.utility.ANDSFConstant.IS_ANDSF_POLICY_WIFI_CONNECTED;
import static com.elitecorelib.core.CoreConstants.MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID;

public class ANDSFUtility {

    private static final String MODULE = "ANDSFUtility";
    private static SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private static final int POLICY_PULL_CONSTANT = 890528;
    private static final int POLICY_EVALUTE_CONSTANT = 73204;
    private static RealmOperation realmOperation;

    public static void callPolicyPull(Context ctx) {

        Intent intent = new Intent(ctx, ANDSFPolicyPullReceiver.class);
        boolean isPolicyPullAlarmSet = (PendingIntent.getBroadcast(ctx, POLICY_PULL_CONSTANT, intent, PendingIntent.FLAG_NO_CREATE) != null);
        EliteSession.eLog.i(MODULE, "is Policy Alarm Set status - " + isPolicyPullAlarmSet);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if (isPolicyPullAlarmSet) {
            EliteSession.eLog.i(MODULE, "ANDSF policy Pull Alarm Already set");
        } else {
            int policyCallTimer = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_PULLINTERVAL, "1440")); // 24 hr
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, POLICY_PULL_CONSTANT, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                    , (policyCallTimer * 60 * 1000), pendingIntent);
        }
    }


    public static void callOneTimePolicyPull(Context ctx) {

        Intent intent = new Intent(ctx, ANDSFPolicyPullReceiver.class);
        boolean isPolicyPullAlarmSet = (PendingIntent.getBroadcast(ctx, POLICY_PULL_CONSTANT, intent, PendingIntent.FLAG_NO_CREATE) != null);
        EliteSession.eLog.i(MODULE, "is Policy Alarm Set status - " + isPolicyPullAlarmSet);

        if (isPolicyPullAlarmSet) {
            EliteSession.eLog.i(MODULE, "ANDSF policy Pull Alarm Already set");
        } else {
            int policyCallTimer = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_PULLINTERVAL, "1440")); // 24 hr
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, POLICY_PULL_CONSTANT, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ (policyCallTimer * 60 * 1000), pendingIntent);
        }
    }

    public static void updatePolicyPull(Context ctx, int policyCallTimer){

        Intent intent = new Intent(ctx, ANDSFPolicyPullReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, POLICY_PULL_CONSTANT, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                , (policyCallTimer * 60 * 1000), pendingIntent);

    }

    public static void callPolicyEvaluation(Context ctx) {

        EliteSession.eLog.d(MODULE, "ANDSF Evaluation policy Call");
        Intent intent = new Intent(ctx, ANDSFPolicyEvaluateReceiver.class);
        boolean isPolicyEvaluationAlarmSet = (PendingIntent.getBroadcast(ctx, POLICY_EVALUTE_CONSTANT, intent, PendingIntent.FLAG_NO_CREATE) != null);
        EliteSession.eLog.i(MODULE, "is Policy Evaluation Alarm Set status - " + isPolicyEvaluationAlarmSet);

        if (isPolicyEvaluationAlarmSet) {
            EliteSession.eLog.i(MODULE, "ANDSF policy Evaluation Alarm Already set");
        } else {

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis() + (1 * 60 * 1000));

            int policyEvaluteTimer = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_EVALUATEINTERVAL, "900")); //15 min
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, POLICY_EVALUTE_CONSTANT, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()
                    , (policyEvaluteTimer * 60 * 1000), pendingIntent);
        }
    }

    public static void callOntTimePolicyEvaluation(Context ctx) {

        EliteSession.eLog.d(MODULE, "ANDSF Evaluation policy Call");
        Intent intent = new Intent(ctx, ANDSFPolicyEvaluateReceiver.class);
        boolean isPolicyEvaluationAlarmSet = (PendingIntent.getBroadcast(ctx, POLICY_EVALUTE_CONSTANT, intent, PendingIntent.FLAG_NO_CREATE) != null);
        EliteSession.eLog.i(MODULE, "is Policy Evaluation Alarm Set status - " + isPolicyEvaluationAlarmSet);

        if (isPolicyEvaluationAlarmSet) {
            EliteSession.eLog.i(MODULE, "ANDSF policy Evaluation Alarm Already set");
        } else {
            int policyEvaluteTimer = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANDSF_EVALUATEINTERVAL, "900")); //15 min
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, POLICY_EVALUTE_CONSTANT, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (1 * 60 * 1000)+ (policyEvaluteTimer * 60 * 1000), pendingIntent);
        }
    }

    public static void updatePolicyyEvaluation(Context ctx, int policyEvaluteTimer ){

        Intent intent = new Intent(ctx, ANDSFPolicyEvaluateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, POLICY_EVALUTE_CONSTANT, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                , (policyEvaluteTimer * 60 * 1000), pendingIntent);
    }

    public static void callClearWiFiConnectivity(Context ctx) {
        EliteSession.eLog.d(MODULE, "Clear Active WiFiConnection and WiFiProfile");
        realmOperation = RealmOperation.with(ctx);

        // Delete all wifi connection & profile
        realmOperation.deleteAll(PojoWiFiProfile.class);
        realmOperation.deleteAll(PojoWiFiConnection.class);

        if (!spTask.getString(IS_ANDSF_POLICY_WIFI_CONNECTED).equals("")) {

            WifiManager wifiManager = LibraryApplication.getLibraryApplication().getWiFiManager();
            EliteSession.eLog.d(MODULE, "User Device wifi disable");
            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
                while (wifiManager.isWifiEnabled()) {
                }
            }
            spTask.saveString(IS_ANDSF_POLICY_WIFI_CONNECTED, "");
        } else {

            if (spTask.getBoolean(CoreConstants.ANDSF_ISCONNECTED) == true) {
                EliteSession.eLog.d(MODULE, "User Device connected with SSID");
            } else {
                WifiManager wifiManager = LibraryApplication.getLibraryApplication().getWiFiManager();
                EliteSession.eLog.d(MODULE, "User Device wifi disable");
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                    while (wifiManager.isWifiEnabled()) {
                    }
                }
            }
        }
        spTask.saveString(SharedPreferencesConstant.ACTIVEPROFILE, "");
        spTask.saveString(SharedPreferencesConstant.ACTIVECONNECTION, "");
    }

    public static void getDeviceDetails(Context ctx, String latitude, String longitude) {
        ANDSFCircular mGeo_location = new ANDSFCircular();
        mGeo_location.latitude = latitude + "";
        mGeo_location.longitude = longitude + "";
        boolean isLTEDatNetworkType = false;

        ANDSFLocation3GPP mLocation3GPP = new ANDSFLocation3GPP();
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(TELEPHONY_SERVICE);
//        tm.listen(new PhoneStateListener(
//
//        ), PhoneStateListener.LISTEN_CALL_STATE | PhoneStateListener.LISTEN_CELL_LOCATION);
//

        if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {

            ElitelibUtility.geGSMCellLocation(ctx);

            try {
                ArrayList<String> mccMNC = ElitelibUtility.getMccMncFromActiveNetworkSim();
                mLocation3GPP.PLMN = mccMNC.get(0) + mccMNC.get(1);
                isLTEDatNetworkType = isLTE_NetworkType(tm);

                if ((spTask.getString(CoreConstants.LAC_VALUE) != null && !spTask.getString(CoreConstants.LAC_VALUE).equals("") )||
                        (spTask.getString(CoreConstants.CELL_ID_VALUE) != null && !spTask.getString(CoreConstants.CELL_ID_VALUE).equals(""))) {
                    if (isLTEDatNetworkType) {
                        mLocation3GPP.TAC = spTask.getString(CoreConstants.LAC_VALUE);
                        mLocation3GPP.EUTRA_CI = spTask.getString(CoreConstants.CELL_ID_VALUE);
                    } else {
                        mLocation3GPP.LAC = spTask.getString(CoreConstants.LAC_VALUE);
                        mLocation3GPP.GERAN_CI = spTask.getString(CoreConstants.CELL_ID_VALUE);
                    }
                } else {
                    EliteSession.eLog.e(MODULE, "GSMCellLocation is null ");
                }
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "Error in cellid get: " + e.getMessage());
            }
        }
        DeviceDetail.setInstance(mGeo_location, mLocation3GPP, new ANDSFwiMAXLocation(), new ANDSFWLANLocation(), new Date(System.currentTimeMillis()), spTask.getString(CoreConstants.imsi), spTask.getString(CoreConstants.imei), spTask.getString(CoreConstants.MSISDN), isLTEDatNetworkType);
    }


    public static boolean isLTE_NetworkType(TelephonyManager tm) {

        if (tm.getDataNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
            EliteSession.eLog.d(MODULE, "User Data NetworkType is 4G or LTE");
            return true;
        } else {
            EliteSession.eLog.d(MODULE, "User Data NetworkType is 2G/3G");
            return false;
        }
    }

    public static int getBatteryPercentage(Context context) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }

    public static boolean isConnectedWithWiFi(Context mContext) {
        boolean haveConnectedWifi = false;
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) {
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                    EliteSession.eLog.d(MODULE, "Wifi on & connected with SSID");
                }
            }
        }
        return haveConnectedWifi;
    }

    public static void invokeDynamicParameter(ConnectionManagerListner connectionManagerListner) {

        EliteSession.eLog.d(MODULE, " Invoke Dynamic Parameter");

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
            jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));
            jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);

            ConnectionManagerClass connectionManagerClass = new ConnectionManagerClass(connectionManagerListner, MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID);
            connectionManagerClass.execute(jsonObject.toString(), spTask.getString(CoreConstants.MONETIZATION_URL) + CoreConstants.MONETIZATION_GET_DYNAMIC_PARAMETER_URL);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " GetParameter request error " + e.getMessage());
        }
    }
}
