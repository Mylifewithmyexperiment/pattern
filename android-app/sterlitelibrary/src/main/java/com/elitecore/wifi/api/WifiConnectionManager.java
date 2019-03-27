package com.elitecore.wifi.api;

import android.annotation.SuppressLint;
import android.content.Context;

import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.andsf.utility.ANDSFUtility;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.pojo.PojoWiFiConnection;
import com.elitecorelib.core.pojo.PojoWiFiProfile;
import com.elitecorelib.core.realm.RealmOperation;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.elitecorelib.wifi.api.WiFiConstants;
import com.elitecorelib.wifi.utility.WifiUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.elitecorelib.andsf.utility.ANDSFConstant.IS_ANDSF_POLICY_WIFI_CONNECTED;

public class WifiConnectionManager implements OnWiFiTaskCompleteListner {

    private SharedPreferencesTask sptask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private final String MODULE = "WifiConnectionManager";
    private IWiFiConfiguration wifiapi = null;
    private String ssid, activeProfile;
    private RealmOperation operation = null;
    private Context mContext;
    private ArrayList<PojoWiFiConnection> activeConnections = null;
    private PojoWiFiProfile activeWifiProfile;
    private int mWiFiIndex = 0;
    private String responseResult = null;
    private Boolean isSSIDinRange = false;

    public WifiConnectionManager(Context context) {
        try {
            mContext = context;

            if (sptask.getBoolean(CoreConstants.ISBETTERYTHRESHOLDENABLE) == true) {
                EliteSession.eLog.d(MODULE, "Battery Check Enable");

                if (checkBatteryLife(ElitelibUtility.getBatteryPercentage(mContext)) == false) {
                    EliteSession.eLog.d(MODULE, "Battery Check false");
                    isSSIDinRange = false;
                    return;
                }
            }
            activeConnections = new ArrayList<PojoWiFiConnection>();
            wifiapi = new EliteWiFiAPI(this);
            wifiapi.getAllWifiSSID();
        } catch (Exception e) {
            EliteSession.eLog.d(MODULE, "Error in - " + e.getMessage());
        }
    }

    private void getActiveProfile() {

        operation = new RealmOperation(mContext);
        activeWifiProfile = operation.getActiveWiFi();
        activeConnections = operation.getWiFiConnection();

        sptask.saveString(SharedPreferencesConstant.ACTIVEPROFILE, activeProfile);
        activeProfile = activeWifiProfile.getAndroidSettingName();
        EliteSession.eLog.d(MODULE, "getActiveProfile name - " + activeWifiProfile.getAndroidSettingName() + " Active Connection Size - " + activeConnections.size());

        InvokeCurrentWifiConnection();
    }

    @SuppressLint("LongLogTag")
    private void InvokeCurrentWifiConnection() {

        doWifiConnection(mWiFiIndex);
    }

