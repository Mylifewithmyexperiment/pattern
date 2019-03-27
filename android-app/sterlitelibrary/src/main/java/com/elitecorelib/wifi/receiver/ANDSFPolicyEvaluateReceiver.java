package com.elitecorelib.wifi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.elitecore.wifi.api.CommonWiFiUtility;
import com.elitecore.wifi.api.EliteWiFIConstants;
import com.elitecore.wifi.api.EliteWiFiAPI;
import com.elitecore.wifi.api.WifiConnectionManager;
import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecorelib.andsf.api.PolicyImplementation;
import com.elitecorelib.andsf.pojo.ANDSFAccessNetworkInformationWLAN;
import com.elitecorelib.andsf.pojo.ANDSFDiscoveryInformations;
import com.elitecorelib.andsf.pojo.ANDSFPolicies;
import com.elitecorelib.andsf.pojo.ANDSFPolicyResponse;
import com.elitecorelib.andsf.utility.ANDSFUtility;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.fcm.NotificationClass;
import com.elitecorelib.core.pojo.PojoWiFiConnection;
import com.elitecorelib.core.pojo.PojoWiFiProfile;
import com.elitecorelib.core.realm.RealmOperation;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferenceConstants;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.elitecorelib.andsf.utility.ANDSFConstant.IS_ANDSF_POLICY_WIFI_CONNECTED;

public class ANDSFPolicyEvaluateReceiver extends BroadcastReceiver implements OnWiFiTaskCompleteListner {

    private String MODULE = "ANDSFPolicyEvaluateReceiver";
    private RealmOperation realmOperation;
    private Context mContext;
    private static SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    private EliteWiFiAPI eliteWiFiAPI;
    private WifiConnectionManager wifiConnectionManager;
    private ANDSFAccessNetworkInformationWLAN wlanInfo = null;
    private String WiFiSSID = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        EliteSession.eLog.i(MODULE, "------------------Invoke Andsf Policy evaluation---------------");
        mContext = context;

        realmOperation = RealmOperation.with(mContext);
        EliteSession.eLog.d(MODULE, "getting storage policy ");

        // if this value is true then ANDSF evaluation not continue
        if (spTask.getString(CoreConstants.ANDSF_USERPREFERENCE).equals("true") && ANDSFUtility.isConnectedWithWiFi(mContext) && !CommonWiFiUtility.isAlreadyConnected(spTask.getString(IS_ANDSF_POLICY_WIFI_CONNECTED))) {
            EliteSession.eLog.i(MODULE, "ANDSF userPreference value is " + spTask.getString(CoreConstants.ANDSF_USERPREFERENCE) + " then Evaluation stop");
            return;
        } else {
            spTask.saveBoolean(CoreConstants.ANDSF_ISCONNECTED, ANDSFUtility.isConnectedWithWiFi(mContext));
        }

        ArrayList<ANDSFDiscoveryInformations> discoveryInformation = realmOperation.getALLDiscoveryInformation();
        ArrayList<ANDSFPolicies> andsfPolicies = realmOperation.getAllPolicies();

        ANDSFPolicyResponse policyResponse = new ANDSFPolicyResponse();

        if (andsfPolicies == null || andsfPolicies.size() == 0) {

            ANDSFUtility.callClearWiFiConnectivity(context);
            EliteSession.eLog.i(MODULE, "Policy list is empty");
            return;
        } else {
            policyResponse.setPolicies(andsfPolicies);

            if (discoveryInformation == null || discoveryInformation.size() == 0) {
                EliteSession.eLog.i(MODULE, "ANDSF DiscoveryInformation not Available in storage");
                ANDSFUtility.callClearWiFiConnectivity(context);
                return;
            } else {
                policyResponse.setDiscoveryInformations(discoveryInformation);
            }
            EliteSession.eLog.i(MODULE, "Invoke Policy Implementation");
        }

        boolean isValidOperator = CommonWiFiUtility.checkValidOperator(ElitelibUtility.getKeyPairvalue(CoreConstants.KEY_MCC, CoreConstants.KEY_MCC), ElitelibUtility.getKeyPairvalue(CoreConstants.KEY_MNC, CoreConstants.KEY_MNC), CoreConstants.Seprator);

        if (isValidOperator) {
            ANDSFUtility.getDeviceDetails(this.mContext, spTask.getString(SharedPreferencesConstant.CURRENT_LATITUDE), spTask.getString(SharedPreferencesConstant.CURRENT_LONGITUDE));

            EliteSession.eLog.d(MODULE, "Valid Operator");
            PolicyImplementation.setPolicyResponse(policyResponse);
            PolicyImplementation.evaluate();
            ANDSFPolicies mPolicies = PolicyImplementation.getCurrentValidPolicy();
            ArrayList<ANDSFDiscoveryInformations> discoveryInformationsList = PolicyImplementation.getCurrentValidDiscoveryInformationList();

            if (mPolicies != null) {
                EliteSession.eLog.d(MODULE, "Current Valid policy is " + mPolicies.policyName);
                if (mPolicies.ext.wifiStrength != 0) {
                    int dBm = 100 - (mPolicies.ext.wifiStrength / 2);
                    EliteSession.eLog.d(MODULE, "Signal strength in dbm:: -" + dBm);

                    spTask.saveInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL, dBm);
                    spTask.saveBoolean(SharedPreferencesConstant.ISSIGNALASSISTANCE, true);
                } else {
                    spTask.saveBoolean(SharedPreferencesConstant.ISSIGNALASSISTANCE, false);
                    spTask.saveInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL, 0);
                }

                if (mPolicies.ext.batteryLife != 0) {
                    spTask.saveBoolean(SharedPreferencesConstant.ISBETTERYTHRESHOLDENABLE, true);
                    spTask.saveInt(SharedPreferenceConstants.PROGRESS_BATTERY, mPolicies.ext.batteryLife);
                } else {
                    spTask.saveBoolean(SharedPreferencesConstant.ISBETTERYTHRESHOLDENABLE, false);
                    spTask.saveInt(SharedPreferenceConstants.PROGRESS_BATTERY, 0);
                }
            } else {
                EliteSession.eLog.d(MODULE, "Current Valid policy not found ");
                ANDSFUtility.callClearWiFiConnectivity(mContext);
            }

            if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equalsIgnoreCase("true")) {
                if (spTask.getString(CoreConstants.ANDSF_DOWNLOADSPEED).equals("0") && spTask.getString(CoreConstants.ANDSF_UPLOADSPEED).equals("0")) {
                    spTask.saveString(CoreConstants.ISSPEEDTHRESHOLDENABLE, "false");
                }
            }

//            EliteSession.eLog.d(MODULE, "speed speed threshold enable " + ElitelibUtility.getKeyPairvalue(CoreConstants.ISSPEEDTHRESHOLDENABLE, CoreConstants.ISSPEEDTHRESHOLDENABLE_VALUE));
//            if (ElitelibUtility.getKeyPairvalue(CoreConstants.ISSPEEDTHRESHOLDENABLE, CoreConstants.ISSPEEDTHRESHOLDENABLE_VALUE).equals("true")) {
//                spTask.saveBoolean(CoreConstants.ISSPEEDTHRESHOLDENABLE, true);
//            } else {
//                spTask.saveBoolean(CoreConstants.ISSPEEDTHRESHOLDENABLE, false);
//            }