    private void doWifiConnection(int index) {
        isSSIDinRange = false;
        ssid = activeConnections.get(index).getSsidName();
        sptask.saveString(SharedPreferencesConstant.ACTIVECONNECTION, ssid);
        EliteSession.eLog.i(MODULE, "WiFiCount index =" + index + " SSID Name =" + ssid);
        try {
            JSONObject json = new JSONObject(responseResult);
            JSONArray jsonArray = json.getJSONArray(EliteWiFIConstants.RESPONSEDATA);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject obj = jsonArray.getJSONObject(i);

                    if (obj.getString(EliteWiFIConstants.WIFI_SSID).equals(ssid)) {
                        isSSIDinRange = true;

                        if (activeConnections.get(index).getSecurityType().equals(WifiUtility.EAP) &&
                                WifiUtility.getWifiScanSecurity(obj).equals(WifiUtility.WPAWPA2Enterprise)) {
                            EliteSession.eLog.d(MODULE, "Capability Match");
                            if (sptask.getBoolean(CoreConstants.ISSIGNALASSISTANCE) == true) {
                                EliteSession.eLog.d(MODULE, "Signal Check Enable");
                                if (checkWifiSignal(obj.getInt(EliteWiFIConstants.WIFI_LEVEL)) == false) {
                                    isSSIDinRange = false;
                                    EliteSession.eLog.d(MODULE, "Signal Check false");
                                }
                            }
                        } else if (WifiUtility.getWifiScanSecurity(obj).contains(activeConnections.get(index).getSecurityType())) {
                            EliteSession.eLog.d(MODULE, "Capability Match");

                            if (sptask.getBoolean(CoreConstants.ISSIGNALASSISTANCE) == true) {
                                EliteSession.eLog.d(MODULE, "Signal Check Enable");
                                if (checkWifiSignal(obj.getInt(EliteWiFIConstants.WIFI_LEVEL)) == false) {
                                    isSSIDinRange = false;
                                    EliteSession.eLog.d(MODULE, "Signal Check false");
                                }
                            }
                        } else {
                            EliteSession.eLog.d(MODULE, "Capability not  Match");
                            isSSIDinRange = false;
                        }

                        EliteSession.eLog.d(MODULE, "SSID available in range -" + isSSIDinRange);

                        if (isSSIDinRange == true) {
                            wifiapi.connectToWiFi(mContext, getOLDWiFiPoJo(activeConnections.get(index)), this, true, false);
                            return;
                        }
                    }
                }
            } else {
                EliteSession.eLog.d(MODULE, "Error while in jsonarray getting ");
            }