//            EliteSession.eLog.d(MODULE, "Download upload speed " + ElitelibUtility.getKeyPairvalue(CoreConstants.ANDSF_DOWNLOADSPEED, CoreConstants.ANDSF_DOWNLOADSPEED_VALUE));
//            spTask.saveString(CoreConstants.ANDSF_DOWNLOADSPEED, ElitelibUtility.getKeyPairvalue(CoreConstants.ANDSF_DOWNLOADSPEED, CoreConstants.ANDSF_DOWNLOADSPEED_VALUE));
//            spTask.saveString(CoreConstants.ANDSF_UPLOADSPEED, ElitelibUtility.getKeyPairvalue(CoreConstants.ANDSF_UPLOADSPEED, CoreConstants.ANDSF_UPLOADSPEED_VALUE));

            realmOperation = RealmOperation.with(mContext);
            if (discoveryInformationsList != null && discoveryInformationsList.size() > 0) {
                ANDSFDiscoveryInformations mDiscoveryInformations = discoveryInformationsList.get(0);

                if (mDiscoveryInformations != null) {
                    wlanInfo = mDiscoveryInformations.accessNetworkInformationWLAN;
                    if (wlanInfo != null) {
                        EliteSession.eLog.d(MODULE, "Current WiFi Connection SSID is " + wlanInfo.SSIDName);
                        // Delete all wifi connection & profile
                        realmOperation.deleteAll(PojoWiFiProfile.class);
                        realmOperation.deleteAll(PojoWiFiConnection.class);

                        for (ANDSFDiscoveryInformations informations : discoveryInformationsList) {
                            ANDSFAccessNetworkInformationWLAN wlanInformation = informations.accessNetworkInformationWLAN;

                            if (wlanInformation != null) {
                                PojoWiFiConnection mPojoWiFiConnection = new PojoWiFiConnection();
                                mPojoWiFiConnection.setPreferable(true);
                                mPojoWiFiConnection.setSecurityType(wlanInformation.securityType);
                                mPojoWiFiConnection.setSsidName(wlanInformation.SSIDName);
                                mPojoWiFiConnection.setPassword(wlanInformation.password);
                                mPojoWiFiConnection.setEapType(wlanInformation.EAPTypeAuths);
                                realmOperation.insertData(mPojoWiFiConnection);
                            }
                        }

                        PojoWiFiProfile profile = new PojoWiFiProfile();
                        profile.setAndroidSettingName(mPolicies.policyName);
                        profile.setPreferable(true);
                        realmOperation.insertData(profile);

                        WiFiSSID = wlanInfo.SSIDName;
                        if (CommonWiFiUtility.isAlreadyConnected(wlanInfo.SSIDName)) {
                            EliteSession.eLog.d(MODULE, "WiFi SSID Already connected");

                            spTask.saveString(SharedPreferencesConstant.ACTIVECONNECTION, WiFiSSID);
                            spTask.saveString(SharedPreferencesConstant.ACTIVEPROFILE, mPolicies.policyName);
                            spTask.saveString(IS_ANDSF_POLICY_WIFI_CONNECTED, WiFiSSID);


                            if (spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY) != 0) {
                                if (spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY) < ANDSFUtility.getBatteryPercentage(LibraryApplication.getLibraryApplication().getLibraryContext())) {
                                    EliteSession.eLog.d(MODULE, "Battery threshold match with andsf policy");
//                                        wifiConnectionManager = new WifiConnectionManager(mContext);
                                    if (spTask.getBooleanFirstFalse(SharedPreferencesConstant.ISSIGNALASSISTANCE) == true && spTask.getInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL) != 0) {
                                        WifiManager wifiManager = LibraryApplication.getLibraryApplication().getWiFiManager();
                                        if (wifiManager.isWifiEnabled()) {
                                            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                            EliteSession.eLog.d(MODULE, "Signal for scan WiFi RSSI :: " + wifiInfo.getRssi());
                                            int wifiSignalLevel = -(spTask.getInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL) + Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER, SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER)));
                                            if (wifiInfo.getRssi() < wifiSignalLevel) {
                                                EliteSession.eLog.d(MODULE, "current signal less then server");
                                                ANDSFUtility.callClearWiFiConnectivity(mContext);
                                            } else {
                                                EliteSession.eLog.d(MODULE, "current signal greter then server");

                                               /* if (spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).isEmpty() == false ||
                                                        spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).isEmpty() == false ||
                                                        spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYURL).isEmpty() == false) {

                                                    EliteSession.eLog.i(MODULE, "Latency All Parameters set on the Serve and Latency Check Execution Start");
                                                    new ElitelibUtility.LatencyCheck(this).execute();

                                                } else {

                                                    if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                                        EliteSession.eLog.d(MODULE, "Speed Threshold enable and check to network speed call");
                                                        eliteWiFiAPI = new EliteWiFiAPI(this);
                                                        eliteWiFiAPI.getDownloadUploadSpeed(wlanInfo.SSIDName, this, LibraryApplication.getLibraryApplication().getCurrentActivity());
                                                    }
                                                }*/
                                                if (spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("") ||
                                                        spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("0") ||
                                                        spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("") ||
                                                        spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("0") ||
                                                        spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYURL).equalsIgnoreCase("")) {

                                                    if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                                        EliteSession.eLog.i(MODULE, "Latency All Parameters not set on the Serve");
                                                        if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                                            EliteSession.eLog.d(MODULE, "Speed Threshold enable and check to network speed call");
                                                            eliteWiFiAPI = new EliteWiFiAPI(this);
                                                            eliteWiFiAPI.getDownloadUploadSpeed(wlanInfo.SSIDName, this);
                                                        }
                                                    }


                                                } else {

                                                    EliteSession.eLog.i(MODULE, "Latency All Parameters set on the Serve and Latency Check Execution Start");
                                                    new ElitelibUtility.LatencyCheck(this).execute();
                                                }
                                            }
                                        }
                                    } else {
                                        EliteSession.eLog.d(MODULE, "Signal value not set from server");
                                        if (spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("") ||
                                                spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("0") ||
                                                spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("") ||
                                                spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("0") ||
                                                spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYURL).equalsIgnoreCase("")) {

                                            if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                                EliteSession.eLog.i(MODULE, "Latency All Parameters not set on the Serve");
                                                if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                                    EliteSession.eLog.d(MODULE, "Speed Threshold enable and check to network speed call");
                                                    eliteWiFiAPI = new EliteWiFiAPI(this);
                                                    eliteWiFiAPI.getDownloadUploadSpeed(wlanInfo.SSIDName, this);
                                                }
                                            }


                                        } else {

                                            EliteSession.eLog.i(MODULE, "Latency All Parameters set on the Serve and Latency Check Execution Start");
                                            new ElitelibUtility.LatencyCheck(this).execute();
                                        }

                                    }
                                } else {
                                    EliteSession.eLog.d(MODULE, "Wifi Turn off due to battery value low");
                                    ANDSFUtility.callClearWiFiConnectivity(mContext);
                                }
                            } else {

                                if (spTask.getBooleanFirstFalse(SharedPreferencesConstant.ISSIGNALASSISTANCE) == true && spTask.getInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL) != 0) {
                                    WifiManager wifiManager = LibraryApplication.getLibraryApplication().getWiFiManager();
                                    if (wifiManager.isWifiEnabled()) {
                                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                        EliteSession.eLog.d(MODULE, "Signal RSSI :: " + wifiInfo.getRssi());
                                        int wifiSignalLevel = -(spTask.getInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL) + Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER, SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER)));
                                        if (wifiInfo.getRssi() < wifiSignalLevel) {
                                            EliteSession.eLog.d(MODULE, "current signal less then server");
                                            ANDSFUtility.callClearWiFiConnectivity(mContext);
                                        } else {
                                            EliteSession.eLog.d(MODULE, "current signal greter then server");

                                            if (spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("") ||
                                                    spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("0") ||
                                                    spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("") ||
                                                    spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("0") ||
                                                    spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYURL).equalsIgnoreCase("")) {

                                                if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                                    EliteSession.eLog.i(MODULE, "Latency All Parameters not set on the Serve");
                                                    if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                                        EliteSession.eLog.d(MODULE, "Speed Threshold enable and check to network speed call");
                                                        eliteWiFiAPI = new EliteWiFiAPI(this);
                                                        eliteWiFiAPI.getDownloadUploadSpeed(wlanInfo.SSIDName, this);
                                                    }
                                                }


                                            } else {

                                                EliteSession.eLog.i(MODULE, "Latency All Parameters set on the Serve and Latency Check Execution Start");
                                                new ElitelibUtility.LatencyCheck(this).execute();
                                            }

                                        }
                                    }
                                } else {
                                    if (spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("") ||
                                            spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYPINGCOUNT).equalsIgnoreCase("0") ||
                                            spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("") ||
                                            spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE).equalsIgnoreCase("0") ||
                                            spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYURL).equalsIgnoreCase("")) {

                                        if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                            EliteSession.eLog.i(MODULE, "Latency All Parameters not set on the Serve");
                                            if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                                                EliteSession.eLog.d(MODULE, "Speed Threshold enable and check to network speed call");
                                                eliteWiFiAPI = new EliteWiFiAPI(this);
                                                eliteWiFiAPI.getDownloadUploadSpeed(wlanInfo.SSIDName, this);
                                            }
                                        }


                                    } else {

                                        EliteSession.eLog.i(MODULE, "Latency All Parameters set on the Serve and Latency Check Execution Start");
                                        new ElitelibUtility.LatencyCheck(this).execute();
                                    }

                                }
                            }

                        } else {
                            EliteSession.eLog.d(MODULE, "User not Connected to -" + wlanInfo.SSIDName);
                            if (wlanInfo.autoJoin == true) {
                                EliteSession.eLog.d(MODULE, "Auto join enable in policy: : " + spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY));
                                EliteSession.eLog.d(MODULE, "get Device battery:: " + ANDSFUtility.getBatteryPercentage(LibraryApplication.getLibraryApplication().getLibraryContext()) + " Server Battery is " + mPolicies.ext.batteryLife);

                                if (spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY) != 0) {
                                    if (spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY) < ANDSFUtility.getBatteryPercentage(LibraryApplication.getLibraryApplication().getLibraryContext())) {
                                        EliteSession.eLog.d(MODULE, "Battery threshold match with andsf policy");
                                        wifiConnectionManager = new WifiConnectionManager(mContext);
                                    } else {
                                        ANDSFUtility.callClearWiFiConnectivity(mContext);
                                    }
                                } else {
                                    EliteSession.eLog.d(MODULE, "Battery threshold not configured or 0 in server policy, Start wifi connectivity");
                                    wifiConnectionManager = new WifiConnectionManager(mContext);
                                }
                            } else {
                                EliteSession.eLog.d(MODULE, "Auto join disable in policy:: " + spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY));
                                EliteSession.eLog.d(MODULE, "get Device battery:: " + ANDSFUtility.getBatteryPercentage(LibraryApplication.getLibraryApplication().getLibraryContext()) + " Server Battery is " + mPolicies.ext.batteryLife);

                                if (spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY) != 0) {
                                    if (spTask.getInt(SharedPreferenceConstants.PROGRESS_BATTERY) < ANDSFUtility.getBatteryPercentage(LibraryApplication.getLibraryApplication().getLibraryContext())) {
                                        EliteSession.eLog.d(MODULE, "Battery match with from andsf policy server");
                                        NotificationClass.getIntance(LibraryApplication.getLibraryApplication().getLibraryContext()).showNotification(ElitelibUtility.getKeyPairvalue(CoreConstants.rangeJioMessage, CoreConstants.range_jio_wifi));
                                    } else {
                                        EliteSession.eLog.d(MODULE, "Battery threshold not match with from andsf policy server, Start wifi connectivity");
                                        ANDSFUtility.callClearWiFiConnectivity(mContext);
                                    }
                                } else {
                                    EliteSession.eLog.d(MODULE, "Battery threshold not configured or 0 in server policy");
                                    List<ScanResult> wifiScanResults = null;
                                    if (spTask.getBooleanFirstFalse(SharedPreferencesConstant.ISSIGNALASSISTANCE)) {
                                        WifiManager wifiManager = LibraryApplication.getLibraryApplication().getWiFiManager();
                                        while (wifiScanResults == null) {
                                            wifiScanResults = wifiManager.getScanResults();
                                        }

                                        if (wifiScanResults != null) {
                                            int numberOfWifiNetworks = wifiScanResults.size();
                                            int availableNetworks = numberOfWifiNetworks - 1;

                                            int lastMatchSingnalStrenth = 0;

                                            while (availableNetworks >= 0) {
                                                if ((wifiScanResults.get(availableNetworks).SSID).equals(wlanInfo.SSIDName)) {
                                                    EliteSession.eLog.d(MODULE, "Scan WiFi Match Signal Strenth is=" + (wifiScanResults.get(availableNetworks).level));

                                                    if (lastMatchSingnalStrenth == 0) {
                                                        lastMatchSingnalStrenth = wifiScanResults.get(availableNetworks).level;
                                                    } else {
                                                        if (lastMatchSingnalStrenth < wifiScanResults.get(availableNetworks).level) {
                                                            lastMatchSingnalStrenth = wifiScanResults.get(availableNetworks).level;
                                                        }
                                                    }
                                                }
                                                availableNetworks--;
                                            }
                                            int requiredSignalLevel = -(spTask.getInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL));

                                            EliteSession.eLog.d(MODULE, "final Scan WiFi Signal Strenth is =" + lastMatchSingnalStrenth);
                                            EliteSession.eLog.d(MODULE, "Server WiFi Signal Strenth is=" + spTask.getInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL));
                                            EliteSession.eLog.d(MODULE, "Additional WiFi Signal Strenth is=" + Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER, SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER)));
                                            EliteSession.eLog.d(MODULE, "Policy WiFi Signal Strenth is=" + requiredSignalLevel);

                                            if (lastMatchSingnalStrenth < requiredSignalLevel) {
                                                EliteSession.eLog.d(MODULE, "signal strength not match with ANDSF policy server");
                                                ANDSFUtility.callClearWiFiConnectivity(mContext);
                                            } else {
                                                EliteSession.eLog.d(MODULE, "Signal strength match with from andsf policy server");
                                                NotificationClass.getIntance(LibraryApplication.getLibraryApplication().getLibraryContext()).showNotification(ElitelibUtility.getKeyPairvalue(CoreConstants.rangeJioMessage, CoreConstants.range_jio_wifi));
                                            }
                                        } else {
                                            EliteSession.eLog.d(MODULE, "WiFi Scan Result not found");
                                        }
                                    } else {
                                        EliteSession.eLog.d(MODULE, "ISSIGNALASSISTANCE Not Set From server policy");
                                        NotificationClass.getIntance(LibraryApplication.getLibraryApplication().getLibraryContext()).showNotification(ElitelibUtility.getKeyPairvalue(CoreConstants.rangeJioMessage, CoreConstants.range_jio_wifi));
                                    }
                                }
                            }
                        }

                    }
                }
            }


        } else {
            EliteSession.eLog.d(MODULE, "Not Valid Operator");
        }

    }

    @Override
    public void onWiFiTaskComplete(String result) {

    }

    @Override
    public void isWiFiInRange(boolean status) {

    }

    @Override
    public void getResponseData(String responseData) {
        try {
            JSONObject mJsonObject = new JSONObject(responseData);
            EliteSession.eLog.d(MODULE, "response :: " + responseData);

            if (mJsonObject.getString(EliteWiFIConstants.REQUESTID).equals("" + EliteWiFIConstants.SUCCESS_CODE_LATENCYCHECK)) {
                EliteSession.eLog.d(MODULE, "Latency Success Response");
                int sentPackets = mJsonObject.getInt(EliteWiFIConstants.ANDSF_LATENCYPACKETSENT);
                int receivePackets = mJsonObject.getInt(EliteWiFIConstants.ANDSF_LATENCYPACKETRECIEVE);
                double latency = mJsonObject.getInt(EliteWiFIConstants.ANDSF_LATENCYVALUE);
                EliteSession.eLog.i(MODULE, "Packets Sent : " + sentPackets + " Packets Receive : "
                        + receivePackets + " Latency : " + latency);

                if (receivePackets == 0 || latency == 0) {
                    EliteSession.eLog.d(MODULE, "Latecny is zero or Recieved packets is zero");
                    ANDSFUtility.callClearWiFiConnectivity(mContext);
                } else {
                    if (latency <= Double.parseDouble(spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE))) {
                        EliteSession.eLog.i(MODULE, "Ping Latency is " + latency + " will is lesser or equal to then Thresold Latency "
                                + spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE));
                        if (spTask.getString(CoreConstants.ISSPEEDTHRESHOLDENABLE).equals("true")) {
                            EliteSession.eLog.d(MODULE, "Enable Speed Thresold");
                            eliteWiFiAPI = new EliteWiFiAPI(this);
                            eliteWiFiAPI.getDownloadUploadSpeed(WiFiSSID, ANDSFPolicyEvaluateReceiver.this);
                        }
                    } else {
                        EliteSession.eLog.i(MODULE, "Ping Latency is " + latency + " will is greater then Thresold Latency "
                                + spTask.getString(SharedPreferencesConstant.ANDSF_LATENCYTHRESOLDVALUE));
                        ANDSFUtility.callClearWiFiConnectivity(mContext);
                    }

                }

            } else if (mJsonObject.getString(EliteWiFIConstants.REQUESTID).equals(EliteWiFIConstants.FAILURE_CODE_LATENCYCHECK)) {
                EliteSession.eLog.d(MODULE, "Latency Failed Response");
                ANDSFUtility.callClearWiFiConnectivity(mContext);
            } else if (mJsonObject.getString(EliteWiFIConstants.REQUESTID).equals("2005")) {
                EliteSession.eLog.d(MODULE, "Upload Download speed response data:: " + responseData);

                Double mDoubleDown = Double.parseDouble(mJsonObject.getString("Download_speed"));
                Double mDoubleUp = Double.parseDouble(mJsonObject.getString("Upload_speed"));

                EliteSession.eLog.d(MODULE, "Network data Download Speed: " + mDoubleDown + " Upload Speed: " + mDoubleUp);
                EliteSession.eLog.d(MODULE, "server Download speed is : " + spTask.getString(CoreConstants.ANDSF_DOWNLOADSPEED) + " Upload is : " + spTask.getString(CoreConstants.ANDSF_UPLOADSPEED));

                if (spTask.getString(CoreConstants.ANDSF_DOWNLOADSPEED) != null && !spTask.getString(CoreConstants.ANDSF_DOWNLOADSPEED).equals("") && spTask.getString(CoreConstants.ANDSF_UPLOADSPEED) != null && !spTask.getString(CoreConstants.ANDSF_UPLOADSPEED).equals("")) {

                    if (!spTask.getString(CoreConstants.ANDSF_DOWNLOADSPEED).equals("0") && !spTask.getString(CoreConstants.ANDSF_UPLOADSPEED).equals("0")) {
                        EliteSession.eLog.d(MODULE, "Download Upload speed not set 0");
                        if (mDoubleDown >= Integer.parseInt(spTask.getString(CoreConstants.ANDSF_DOWNLOADSPEED)) && mDoubleUp >= Integer.parseInt(spTask.getString(CoreConstants.ANDSF_UPLOADSPEED))) {
                            EliteSession.eLog.d(MODULE, "Network data Speed gretter then configured in preference, wifi is on");
                        } else {
                            EliteSession.eLog.d(MODULE, "Network data Speed lower then configured in preference, wifi to be off automatically");
                            ANDSFUtility.callClearWiFiConnectivity(mContext);
                        }
                    } else if (spTask.getString(CoreConstants.ANDSF_DOWNLOADSPEED).equals("0")) {
                        EliteSession.eLog.d(MODULE, "Download speed is 0");
                        if (mDoubleUp >= Integer.parseInt(spTask.getString(CoreConstants.ANDSF_UPLOADSPEED))) {
                            EliteSession.eLog.d(MODULE, "Network data Speed gretter then configured in preference, wifi is on");
                        } else {
                            EliteSession.eLog.d(MODULE, "Network data Speed lower then configured in preference, wifi to be off automatically");
                            ANDSFUtility.callClearWiFiConnectivity(mContext);
                        }
                    } else if (spTask.getString(CoreConstants.ANDSF_UPLOADSPEED).equals("0")) {
                        EliteSession.eLog.d(MODULE, "Upload speed is 0");
                        if (mDoubleDown >= Integer.parseInt(spTask.getString(CoreConstants.ANDSF_DOWNLOADSPEED))) {
                            EliteSession.eLog.d(MODULE, "Network data Speed gretter then configured in preference, wifi is on");
                        } else {
                            EliteSession.eLog.d(MODULE, "Network data Speed lower then configured in preference, wifi to be off automatically");
                            ANDSFUtility.callClearWiFiConnectivity(mContext);
                        }
                    }
                }
            } else if (mJsonObject.getString(EliteWiFIConstants.REQUESTID).equals("2004")) {
                EliteSession.eLog.d(MODULE, "Unable to get Upload Download speed response data");
            }

        } catch (JSONException e) {
            EliteSession.eLog.e(MODULE, "Error : " + e.getMessage());
        }
    }

    @Override
    public void onWiFiScanComplete(List<String> ssidList) {

    }
}