            if (isSSIDinRange == false) {
                mWiFiIndex = mWiFiIndex + 1;
                if (activeConnections.size() > mWiFiIndex) {
                    getActiveProfile();
                } else {
                    ANDSFUtility.callClearWiFiConnectivity(mContext);
                }
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while in jsonarray getting " + e.getMessage());
        }
    }

    private boolean checkWifiSignal(int CurrentSignal) {

        EliteSession.eLog.d(MODULE, "final Scan WiFi Signal Strenth is =" + CurrentSignal);
        EliteSession.eLog.d(MODULE, "Server WiFi Signal Strenth is=" + sptask.getInt(CoreConstants.REQUIREDSIGNALLEVEL));
        EliteSession.eLog.d(MODULE, "Additional WiFi Signal Strenth is=" + Integer.parseInt(sptask.getString(CoreConstants.REQUIREDSIGNALLEVEL_USER)));
        EliteSession.eLog.d(MODULE, "Policy WiFi Signal Strenth is=" + -sptask.getInt(CoreConstants.REQUIREDSIGNALLEVEL));

        return CurrentSignal >= -sptask.getInt(CoreConstants.REQUIREDSIGNALLEVEL);
    }

    private boolean checkBatteryLife(int value) {

        return value >= sptask.getInt(CoreConstants.PROGRESS_BATTERY);
    }

    private final PojoConnection getOLDWiFiPoJo(PojoWiFiConnection pojoWiFiConnection) {
        PojoConnection oldWiFiPojo = new PojoConnection();
        oldWiFiPojo.setSsid(pojoWiFiConnection.getSsidName());
        oldWiFiPojo.setIdentity(pojoWiFiConnection.getUserIdentity());
        oldWiFiPojo.setActive(pojoWiFiConnection.isPreferable());
        oldWiFiPojo.setDefault(pojoWiFiConnection.isPreferable());
        oldWiFiPojo.setLocal(pojoWiFiConnection.isLocal());
        oldWiFiPojo.setOutOfRange(pojoWiFiConnection.isOutOfRange());
        oldWiFiPojo.setShowPassword(pojoWiFiConnection.isShowPassword());
        oldWiFiPojo.setWisprEnabled(pojoWiFiConnection.isWisprEnabled());
        oldWiFiPojo.setPassword(pojoWiFiConnection.getPassword());
        oldWiFiPojo.setPhase2Authentication(pojoWiFiConnection.getProtocolType());


        if (pojoWiFiConnection.getSecurityType().contains(WiFiConstants.SEUCURITY_TYPE_EAP)) {

            if (pojoWiFiConnection.getEapType().equals(EliteWiFIConstants.WIFI_SIM)) {
                oldWiFiPojo.setSecurity(EliteWiFIConstants.WIFI_EAPSIM);
            } else if (pojoWiFiConnection.getEapType().equals(EliteWiFIConstants.WIFI_AKA)) {
                oldWiFiPojo.setSecurity(EliteWiFIConstants.WIFI_EAPAKA);
            } else {
                oldWiFiPojo.setSecurity(EliteWiFIConstants.WIFI_EAPSIM);
            }
        } else if (pojoWiFiConnection.getSecurityType().contains(WiFiConstants.SEUCURITY_TYPE_WPA)) {
            oldWiFiPojo.setSecurity(EliteWiFIConstants.WIFI_WPA);
        } else if (pojoWiFiConnection.getSecurityType().contains(EliteWiFIConstants.WIFI_OPEN)) {
            oldWiFiPojo.setSecurity(EliteWiFIConstants.WIFI_OPEN);
        }

        oldWiFiPojo.setSim_operator_name(pojoWiFiConnection.getNetworkName());
        oldWiFiPojo.setWisprAuthenticationMethod(pojoWiFiConnection.getWisprAuthenticationMethod());
        oldWiFiPojo.setWisprPassword(pojoWiFiConnection.getWisprPassword());
        oldWiFiPojo.setWisprUsarname(pojoWiFiConnection.getWisprUsarname());
        if (pojoWiFiConnection.getValidForAllNetwork() != null && pojoWiFiConnection.getValidForAllNetwork().equals("N")) {
            oldWiFiPojo.setCheckOperator(true);
        }

        return oldWiFiPojo;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onWiFiTaskComplete(String result) {

        EliteSession.eLog.d(MODULE, "WiFiTastComplete Result  - " + result);

        if (result != null) {

            if (result == WiFiConstants.CONNECTED || result == WiFiConstants.ALREADYCONNECTED) {

                sptask.saveString(IS_ANDSF_POLICY_WIFI_CONNECTED, ssid);

                sptask.saveString(SharedPreferencesConstant.ACTIVECONNECTION, ssid);
                sptask.saveString(SharedPreferencesConstant.ACTIVEPROFILE, activeProfile);

                if (sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("") ||
                        sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("0") ||
                        sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("") ||
                        sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("0") ||
                        sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYURL).equalsIgnoreCase("")) {

                    if (sptask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                        EliteSession.eLog.i(MODULE, "Latency All Parameters not set on the Serve");
                        if (sptask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                            EliteSession.eLog.d(MODULE, "Enable Speed Thresold");
                            wifiapi.getDownloadUploadSpeed(ssid, this);
                        }
                    }


                } else {

                    EliteSession.eLog.i(MODULE, "Latency All Parameters set on the Serve and Latency Check Execution Start");
                    new ElitelibUtility.LatencyCheck(this).execute();
                }

               /* if(sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).isEmpty() == false ||
                        sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).isEmpty() == false ||
                        sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYURL).isEmpty() == false){

                    EliteSession.eLog.i(MODULE, "Latency All Parameters set on the Serve and Latency Check Execution Start");
                    new ElitelibUtility.LatencyCheck(this).execute();

                }else{
                    EliteSession.eLog.i(MODULE, "Latency All Parameters not set on the Serve");
                    if (sptask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                        EliteSession.eLog.d(MODULE, "Enable Speed Thresold");
                        wifiapi.getDownloadUploadSpeed(ssid, this, LibraryApplication.getLibraryApplication().getCurrentActivity());
                    }
                }*/


                //wifiapi.getDownloadUploadSpeed(ssid, this, LibraryApplication.getLibraryApplication().getCurrentActivity());

            } else if (result == WiFiConstants.NOTCONNECTED) {

                mWiFiIndex = mWiFiIndex + 1;
                if (activeConnections.size() > mWiFiIndex) {
                    getActiveProfile();
                } else {
                    ANDSFUtility.callClearWiFiConnectivity(mContext);
                }
            }
        }
    }

    @Override
    public void isWiFiInRange(boolean status) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public void getResponseData(String responseData) {

        try {
            EliteSession.eLog.i(MODULE, " Response Data - " + responseData);
            JSONObject json = new JSONObject(responseData);
            if (json.getString(EliteWiFIConstants.REQUESTID).equals(String.valueOf(EliteWiFIConstants.SUCCESS_CODE_ALLSSID))) {
                responseResult = responseData;
                getActiveProfile();

            } else if (json.getString(EliteWiFIConstants.REQUESTID).
                    equals(EliteWiFIConstants.FAILURE_CODE_INTERNETNOTAVAILABLE)) {

            } else if (json.getString(EliteWiFIConstants.REQUESTID).equals(String.valueOf(EliteWiFIConstants.FAILURE_CODE_SSIDEMPTY))) {

            } else if (json.getString(EliteWiFIConstants.REQUESTID).equals(String.valueOf(EliteWiFIConstants.FAILURE_CODE_SSIDNOTCONNECTED))) {

            } else if (json.getString(EliteWiFIConstants.REQUESTID).equals(String.valueOf(EliteWiFIConstants.SUCCESS_CODE_NETWORKSPEED))) {


                double upload = json.getDouble(EliteWiFIConstants.UPLOAD_SPEED);
                double download = json.getDouble(EliteWiFIConstants.DOWNLOAD_SPEED);


                if (sptask.getString(CoreConstants.ANDSF_DOWNLOADSPEED).isEmpty() == false
                        || sptask.getString(CoreConstants.ANDSF_UPLOADSPEED).isEmpty() == false) {

                    EliteSession.eLog.i(MODULE, "Upload Speed : " + upload + " Download Speed : " + download);
                    EliteSession.eLog.i(MODULE, "Threshold Upload Speed : " + sptask.getString(CoreConstants.ANDSF_UPLOADSPEED) +
                            " Threshold Download Speed : " + sptask.getString(CoreConstants.ANDSF_DOWNLOADSPEED));


                    if (download <= Double.parseDouble(sptask.getString(CoreConstants.ANDSF_DOWNLOADSPEED)) ||
                            upload <= Double.parseDouble(sptask.getString(CoreConstants.ANDSF_UPLOADSPEED))) {
                        ANDSFUtility.callClearWiFiConnectivity(mContext);
                    }
                }

            } else if (json.getString(EliteWiFIConstants.REQUESTID).equals(String.valueOf(EliteWiFIConstants.SUCCESS_CODE_LATENCYCHECK))) {

                int sentPackets = json.getInt(EliteWiFIConstants.ANDSF_LATENCYPACKETSENT);
                int receivePackets = json.getInt(EliteWiFIConstants.ANDSF_LATENCYPACKETRECIEVE);
                double latency = json.getInt(EliteWiFIConstants.ANDSF_LATENCYVALUE);
                EliteSession.eLog.i(MODULE, "Packets Sent : " + sentPackets + " Packets Receive : "
                        + receivePackets + " Latency : " + latency);

                if (receivePackets == 0 || latency == 0) {
                    EliteSession.eLog.d(MODULE, "Latecny is zero or Recieved packets is zero");
                    ANDSFUtility.callClearWiFiConnectivity(mContext);
                } else {
                    if (latency <= Double.parseDouble(sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE))) {
                        EliteSession.eLog.i(MODULE, "Ping Latency is " + latency + " will is lesser or equal to then Thresold Latency "
                                + sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE));
                        if (sptask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                            EliteSession.eLog.d(MODULE, "Enable Speed Thresold");
                            wifiapi.getDownloadUploadSpeed(ssid, this);
                        }
                    } else {
                        EliteSession.eLog.i(MODULE, "Ping Latency is " + latency + " will is greater then Thresold Latency "
                                + sptask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE));
                        ANDSFUtility.callClearWiFiConnectivity(mContext);
                    }
                }

            } else if (json.getString(EliteWiFIConstants.REQUESTID).equals(EliteWiFIConstants.FAILURE_CODE_LATENCYCHECK)) {
                EliteSession.eLog.d(MODULE, "Latency Failed Response");
                ANDSFUtility.callClearWiFiConnectivity(mContext);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWiFiScanComplete(List<String> ssidList) {

    }
}
