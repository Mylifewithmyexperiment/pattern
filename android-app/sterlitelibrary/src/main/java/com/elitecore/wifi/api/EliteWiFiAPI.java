package com.elitecore.wifi.api;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TrafficStats;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.elitecore.elitesmp.api.EliteSMPAPI;
import com.elitecore.elitesmp.api.EliteSMPConstants;
import com.elitecore.elitesmp.api.IEliteSMPAPI;
import com.elitecore.elitesmp.listener.OnEliteSMPTaskCompleteListner;
import com.elitecore.elitesmp.pojo.Plan;
import com.elitecore.elitesmp.utility.ElitePropertiesUtil;
import com.elitecore.elitesmp.utility.EliteSMPUtilConstants;
import com.elitecore.elitesmp.utility.EliteSMPUtility;
import com.elitecore.wifi.listener.OnInternetCheckCompleteListner;
import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecore.wifi.pojo.PojoConnection;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.dao.DBOperation;
import com.elitecorelib.core.dao.DBQueryFieldConstants;
import com.elitecorelib.core.interfaces.AnalyticId;
import com.elitecorelib.core.pojo.PojoSubscriber;
import com.elitecorelib.core.pojo.PojoWifiAutoLogin;
import com.elitecorelib.core.pojo.PojoWifiInformation;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;
import com.elitecorelib.core.services.InterNetAvailabilityCheckTask;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferenceConstants;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.elitecorelib.wifi.api.WiFiConstants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.elitecorelib.core.CoreConstants.GRADLE_SMP_SERVER_URL;
import static com.elitecorelib.core.utility.ElitelibUtility.getMetaDataValue;

public class EliteWiFiAPI implements IWiFiConfiguration, ConnectionManagerCompleteListner, OnInternetCheckCompleteListner {


    private static String MODULE = "EliteWiFiAPI";
    private static SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();
    PojoSubscriber subscriberData;
    private boolean isInRange = false;
    private BroadcastReceiver wifiReciver;
    private DBOperation dbOperation;
    private boolean NETWORKNEGATIVE = false;
    private boolean considerEAP;
    private boolean isWifiReset = false;
    private OnWiFiTaskCompleteListner wifiTaskCompleteListener;


    public EliteWiFiAPI(OnWiFiTaskCompleteListner wifiTaskCompleteListner) {
        if (wifiTaskCompleteListner != null)
            this.wifiTaskCompleteListener = wifiTaskCompleteListner;
        subscriberData = new PojoSubscriber();
    }

    private static String getCallingMethodName() {
        try {
            EliteSession.eLog.i(MODULE, " getting value for calling methods");
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            if (stackTraceElements.length >= 4) {
                return stackTraceElements[3].getMethodName();
            } else {
                return stackTraceElements[2].getMethodName();
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Error while getting method name " + e.getMessage());

        }
        return "";
    }

    public void doRegister() {
        if (spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) == false) {
            EliteSession.eLog.d(MODULE, " Registration process");
            subscriberData = ElitelibUtility.setUserRegistrationInformation(subscriberData, LibraryApplication.getLibraryApplication().getLibraryContext());
            subscriberData.setRegisterWith(CoreConstants.REGISTERWITH);
            try {
                Gson gson = new Gson();
                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(EliteWiFiAPI.this,
                        CoreConstants.MONETIZATION_REGISTERUSERPROFILE_REQUESTID);
                task.execute(gson.toJson(subscriberData), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() +
                        CoreConstants.MONETIZATION_REGISTERUSERPROFILE);
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, " Registration request error " + e.getMessage());
            }
        }
    }

    @Override
    public void doRegistration(String sharedKey, PojoSubscriber subscriber) {
        try {
            if (sharedKey != null && !sharedKey.equals("")) {
                EliteSession.eLog.d(MODULE, "doRegistration Shared key not null or blank");
                ElitelibUtility.setSecretKey(sharedKey);
                EliteSession.eLog.d(MODULE, " Registration process");

                subscriber = ElitelibUtility.setUserRegistrationInformation(subscriber);
                subscriber.setRegisterWith(LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationInfo().loadLabel(LibraryApplication.getLibraryApplication().getLibraryContext().getPackageManager()) + "");

                try {
                    String monetization_URL = null;

                    if (spTask.getString(CoreConstants.MONETIZATION_URL) == null || spTask.getString(CoreConstants.MONETIZATION_URL).equals("")) {
                        EliteSession.eLog.d(MODULE,"monetization url getting from Config Property");
                        monetization_URL = ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.VALUE_MONETIZATION_URL);
                    } else {
                        monetization_URL  = spTask.getString(CoreConstants.MONETIZATION_URL);
                        EliteSession.eLog.d(MODULE,"monetization url getting from SPTask");
                    }
                    Gson gson = new Gson();
                    ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(EliteWiFiAPI.this, EliteWiFIConstants.GETMONETIZATIONFORWIFI);
                    task.execute(gson.toJson(subscriber), monetization_URL + CoreConstants.MONETIZATION_REGISTERUSERPROFILE);
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, " Registration request error " + e.getMessage());
                    JSONObject mObject = new JSONObject();
                    try {
                        mObject.put(EliteWiFIConstants.RESPONSECODE, EliteWiFIConstants.FAILURECODE);
                        mObject.put(EliteWiFIConstants.RESPONSEMESSAGE, CoreConstants.Request_Timeout);
                    } catch (JSONException Je) {
                        EliteSession.eLog.e(MODULE, Je.getMessage());
                    }
                    getWifiTaskCompleteListener().getResponseData(mObject.toString());
                }
            } else {
                EliteSession.eLog.d(MODULE, "doRegistration Shared key null or blank");
                JSONObject mObject = new JSONObject();
                try {
                    mObject.put(EliteWiFIConstants.RESPONSECODE, EliteWiFIConstants.FAILURECODE);
                    mObject.put(EliteWiFIConstants.RESPONSEMESSAGE, CoreConstants.Sharedkey_mandatory);
                } catch (JSONException Je) {
                    EliteSession.eLog.e(MODULE, Je.getMessage());
                }
                getWifiTaskCompleteListener().getResponseData(mObject.toString());
            }

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Registration error " + e.getMessage());
        }
    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestId) {
        if (result != null && requestId == CoreConstants.MONETIZATION_REGISTERUSERPROFILE_REQUESTID) {
            EliteSession.eLog.d(MODULE, " Registration request invoked");
            if (result.trim().compareTo("") != 0) {
                EliteSession.eLog.i(MODULE, " Registration response found " + result);
                Gson gson = new Gson();
                PojoConfigModelResponse resObj = gson.fromJson(result, PojoConfigModelResponse.class);
                if (resObj.getResponseCode() == 1) {
                    spTask.saveBoolean(CoreConstants.DO_REGISTER, true);
                }
            }
        } else if (result != null && requestId == EliteWiFIConstants.AUTO_DETECT) {
            if (result.compareTo(CoreConstants.INTERNET_CHECK_SUCCESS) == 0) {
                EliteSession.eLog.d(MODULE, "internet available >>");

            } else {
                EliteSession.eLog.d(MODULE, "internet not available >>");
            }

            // QTL library Monetization registration response
        } else if (requestId == EliteWiFIConstants.GETMONETIZATIONFORWIFI) {
            if (result != null) {
                EliteSession.eLog.d(MODULE, "Monetization registration invoked");
                // TODO
                Gson gson = new Gson();
                com.elitecorelib.core.pojo.PojoConfigModelResponse resObj = gson.fromJson(result, com.elitecorelib.core.pojo.PojoConfigModelResponse.class);
                if (resObj.getResponseCode() == 1) {
                    EliteSession.eLog.d(MODULE, "Monetization registration successfully invoked");
                    JSONObject mJsonObject = new JSONObject();
                    try {
                        mJsonObject.put(EliteWiFIConstants.RESPONSECODE, EliteWiFIConstants.SUCCESSCODE);
                        mJsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE, resObj.getResponseMessage());
                        mJsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.GETMONETIZATIONFORWIFI);
                        mJsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                        mJsonObject.put(CoreConstants.USERIDENTITY, resObj.getResponceData().getUserIdentity());

                        spTask.saveString(CoreConstants.USERIDENTITY, resObj.getResponceData().getUserIdentity());
                        spTask.saveInt(CoreConstants.EVENTANALYTICSENABLE, resObj.getResponceData().getEventAnalyticsEnable());
                        spTask.saveInt(CoreConstants.EVENTANALYTICSINTERVAL, resObj.getResponceData().getEventAnalyticsInterval());
                        spTask.saveString(CoreConstants.EVENTANALYTICSMODE, resObj.getResponceData().getEventAnalyticsMode());

                        ElitelibUtility.eventAnalyticProcess(LibraryApplication.getLibraryApplication().getLibraryContext());

                        getWifiTaskCompleteListener().getResponseData(mJsonObject.toString());
                    } catch (JSONException e) {
                        EliteSession.eLog.e(MODULE, e.getMessage());
                        JSONObject mObject = new JSONObject();
                        try {
                            mObject.put(EliteWiFIConstants.RESPONSECODE, EliteWiFIConstants.FAILURECODE);
                            mObject.put(EliteWiFIConstants.RESPONSEMESSAGE, CoreConstants.Request_Timeout);
                        } catch (JSONException Je) {
                            EliteSession.eLog.e(MODULE, Je.getMessage());
                        }
                        getWifiTaskCompleteListener().getResponseData(mObject.toString());
                    }
                } else {
                    EliteSession.eLog.d(MODULE, "Monetization registration invoked with resultcode :: " + resObj.getResponseCode());
                    JSONObject mObject = new JSONObject();
                    try {
                        mObject.put(EliteWiFIConstants.RESPONSECODE, resObj.getResponseCode());
                        mObject.put(EliteWiFIConstants.RESPONSEMESSAGE, resObj.getResponseMessage());
                    } catch (JSONException e) {
                        EliteSession.eLog.e(MODULE, e.getMessage());
                    }
                    getWifiTaskCompleteListener().getResponseData(mObject.toString());
                }
            } else {
                EliteSession.eLog.d(MODULE, "Monetization registration invoked with result :: null");
                JSONObject mObject = new JSONObject();
                try {
                    mObject.put(EliteWiFIConstants.RESPONSECODE, EliteWiFIConstants.FAILURECODE);
                    mObject.put(EliteWiFIConstants.RESPONSEMESSAGE, CoreConstants.Request_Timeout);
                } catch (JSONException Je) {
                    EliteSession.eLog.e(MODULE, Je.getMessage());
                }
                getWifiTaskCompleteListener().getResponseData(mObject.toString());
            }
        }
    }

    @Override
    public void getAllWifiSSID() throws Exception {
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        EliteSession.eLog.d(MODULE, " getAllWifiSSID method invoked");
        try {
            methodValidation("getAllWifiSSID");
            int permissionCheck1 = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            EliteSession.eLog.d("PERMISSION", " Manifest.permission.ACCESS_COARSE_LOCATION " + permissionCheck1);

            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            if (!wifiManager.isWifiEnabled()) {
                CommonWiFiUtility.turnOnWiFi(wifiManager);
            }
            wifiManager.startScan();

            List<ScanResult> scanResults = wifiManager.getScanResults();
            if (scanResults != null) {
                String json = new Gson().toJson(scanResults);
                try {
                    JSONArray mJSONArray = new JSONArray(json);
                    JSONArray mJsonArrayList = new JSONArray();
                    if (mJSONArray != null && mJSONArray.length() > 0) {
                        for (int i = 0; i < mJSONArray.length(); i++) {
                            EliteSession.eLog.d(MODULE, " WiFi in range are, " + mJSONArray.getJSONObject(i).getString("SSID"));
                            JSONObject mJsonObject = new JSONObject();
                            mJsonObject.put(EliteWiFIConstants.WIFI_BSSID, mJSONArray.getJSONObject(i).getString(EliteWiFIConstants.WIFI_BSSID));
                            mJsonObject.put(EliteWiFIConstants.WIFI_SSID, mJSONArray.getJSONObject(i).getString(EliteWiFIConstants.WIFI_SSID));
                            mJsonObject.put(EliteWiFIConstants.WIFI_CAPABILITIES, mJSONArray.getJSONObject(i).getString(EliteWiFIConstants.WIFI_CAPABILITIES).replaceAll("[\\[\\](){}]", ""));
                            mJsonObject.put(EliteWiFIConstants.WIFI_FREQUENCY, mJSONArray.getJSONObject(i).getString(EliteWiFIConstants.WIFI_FREQUENCY));
                            mJsonObject.put(EliteWiFIConstants.WIFI_LEVEL, mJSONArray.getJSONObject(i).getString(EliteWiFIConstants.WIFI_LEVEL));
                            mJsonObject.put(EliteWiFIConstants.WIFI_UNTRUSTED, mJSONArray.getJSONObject(i).getString(EliteWiFIConstants.WIFI_UNTRUSTED));
                            mJsonArrayList.put(mJsonObject);
                        }
                        JSONObject mObject = new JSONObject();
                        try {
                            mObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.SUCCESS_CODE_ALLSSID);
                            mObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.SUCCESS_MESSAGE_ALLSSID);
                            mObject.put(EliteWiFIConstants.RESPONSEDATA, mJsonArrayList);
                        } catch (JSONException Je) {
                            EliteSession.eLog.e(MODULE, Je.getMessage());
                        }
                        getWifiTaskCompleteListener().getResponseData(mObject.toString());
                    }

                } catch (JSONException e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }

            }
        } catch (NoClassDefFoundError ie) {
            EliteSession.eLog.e(MODULE, ie.getMessage());
            JSONObject mObject = new JSONObject();
            try {
                mObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_ALLSSID);
                mObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_ALLSSID);
            } catch (JSONException Je) {
                EliteSession.eLog.e(MODULE, Je.getMessage());
            }
            getWifiTaskCompleteListener().getResponseData(mObject.toString());
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
            JSONObject mObject = new JSONObject();
            try {
                mObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_ALLSSID);
                mObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_ALLSSID);
            } catch (JSONException Je) {
                EliteSession.eLog.e(MODULE, Je.getMessage());
            }
            getWifiTaskCompleteListener().getResponseData(mObject.toString());
        }

    }

    @Override
    public void getDownloadUploadSpeed(String ssidName, OnWiFiTaskCompleteListner wifiTaskCompleteListener) {

        if (ssidName == null || ssidName.equals("")) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_SSIDEMPTY);
                jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_SSIDEMPTY);
            } catch (JSONException je) {
                EliteSession.eLog.e(MODULE, je.getMessage());
            }
            wifiTaskCompleteListener.getResponseData(jsonObject.toString());
        }

//        LibraryApplication.getLibraryApplication().setActivity(currentActivity);
        if (CommonWiFiUtility.isAlreadyConnected(ssidName) && EliteSMPUtility.getIPAddress(LibraryApplication.getLibraryApplication().getLibraryContext()) != null && EliteSMPUtility.getIPAddress(LibraryApplication.getLibraryApplication().getLibraryContext()).compareTo("0.0.0.0") != 0) {
            EliteSession.eLog.d(MODULE, "Already connected with SSID " + ssidName);
            ElitelibUtility.addAnalytic(AnalyticId.WiFi_Connection, AnalyticId.WiFi_Connection_value, ssidName, WiFiConstants.ALREADYCONNECTED);

            new InterNetAvailabilityCheckTask(CoreConstants.MONETIZATION_GETINTERNET_DOWNUPSPEED_REQUESTID, this, CoreConstants.INTERNET_CHECK_URL).execute();

        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_SSIDNOTCONNECTED);
                jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_SSIDNOTCONNECTED);
            } catch (JSONException je) {
                EliteSession.eLog.e(MODULE, je.getMessage());
            }
            wifiTaskCompleteListener.getResponseData(jsonObject.toString());
        }

    }

    @Override
    public void isInterNetAvailable(int requestId, String status, String json) {
        if (requestId == CoreConstants.MONETIZATION_GETINTERNET_DOWNUPSPEED_REQUESTID) {
            if (status.compareTo(CoreConstants.INTERNET_CHECK_SUCCESS) == 0) {
                new ElitelibUtility.DownloadSpeedTest(this.wifiTaskCompleteListener).execute();
            } else {
                EliteSession.eLog.d(MODULE, "Internet not available");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_INTERNETNOTAVAILABLE);
                    jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.FAILURE_CODE_INTERNETNOTAVAILABLE);
                } catch (JSONException je) {
                    EliteSession.eLog.e(MODULE, je.getMessage());
                }
                this.wifiTaskCompleteListener.getResponseData(jsonObject.toString());
            }
        }
    }


    @Override
    public void connectToWiFi(final Context context, final PojoConnection connection, final OnWiFiTaskCompleteListner wifiTaskCompleteListener, final boolean keepWiFiOn, final boolean delteOnTurnOffWiFi) throws Exception {
        EliteSession.eLog.d(MODULE, " connectToWiFi invoked >>>> " + spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER));
        try {
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(context))
//                throw new Exception("Application ID is not Valid");
//
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("connectToWiFi");

            if (connection != null) {
                EliteSession.eLog.d(MODULE, "IS sim operator " + connection.isCheckOperator());
                if (connection.isCheckOperator() == true) {
                    TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telephony.getSimOperatorName() != null)
                        EliteSession.eLog.d(MODULE, "SIM OPerator is " + telephony.getSimOperatorName());
                    if (telephony.getSimOperatorName() != null && (!telephony.getSimOperatorName().contains(connection.getSim_operator_name()))) {
                        EliteSession.eLog.d(MODULE, "Sim operator not match with Device sim");
                        ElitelibUtility.addAnalytic(AnalyticId.WiFi_Connection, AnalyticId.WiFi_Connection_value, connection.getSsid(), WiFiConstants.NOTVALIDOPERATOR);
                        wifiTaskCompleteListener.onWiFiTaskComplete(WiFiConstants.NOTVALIDOPERATOR);
                        return;
                    }
                }
            }

            try {
                EliteSession.eLog.d(MODULE, "Checking current connectivity");
                if (CommonWiFiUtility.isAlreadyConnected(connection.getSsid()) && EliteSMPUtility.getIPAddress(context) != null && EliteSMPUtility.getIPAddress(context).compareTo("0.0.0.0") != 0) {
                    EliteSession.eLog.d(MODULE, "Already connected with SSID " + connection.getSsid());
                    ElitelibUtility.addAnalytic(AnalyticId.WiFi_Connection, AnalyticId.WiFi_Connection_value, connection.getSsid(), WiFiConstants.ALREADYCONNECTED);
                    wifiTaskCompleteListener.onWiFiTaskComplete(WiFiConstants.ALREADYCONNECTED);
                    return;
                }

            } catch (Exception e) {

                EliteSession.eLog.e(MODULE, " Error while checking connectivity:" + e.getMessage());
            }
            Thread connectinThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    EliteSession.eLog.d(MODULE, " ==============Input WiFi Connection Parameters Sent By user======================");

                    EliteSession.eLog.d(MODULE, " " + ElitelibUtility.pozotoJSONObject(connection).toString());
                    IEliteWiFiconfigurator config;
                    int networkId = -1;
                    try {
                        config = WiFISettingsFactory.getWiFiSettings(connection);
                        networkId = config.createWiFiSettings(context, connection);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && networkId == -1) {
                            EliteSession.eLog.d(MODULE, "Marshmallow already configured network");
                            networkId = CommonWiFiUtility.getExistingNetworkId(connection.getSsid());

                        }
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, e.getLocalizedMessage());
                    }

                    EliteSession.eLog.d(MODULE, " Network ID : " + networkId);
                    if (networkId != -1) {
                        NETWORKNEGATIVE = false;
                        if (delteOnTurnOffWiFi) {
                            LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveBoolean(SharedPreferenceConstants.DELETE_AFTER_WIFI_OFF, true);

                        }
                        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(SharedPreferenceConstants.SSIDNAME, connection.getSsid());
                        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveBoolean(SharedPreferenceConstants.ISWIFISETTINGSDELETED, false);
                        EliteSession.eLog.d(MODULE, " invoke proecess for WiFI connection");
                        WIFIConnection.getWIFIConnection().doWiFiConnection(context, networkId, connection, wifiTaskCompleteListener, keepWiFiOn);
                    } else {
                        NETWORKNEGATIVE = true;
                        if (!keepWiFiOn) {
                            EliteSession.eLog.d(MODULE, "Keep wifi on is false, turning off the WIFi");
                            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                            CommonWiFiUtility.turnOffWiFi(wifiManager);
                        }

                        ElitelibUtility.addAnalytic(AnalyticId.WiFi_Connection, AnalyticId.WiFi_Connection_value, connection.getSsid(), WiFiConstants.NOTCONNECTED, networkId + "");
                        wifiTaskCompleteListener.onWiFiTaskComplete(WiFiConstants.NOTCONNECTED);
                    }
                }
            });
            connectinThread.start();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Error while creating Network ID:" + e.getMessage());
        }
    }

    @Override
    public boolean removeWiFiSetting(String ssidname) {
        boolean status = false;
        try {
            EliteSession.eLog.d(MODULE, " removeWiFiSetting executed");
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !checkLicense())
//                throw new Exception("Application ID is not Valid");
//
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("removeWiFiSetting");

            if (ssidname != null && ssidname.compareTo("") != 0) {
                WifiManager wifiManager = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                status = CommonWiFiUtility.removeNetwork(wifiManager, ssidname);
            }
            EliteSession.eLog.d(MODULE, " removeWiFiSetting done");
            return status;

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Error while removeWiFiSetting:" + e.getMessage());
        }
        return status;
    }

    @Override
    public void isWiFiInRange(final OnWiFiTaskCompleteListner wifiTaskCompleteListener, final String ssidName) throws Exception {

        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        EliteSession.eLog.d(MODULE, " isWiFiInRange method invoked");
//        if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(context))
//            throw new Exception("Application ID is not Valid");
//
//        if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//            throw new Exception(CoreConstants.NOT_REGISTER);
//        }
        methodValidation("isWiFiInRange");

        try {

            int permissionCheck1 = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            EliteSession.eLog.d("PERMISSION", " Manifest.permission.ACCESS_COARSE_LOCATION " + permissionCheck1);
            if (ssidName != null && ssidName.trim().compareTo("") != 0) {

                isInRange = false;

                final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                if (!wifiManager.isWifiEnabled()) {
//                Thread thread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        EliteSession.eLog.d(MODULE, "Call WiFi On, Backgroud thread start.");
                    CommonWiFiUtility.turnOnWiFi(wifiManager);
                    ElitelibUtility.addAnalytic(AnalyticId.WiFi_Status, AnalyticId.WiFi_Status_value, "ON", ssidName);

//                    }
//                });
//                thread.start();
                }
                wifiManager.startScan();
                wifiReciver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        List<ScanResult> scanResults = wifiManager.getScanResults();
                        try {
                            if (scanResults != null) {
                                String json = new Gson().toJson(scanResults);
                                try {
                                    JSONArray mJSONArray = new JSONArray(json);
                                    if (mJSONArray != null && mJSONArray.length() > 0) {
                                        for (int i = 0; i < mJSONArray.length(); i++) {
                                            EliteSession.eLog.d(MODULE, " WiFi in range are, " + mJSONArray.getJSONObject(i).getString("SSID"));
                                        }
                                    }

                                } catch (JSONException e) {
                                    EliteSession.eLog.e(MODULE, e.getMessage());
                                }
                            }
                        } catch (NoClassDefFoundError ie) {
                            EliteSession.eLog.e(MODULE, ie.getMessage());
                        } catch (Exception e) {
                            EliteSession.eLog.e(MODULE, e.getMessage());
                        }
                        for (int i = 0; i < scanResults.size(); i++) {
                            ScanResult scanResult = scanResults.get(i);
                            if (scanResult.SSID.equals(ssidName)) {
                                EliteSession.eLog.d(MODULE, " " + scanResult.SSID);
                                EliteSession.eLog.d(MODULE, " " + scanResult.level + " is signal Level");
                                spTask.saveInt(SharedPreferencesConstant.KEY_SIGNALLEVEL, scanResult.level);
                                isInRange = true;
                                break;
                            }
                        }

                        ElitelibUtility.addAnalytic(AnalyticId.WiFi_Range, AnalyticId.WiFi_Range_value, ssidName, isInRange + "");
                        if (isInRange) {

                            wifiTaskCompleteListener.isWiFiInRange(true);
                            EliteSession.eLog.d(MODULE, " " + " Scanning is completed,Wifi In the range");

                        } else {
                            wifiTaskCompleteListener.isWiFiInRange(false);
                            EliteSession.eLog.d(MODULE, " " + " Scanning is completed,Wifi Not in range, Cancel the thread for connection");
                        }

                        try {
                            if (wifiReciver != null)
                                context.unregisterReceiver(wifiReciver);
                        } catch (Exception e) {
                            EliteSession.eLog.e(MODULE, " Error unregisterReceiver " + e.getMessage());
                        }
                    }
                };
                context.registerReceiver(wifiReciver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));


            } else {

                throw new Exception("SSID not valid or empty");
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Error " + e.getMessage());
        }
        EliteSession.eLog.d(MODULE, " isWiFiInRange completed");

    }


    /**
     * This method is use to get personal wifi from ssid name
     *
     * @param ssidName wifi ssid name
     * @return PojoWifiInformation contain personal wifi information
     */
    public PojoWifiInformation getPersonalWifi(String ssidName) {
        dbOperation = DBOperation.getDBHelperOperation();
        dbOperation.open();
        PojoWifiInformation pojoWifiInformation = dbOperation.getWifiInformationBySSIDName(ssidName);
        EliteSession.eLog.d(MODULE, " pojoWifiInformation : " + pojoWifiInformation);
        dbOperation.close();
        return pojoWifiInformation;
    }

    private boolean checkLicense() {
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();
        if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(context)) {
            wifiTaskComplete.getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_APPLICATIONIDWRONG, EliteWiFIConstants.FAILURE_MESSAGE_APPLICATIONIDWRONG, wifiTaskComplete.getRequestId()));
            return false;
        }
        return true;
    }

    /**
     * This method is use to add personal wifi
     *
     * @param pojoWifiInformation Contain wifi information to add
     */
    public void addPersonalWifi(PojoWifiInformation pojoWifiInformation) {
        EliteSession.eLog.i(MODULE, " addPersonalWifi method call");
        WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();
        wifiTaskComplete.setRequestId(EliteWiFIConstants.ADD_PERSONAL_WIFI);
        try {
//            if (!checkLicense())
//                return;

//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("addPersonalWifi");
            try {
                EliteSession.eLog.d(MODULE, "Connecting database");
                dbOperation = DBOperation.getDBHelperOperation();
                dbOperation.open();
                PojoWifiInformation wifiInformation = dbOperation.getWifiInformationBySSIDName(pojoWifiInformation.getSsidName());
                if (wifiInformation != null) {
                    wifiTaskComplete.getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_ALREADYEXIST, EliteWiFIConstants.FAILURE_MESSAGE_ALREADYEXIST, wifiTaskComplete.getRequestId()));
                    return;
                }
                dbOperation.close();
                dbOperation.insertWifiInformation(pojoWifiInformation);
            } finally {
                if (dbOperation.isDBOpen()) {
                    dbOperation.close();
                }
            }

            EliteSession.eLog.i(MODULE, " wifiTaskComplete object is : " + wifiTaskComplete.toString());
            wifiTaskComplete.getResponseData(getSuccessResponse(EliteWiFIConstants.SUCCESS_MESSAGED_WIFIADDED, wifiTaskComplete.getRequestId()));


        } catch (Exception e) {
            wifiTaskComplete.getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTOADD, EliteWiFIConstants.FAILURE_MESSAGE_FAILTOADD, wifiTaskComplete.getRequestId()));
            EliteSession.eLog.e(MODULE, " Fail to add PersonalWifi" + e.getMessage());
            return;
        }
        EliteSession.eLog.i(MODULE, " Return from addPersonalWifi method with success fully add personal wifi with ssid name : " + pojoWifiInformation.getSsidName());

    }

    /**
     * This method is use to connect personal wifi
     * *
     * onlyPersonl=true will consider only personal SSID
     * onlyPersonl=false will con
     */
    public void connectToPersonalWifi(boolean onlyPersonl) {
        try {
//            if (!checkLicense())
//                return;
//
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("connectToPersonalWifi");

            EliteSession.eLog.i(MODULE, " connectToPersonalWifi method call with argument onlyPersonal : " + onlyPersonl);
            WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();
            wifiTaskComplete.setRequestId(EliteWiFIConstants.CONNECT_TO_WIFI);
            wifiTaskComplete.setConnectToWifi(true);
            if (considerEAP) {
                EliteSession.eLog.d(MODULE, " Consider EAP in connection");
                wifiTaskComplete.setOpenSSID(false);
            } else {
                EliteSession.eLog.d(MODULE, " Consider only OPEN in connection");
                wifiTaskComplete.setOpenSSID(true);
            }
            if (onlyPersonl) {
                wifiTaskComplete.setOperatorWifiOnly(false);
                wifiTaskComplete.setPersonalWifiOnly(true);
            } else {
                wifiTaskComplete.setOperatorWifiOnly(true);
                wifiTaskComplete.setPersonalWifiOnly(false);
            }
            EliteSession.eLog.i(MODULE, " wifiTaskComplete object is : " + wifiTaskComplete.toString());
            CommonWiFiUtility.scanWiFiInRangeAndProcess(wifiTaskComplete);

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Fail to connectToPersonalWifi" + e.getMessage());
        }
    }

    @Override
    public void connectToPersonalWifi(boolean onlyPersonal, boolean considerEAP) {
        try {
//            if (!checkLicense())
//                return;
//
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("connectToPersonalWifi");

            this.considerEAP = considerEAP;
            connectToPersonalWifi(onlyPersonal);

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Fail to connectToPersonalWifi" + e.getMessage());
        }

    }

    /**
     * This method is use to auto detect list of available wifi in rang.
     */
    public void autoDetectWifi() {
        try {
            EliteSession.eLog.d(MODULE, " autoDetectWifi method call");
//            if (!checkLicense())
//                return;
//
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("autoDetectWifi");

            WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();
            wifiTaskComplete.setRequestId(EliteWiFIConstants.AUTO_DETECT);
            wifiTaskComplete.setConnectToWifi(false);
            wifiTaskComplete.setPersonalWifiOnly(false);
            wifiTaskComplete.setAutoDetectWifi(true);
            EliteSession.eLog.d(MODULE, " wifiTaskComplete object is : " + wifiTaskComplete.toString());
            CommonWiFiUtility.turnOnWiFi(LibraryApplication.getLibraryApplication().getWiFiManager());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            CommonWiFiUtility.scanWiFiInRangeAndProcess(wifiTaskComplete);
            EliteSession.eLog.d(MODULE, " Return autoDetectWifi method");

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Fail to autoDetectWifi" + e.getMessage());
        }
    }

    /**
     * This method is use to delete wifi information by ssid name
     *
     * @param ssidName name of wifi ssid
     */
    public void deleteWifiInformation(String ssidName) {
        try {
            EliteSession.eLog.d(MODULE, " deleteWifiInformation method call");
//            if (!checkLicense())
//                return;
//
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("deleteWifiInformation");

            dbOperation = DBOperation.getDBHelperOperation();
            dbOperation.open();
            dbOperation.deleteWifiInformation(DBQueryFieldConstants.TABLE_NAME_WIFI_INFORMATION, new String[]{DBQueryFieldConstants.SSID_NAME}, new String[]{ssidName});
            dbOperation.close();
            EliteSession.eLog.d(MODULE, " Return deleteWifiInformation method");

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Fail to deleteWifiInformation" + e.getMessage());
        }
    }

    /**
     * This method is use to autoLogin to wifi
     *
     * @param autoLogin Object of PojoWifiAutoLogin
     */
    public void autoLoginToWifi(PojoWifiAutoLogin autoLogin) {
        try {
            EliteSession.eLog.d(MODULE, " autoLoginToWifi method call");
//            if (!checkLicense())
//                return;
//
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("autoLoginToWifi");

            int reqid;
            if (autoLogin.isOffload())
                reqid = EliteWiFIConstants.AUTO_LOGIN_OFFLOAD;
            else
                reqid = EliteWiFIConstants.AUTO_LOGIN_FREE_WIFI;
            if (!verifyAutoLoginParam(autoLogin, reqid))
                return;
            EliteSession.eLog.i(MODULE, " request parameter is " + autoLogin);
            WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();
            wifiTaskComplete.setConnectToWifi(true);
            wifiTaskComplete.setPersonalWifiOnly(false);
            wifiTaskComplete.setOperatorWifiOnly(true);

            if (autoLogin.isOffload()) {
                wifiTaskComplete.setRequestId(EliteWiFIConstants.AUTO_LOGIN_OFFLOAD);
                wifiTaskComplete.setOpenSSID(false);
                wifiTaskComplete.setLogin(false);
            } else {
                wifiTaskComplete.setRequestId(EliteWiFIConstants.AUTO_LOGIN_FREE_WIFI);
                wifiTaskComplete.setAutoLogin(autoLogin);
                wifiTaskComplete.setOpenSSID(true);
                wifiTaskComplete.setLogin(true);
            }

            EliteSession.eLog.i(MODULE, " wifiTaskComplete object is : ");
            String json = new Gson().toJson(wifiTaskComplete);
            EliteSession.eLog.i(MODULE, json.toString());


            CommonWiFiUtility.scanWiFiInRangeAndProcess(wifiTaskComplete);
            EliteSession.eLog.d(MODULE, " Return from autoLoginToWifi method");

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Fail to autoLoginToWifi" + e.getMessage());
        }
    }

    /**
     * This method is use to update wifi priority
     *
     * @param wifiInformation contain information of wifi to update priority
     */
    @Override
    public void updateWifiPriority(PojoWifiInformation wifiInformation) {
        try {
            EliteSession.eLog.d(MODULE, " updateWifiPriority method call");
//            if (!checkLicense())
//                return;
//
//            if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
//                throw new Exception(CoreConstants.NOT_REGISTER);
//            }
            methodValidation("updateWifiPriority");

            WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();
            wifiTaskComplete.setRequestId(EliteWiFIConstants.UPDATEWIFIPRIORITY);
            try {

                dbOperation = DBOperation.getDBHelperOperation();
                dbOperation.open();
                int count = dbOperation.updateWifiPriority(wifiInformation);
                if (count == 0) {
                    wifiTaskComplete.getResponseData(getSuccessResponse("No any Record found from ssid " + wifiInformation.getSsidName() + " to update", EliteWiFIConstants.UPDATEWIFIPRIORITY));
                }
                wifiTaskComplete.getResponseData(getSuccessResponse("Success fully update Wi-Fi " + wifiInformation.getSsidName() + " priority", EliteWiFIConstants.UPDATEWIFIPRIORITY));
                dbOperation.close();
            } catch (Exception e) {
                dbOperation.close();
                wifiTaskComplete.getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_UPDATEFAIL, EliteWiFIConstants.FAILURE_MESSAGE_UPDATEFAIL, EliteWiFIConstants.UPDATEWIFIPRIORITY));
                EliteSession.eLog.e(MODULE, e.getMessage());
            }
            EliteSession.eLog.i(MODULE, " Return updateWifiPriority method");

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Fail to updateWifiPriority" + e.getMessage());
        }

    }

    /**
     * This method verify request parameter of auto login
     *
     * @param autoLogin object of PojoWifiAutoLogin
     * @return true if all parameter for auto login is valid
     */
    private boolean verifyAutoLoginParam(PojoWifiAutoLogin autoLogin, int reqId) {
        EliteSession.eLog.d(MODULE, "Verifying autologin param");
        if (autoLogin == null) {
            getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_PARAMMISSING, EliteWiFIConstants.FAILURE_MESSAGE_PARAMMISSING, reqId));
            return false;
        }
        if (autoLogin.isOffload() == null) {
            getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_OFFLOADMISSING, EliteWiFIConstants.FAILURE_MESSAGE_OFFLOADMISSING, reqId));
            return false;
        }

        if (!autoLogin.isOffload()) {
            if (autoLogin.getPhoneNumber() == null || autoLogin.getPhoneNumber().length() < 10) {
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_PHONEMISSING, EliteWiFIConstants.FAILURE_MESSAGE_PHONEMISSING, reqId));
                return false;
            } else if (autoLogin.getOTP() == null || autoLogin.getOTP().isEmpty()) {
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_OTPMISSING, EliteWiFIConstants.FAILURE_MESSAGE_OTPMISSING, reqId));
                return false;
            } else if (autoLogin.getPackageId() == null ||
                    autoLogin.getPackageId().isEmpty()) {
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_PACKAGEMISSING, EliteWiFIConstants.FAILURE_MESSAGE_PACKAGEMISSING, reqId));
                return false;
            }
        }
        if (autoLogin.getChannel() == null || (autoLogin.getChannel() < 1 || autoLogin.getChannel() > 5)) {
            getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_CHANNELMISSING, EliteWiFIConstants.FAILURE_MESSAGE_CHANNELMISSING, reqId));
            return false;
        }
        return true;
    }





   /* private boolean checkInternetConnection(){
        if(!CommonWiFiUtility.isInternetAvailable(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_PING_URL))) {
            String ssid = disconnectWifi();
            getWifiTaskCompleteListener().getResponseData(getFailResponse("Successfully Connect to  \" " + ssid + " \" Wi-Fi But Fail to get internet connection.."));
            return false;
        }
        return true;
    }*/

    private String disconnectWifi() {
        String ssid = null;
        try {
            WifiManager wifiManager = (WifiManager) LibraryApplication.getLibraryApplication().getLibraryContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            ssid = wifiManager.getConnectionInfo().getSSID();
            wifiManager.disconnect();
        } catch (Exception e) {
            EliteSession.eLog.i(MODULE, " Error While Disconnect Wi-Fi. Reason :  " + e.getMessage());
        }
        return ssid;
    }

  /*  private void connectToPersonalWiFi(final Context context, final PojoConnection connection, final OnWiFiTaskCompleteListner wifiTaskCompleteListener) throws Exception {
        try {
            *//*if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(context))
                throw new Exception("Application ID is not Valid");*//*
            Thread connectinThread = new Thread(new Runnable() {
                @Override
                public void run() {

                    EliteSession.eLog.d(MODULE, " ==============Input Wi-Fi Connection Parameters Sent By user======================");
                    EliteSession.eLog.d(MODULE, " " + connection.toString());
                    IEliteWiFiconfigurator config;
                    int networkId = -1;
                    try {
                        config = WiFISettingsFactory.getWiFiSettings(connection);
                        networkId = config.createWiFiSettings(context, connection);
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE,e.getMessage());
                        getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTOCONNECT,EliteWiFIConstants.FAILURE_MESSAGE_FAILTOCONNECT));
                    }

                    EliteSession.eLog.d(MODULE, " Network ID : " + networkId);
                    if (networkId != -1) {
                      *//*  if (delteOnTurnOffWiFi) {
                            LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveBoolean(SharedPreferenceConstants.DELETE_AFTER_WIFI_OFF, true);

                        }*//*
                        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveString(SharedPreferenceConstants.SSIDNAME, connection.getSsid());
                        LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveBoolean(SharedPreferenceConstants.ISWIFISETTINGSDELETED, false);
                        WIFIConnection.getWIFIConnection().doWiFiConnection(context, networkId, connection.getSsid(), wifiTaskCompleteListener, true);
                    } else {
                        *//*if (!keepWiFiOn) {
                            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                            CommonWiFiUtility.turnOffWiFi(wifiManager);
                        }*//*

                        wifiTaskCompleteListener.onWiFiTaskComplete(WiFiConstants.NOTCONNECTED);
                    }
                }
            });
            connectinThread.start();

        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Error while creating Network ID:" + e.getMessage());
        }

    }*/

    public OnWiFiTaskCompleteListner getWifiTaskCompleteListener() {
        return wifiTaskCompleteListener;
    }

    private PojoConnection prepareConnectionFromWifiInfo(PojoWifiInformation pojoWifiInformation) {
        EliteSession.eLog.i(MODULE, " prepareConnectionFromWifiInfo method call from " + getCallingMethodName() + " method");
        PojoConnection connection = new PojoConnection();
        connection.setSsid(pojoWifiInformation.getSsidName());
        connection.setIdentity(pojoWifiInformation.getIdentity());
        connection.setPassword(pojoWifiInformation.getPassword());
        connection.setPhase2Authentication(pojoWifiInformation.getPhase2Authentication());
        connection.setSecurity(pojoWifiInformation.getSecurityMethod());
        connection.setAuthenticationMethod(pojoWifiInformation.getAuthenMethod());
        EliteSession.eLog.i(MODULE, " Return from prepareConnectionFromWifiInfo method call from " + getCallingMethodName() + " method");
        return connection;
    }

    private String getSuccessResponse(String successMessage, int reqId) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.SUCCESSCODE);
            jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, successMessage);
            jsonObject.put(EliteWiFIConstants.REQUESTID, reqId);

        } catch (JSONException je) {
            EliteSession.eLog.e(MODULE, je.getMessage());
        }
        EliteSession.eLog.i(MODULE, " Final getSuccessResponse : " + jsonObject.toString());
        return jsonObject.toString();
    }

    private String getFailResponse(int errorCode, String errorMessage, int reqId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(EliteWiFIConstants.RESPONSECODE1, errorCode);
            jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, errorMessage);
            jsonObject.put(EliteWiFIConstants.REQUESTID, reqId);
        } catch (JSONException je) {
            EliteSession.eLog.d(MODULE, je.getMessage());
        }

        EliteSession.eLog.i(MODULE, " Final getFailResponse : " + jsonObject.toString());
        return jsonObject.toString();
    }

    /**
     * This method is use to getQOS of Wi-Fi from ssid name
     *
     * @param SSIDName for which QoS is needed
     */
    public void getQOSForWifi(String SSIDName) {
        EliteSession.eLog.i(MODULE, "getQOSForWifi method call");
        WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();

        try {
            Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            String connectedSSIDName = CommonWiFiUtility.getConnectionSSIDName();
            JSONObject jsonObject = new JSONObject();
            long downloadSpeed = 00l;
            long uploadSpeed = 00l;
            if (!SSIDName.equals(connectedSSIDName)) {

                jsonObject.put(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.FAILURE_CODE_NOTCONNECTEDWIFI);
                jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, SSIDName + " " + EliteWiFIConstants.FAILURE_MESSAGE_NOTCONNECTEDWIFI);
            } else {

                long TotalTxBeforeTest = TrafficStats.getTotalTxBytes();
                long TotalRxBeforeTest = TrafficStats.getTotalRxBytes();
                // pingURL();
                final Boolean isTrue = false;
                final List<Thread> threadList = new CopyOnWriteArrayList<Thread>();
                for (int i = 0; i < 20; i++) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            CommonWiFiUtility.isInternetAvailable(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.VALUE_DOWNLOADSPEED_URL));
                            // pingURL();
                        }
                    }, "Thread" + (i + 1));
                    threadList.add(thread);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (Thread t : threadList) {
                            t.start();
                        }
                        while (!threadList.isEmpty()) {
                            for (Thread t : threadList) {
                                if (!t.isAlive()) {
                                    threadList.remove(t);
                                }
                            }
                        }

                        try {
                            synchronized (EliteWiFiAPI.this) {
                                EliteWiFiAPI.this.notifyAll();
                            }
                        } catch (Exception e) {
                            e.printStackTrace(); //If you want further info on failure...
                        }

                    }
                }).start();
                long BeforeTime = System.currentTimeMillis();
                synchronized (this) {
                    EliteSession.eLog.i(MODULE, " main thread is going to wait. thread name is " + Thread.currentThread().getName() + " with date " + new Date() + " on microSecond " + System.currentTimeMillis());
                    this.wait();
                }
                EliteSession.eLog.i(MODULE, " main thread is notify. thread name is " + Thread.currentThread().getName() + " with date " + new Date() + " on microSecond " + System.currentTimeMillis());
                long AfterTime = System.currentTimeMillis();
                long TotalTxAfterTest = TrafficStats.getTotalTxBytes();
                long TotalRxAfterTest = TrafficStats.getTotalRxBytes();


                long TimeDifference = AfterTime - BeforeTime;

                long rxDiff = TotalRxAfterTest - TotalRxBeforeTest;
                long txDiff = TotalTxAfterTest - TotalTxBeforeTest;

                if ((rxDiff != 0) && (txDiff != 0)) {
                    downloadSpeed = rxDiff / (TimeDifference > 0 ? TimeDifference / 1000 : 1);
                    uploadSpeed = txDiff / (TimeDifference > 0 ? TimeDifference / 1000 : 1);
                }

                jsonObject.put(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.SUCCESSCODE);
                jsonObject.put(EliteWiFIConstants.RESPONSEMESSAGE1, "Successfully identify Wi-Fi Speed");


            }
            jsonObject.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.GETQOSFORWIFI);
            jsonObject.put(EliteWiFIConstants.KEY_DOWNLOAD_SPEED, ElitelibUtility.formatSpeedWithPostFix(downloadSpeed));
            jsonObject.put(EliteWiFIConstants.KEY_UPLOAD_SPEED, ElitelibUtility.formatSpeedWithPostFix(uploadSpeed));

            wifiTaskComplete.getResponseData(jsonObject.toString());
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, " Fail get Wi-Fi upload download speed. Reason : " + e.getMessage());
            wifiTaskComplete.getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTOGETSPEED, EliteWiFIConstants.FAILURE_MESSAGE_FAILTOGETSPEED, EliteWiFIConstants.GETQOSFORWIFI));
        }
        EliteSession.eLog.i(MODULE, " return getQOSForWifi method call");
    }

    @Override
    public void pgLogin(String PhoneNo, String Channel, String IpAddress) {
        EliteSession.eLog.d(MODULE, "invoked pg login");
        try {
            WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();
            wifiTaskComplete.setRequestId(EliteWiFIConstants.PGLOGIN);

            if (PhoneNo == null || PhoneNo.equals("")) {
                EliteSession.eLog.d(MODULE, "invoked phone no null");
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_PHONEMISSING, EliteWiFIConstants.FAILURE_MESSAGE_PHONEMISSING, EliteWiFIConstants.PGLOGIN));
                return;
            }
            if (Channel == null || Channel.equals("")) {
                EliteSession.eLog.d(MODULE, "invoked channel no null");
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_CHANNELMISSING, EliteWiFIConstants.FAILURE_MESSAGE_CHANNELMISSING, EliteWiFIConstants.PGLOGIN));
                return;
            }

            EliteSession.eLog.i(MODULE, " ispgLogin true intializing URL");
            IEliteSMPAPI api = new EliteSMPAPI(wifiTaskComplete);
            try {
                api.intializeURL(getMetaDataValue(GRADLE_SMP_SERVER_URL));
                api.doPGLogIn(EliteSMPConstants.ELITESMP_REQUEST_PGLOGIN, PhoneNo, Channel, IpAddress);
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, " login to open ssid fail. Reason : " + e.getMessage());
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }

    }

    @Override
    public void pgLogout(String PhoneNo, String Channel, String IpAddress) {
        EliteSession.eLog.d(MODULE, "invoked pg logout");

        try {
            WifiTaskInternalProcessComplete wifiTaskComplete = new WifiTaskInternalProcessComplete();
            wifiTaskComplete.setRequestId(EliteWiFIConstants.PGLOGOOUT);

            if (PhoneNo == null || PhoneNo.equals("")) {
                EliteSession.eLog.d(MODULE, "invoked phone no null");
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_PHONEMISSING, EliteWiFIConstants.FAILURE_MESSAGE_PHONEMISSING, EliteWiFIConstants.PGLOGOOUT));
                return;
            }
            if (Channel == null || Channel.equals("")) {
                EliteSession.eLog.d(MODULE, "invoked channel no null");
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_CHANNELMISSING, EliteWiFIConstants.FAILURE_MESSAGE_CHANNELMISSING, EliteWiFIConstants.PGLOGOOUT));
                return;
            }

            EliteSession.eLog.i(MODULE, " ispgLogout true intializing URL");
            IEliteSMPAPI api = new EliteSMPAPI(wifiTaskComplete);
            try {
                api.intializeURL(getMetaDataValue(GRADLE_SMP_SERVER_URL));
                api.doPGLogOut(EliteSMPConstants.ELITESMP_REQUEST_PGLOGOUT, PhoneNo, Channel, IpAddress);
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, " logout to open ssid fail. Reason : " + e.getMessage());
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, e.getMessage());
        }
    }


    private void methodValidation(String methodName) throws Exception {
        EliteSession.eLog.d(MODULE, methodName + " invoked in EliteWiFiAPI");
        Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
        if (LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_LOCAL) == 0 && !LibraryApplication.isLicenseValidated(context))
            throw new Exception("Application ID is not Valid");

        if (CoreConstants.ENABLE_MONETIZATION_REGISTRATION && LibraryApplication.getLibraryApplication().getLicenseMechanism() != null && LibraryApplication.getLibraryApplication().getLicenseMechanism().compareTo(CoreConstants.LICENSE_SERVER) == 0 && spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER) != true) {
            throw new Exception(CoreConstants.NOT_REGISTER);
        }
    }


//    public int getREQUESTID() {
//        return REQUESTID;
//    }
//
//    public void setREQUESTID(int REQUESTID) {
//        EliteSession.eLog.i(MODULE, " Request ID set is " + REQUESTID);
//        this.REQUESTID = REQUESTID;
//    }

    /**
     * This is internal call and work as helper class for EliteWifiAPI.
     * This call take care of all internal listener process and give appropriate respond to client
     */
    private class WifiTaskInternalProcessComplete implements OnWiFiTaskCompleteListner, OnEliteSMPTaskCompleteListner {

        private PojoWifiAutoLogin autoLogin;
        private boolean login;
        private boolean autoDetectWifi;
        private boolean connectToWifi;
        private boolean personalWifiOnly;
        private boolean operatorWifiOnly;
        private boolean openSSID;
        private List<PojoWifiInformation> pojoWifiInformations;
        private String connectedSSIDName;
        private int requestId;

        public int getRequestId() {
            return requestId;
        }

        public void setRequestId(int requestId) {
            EliteSession.eLog.d(MODULE, "Request ID is " + requestId);
            this.requestId = requestId;
        }

        @Override
        public String toString() {
            return "WifiTaskInternalProcessComplete{" +
                    "autoLogin=" + autoLogin +
                    ", login=" + login +
                    ", autoDetectWifi=" + autoDetectWifi +
                    ", connectToWifi=" + connectToWifi +
                    ", personalWifiOnly=" + personalWifiOnly +
                    ", operatorWifiOnly=" + operatorWifiOnly +
                    ", openSSID=" + openSSID +
                    ", pojoWifiInformations=" + pojoWifiInformations +
                    ", connectedSSIDName='" + connectedSSIDName + '\'' +
                    ", requestId=" + requestId +
                    '}';
        }


        public List<PojoWifiInformation> getPojoWifiInformations() {
            return pojoWifiInformations;
        }


        @Override
        public void onWiFiScanComplete(List<String> ssidList) {
            EliteSession.eLog.i(MODULE, " onWiFiScanComplete method call from " + getCallingMethodName() + " method");

            if (ssidList == null || ssidList.isEmpty()) {

                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_NOWIFI1, EliteWiFIConstants.FAILURE_MESSAGE_NOWIFI1, requestId));
                EliteSession.eLog.i(MODULE, " Return from onWiFiScanComplete method. No any wifi on range");
                return;
            }
            EliteSession.eLog.i(MODULE, " Found wifi ssid list size is " + ssidList.size());
            List<PojoWifiInformation> pojoWifiInformations;
            dbOperation = DBOperation.getDBHelperOperation();
            dbOperation.open();
            EliteSession.eLog.d(MODULE, " operatorWifiOnly is " + operatorWifiOnly);
            if (operatorWifiOnly) {
                if (!openSSID) {
                    try {
                        String imsi = ElitelibUtility.getIMSI(true, false, ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.VALUE_OPERATOR_NAME));
                        EliteSession.eLog.d(MODULE, "IMSI check completed");
                        if (imsi == null || imsi.isEmpty()) {
                            getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_NOSUBSCRIBER, "You are not a " + ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.VALUE_OPERATOR_NAME) + " subscriber", requestId));
                            return;
                        }
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, " Fail to connect wifi. Reason : " + e.getMessage());
                        getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTOCONNECT, EliteWiFIConstants.FAILURE_MESSAGE_FAILTOCONNECT, requestId));
                    }
                }
                pojoWifiInformations = dbOperation.getWifiListOperatorSpecific(ssidList, openSSID);
            } else {
                pojoWifiInformations = dbOperation.getWifisFromSSIDList(ssidList, personalWifiOnly);
            }
            EliteSession.eLog.d(MODULE, " pojoinformation prepared");
            dbOperation.close();
            try {
                if (pojoWifiInformations == null || pojoWifiInformations.isEmpty()) {
                    EliteSession.eLog.d(MODULE, " pojoinformation empty");
                    if (operatorWifiOnly) {
                        getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_NOOPERATORWIFI, EliteWiFIConstants.FAILURE_MESSAGE_NOOPERATORWIFI, requestId));
                    } else {
                        getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_NOPERSONALWIFI, EliteWiFIConstants.FAILURE_MESSAGE_NOPERSONALWIFI, requestId));
                    }
                    return;
                }
                processOnAvailableWifi(pojoWifiInformations);
            } catch (Exception e) {
                disconnectWifi();
                EliteSession.eLog.e(MODULE, e.getMessage());
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTOCONNECT, EliteWiFIConstants.FAILURE_MESSAGE_FAILTOCONNECT, requestId));
            }
        }

        @Override
        public void onWiFiTaskComplete(String result) {
            EliteSession.eLog.i(MODULE, " back to onWiFiTaskComplete");
            if (result != null)
                EliteSession.eLog.i(MODULE, result);

            if (result != null && (result.equals(com.elitecore.wifi.api.WiFiConstants.CONNECTED) || result.equals(com.elitecore.wifi.api.WiFiConstants.ALREADYCONNECTED))) {
                if (isLogin()) {
                    EliteSession.eLog.i(MODULE, " isLogin true intializing URL");
                    IEliteSMPAPI api = new EliteSMPAPI(this);
                    try {

                        api.intializeURL(getMetaDataValue(GRADLE_SMP_SERVER_URL));
                        api.doAutoLogin(EliteSMPConstants.ELITESMP_REQUEST_DOLOGIN, autoLogin.getPhoneNumber().toString(),
                                autoLogin.getOTP(),
                                autoLogin.getPackageId(),
                                autoLogin.getChannel().toString(), "0", autoLogin.getIpAddress());
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, " login to open ssid fail. Reason : " + e.getMessage());
                    }
                } else {
                    /*if(!checkInternetConnection()) {
                        return;
                    }*/
                    if (result.equals(com.elitecore.wifi.api.WiFiConstants.ALREADYCONNECTED))
                        getWifiTaskCompleteListener().getResponseData(getSuccessResponse("Already Connect to Wi-Fi", requestId));
                    else
                        getWifiTaskCompleteListener().getResponseData(getSuccessResponse("Successfully Connect to Wi-Fi", requestId));
                }
            } else {
                if (NETWORKNEGATIVE) {
                    EliteSession.eLog.d(MODULE, "Network Negative");
                    getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTOCONNECT, EliteWiFIConstants.FAILURE_MESSAGE_FAILTOCONNECT, requestId));
                } else {
                    EliteSession.eLog.d(MODULE, "Network Timeout");
                    getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_REQUESTITMEOUT, EliteWiFIConstants.FAILURE_MESSAGE_REQUESTITMEOUT, requestId));
                }

            }
        }

        /**
         * This method is use to process of login, connect to ssid or only give list of available Wi-Fi.
         *
         * @param pojoWifiInformations list of pojowifiinformation
         * @throws Exception if something went wrong
         */
        private void processOnAvailableWifi(List<PojoWifiInformation> pojoWifiInformations) throws Exception {
            EliteSession.eLog.i(MODULE, " processOnAvailableWifi method call from " + getCallingMethodName() + " method");


            if (pojoWifiInformations != null || !pojoWifiInformations.isEmpty()) {
                EliteSession.eLog.i(MODULE, " pojoWifiInformations size is : " + pojoWifiInformations.size());
                EliteSession.eLog.d(MODULE, "personal Wi-Fi list are : ");
                String json = new Gson().toJson(pojoWifiInformations);
                try {
                    JSONArray mJSONArray = new JSONArray(json);
                    EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
                } catch (JSONException e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
                Collections.sort(pojoWifiInformations);
                this.pojoWifiInformations = pojoWifiInformations;
                EliteSession.eLog.d(MODULE, "personal Wi-Fi list after priority wise sort: ");
                String json1 = new Gson().toJson(pojoWifiInformations);
                try {
                    JSONArray mJSONArray = new JSONArray(json1);
                    EliteSession.eLog.i(MODULE, " List found, " + mJSONArray.toString());
                } catch (JSONException e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }

                if (connectToWifi) {
                    EliteSession.eLog.i(MODULE, " Conntect to wifi is true");
                    PojoConnection connection = prepareConnectionFromWifiInfo(pojoWifiInformations.get(0));
                    Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
                    try {
                        EliteSession.eLog.i(MODULE, " trying to connect");
                        boolean considerAutoRemoveal;
                        if (pojoWifiInformations.get(0).getDelteOnTurnOffWiFi()) {
                            EliteSession.eLog.d(MODULE, "Auto removeal is set to true");
                            considerAutoRemoveal = true;
                        } else {
                            EliteSession.eLog.d(MODULE, "Auto removeal is set to false");
                            considerAutoRemoveal = false;
                        }
                        if (pojoWifiInformations.get(0) != null && pojoWifiInformations.get(0).getAutoRemovealTimerInterval() != null && pojoWifiInformations.get(0).getAutoRemovealTimerInterval() > 0) {
                            EliteSession.eLog.d(MODULE, "Removel timer set to " + pojoWifiInformations.get(0).getAutoRemovealTimerInterval());
                            LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().saveInt(SharedPreferenceConstants.DELETE_TIME_INTERVAL, pojoWifiInformations.get(0).getAutoRemovealTimerInterval());
                        }
                        connectToWiFi(context, connection, this, true, considerAutoRemoveal);
                        //connectToPersonalWiFi(context, connection, this);
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, "Error while connect personal Wi-Fi. Reason : " + e.getMessage());
                        disconnectWifi();
                        getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTOCONNECT, EliteWiFIConstants.FAILURE_MESSAGE_FAILTOCONNECT, requestId));
                    }
                } else {
                    EliteSession.eLog.i(MODULE, " Conntect to wifi is false,prepare response");
                    prepareResponseForAutoDetectedWifi();
                }

            } else {
                if (!connectToWifi) {
                    this.pojoWifiInformations = pojoWifiInformations;
                    prepareResponseForAutoDetectedWifi();
                }
            }
            EliteSession.eLog.i(MODULE, " Return processOnAvailableWifi method call from " + getCallingMethodName() + " method");
        }

        private void prepareResponseForAutoDetectedWifi() throws Exception {
            EliteSession.eLog.i(MODULE, " prepareResponseForAutoDetectedWifi method call from " + getCallingMethodName() + " method");
            try {
                if (pojoWifiInformations == null || this.pojoWifiInformations.isEmpty()) {
                    EliteSession.eLog.i(MODULE, " prepareResponseForAutoDetectedWifi pojoWifiInformations empty");
                    getResponseMap(null, EliteSMPConstants.ELITESMP_REQUEST_GETPARTNERDATA);
                } else {
                    EliteSession.eLog.i(MODULE, " prepareResponseForAutoDetectedWifi pojoWifiInformations Not empty");
                    Context context = LibraryApplication.getLibraryApplication().getLibraryContext();
                    WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    this.connectedSSIDName = CommonWiFiUtility.getConnectionSSIDName();
                    EliteSession.eLog.d(MODULE, "You are currently connected with " + connectedSSIDName);
                    PojoWifiInformation pojoWifiInformation = new PojoWifiInformation();
                    pojoWifiInformation.setSsidName(connectedSSIDName);

                    if (this.pojoWifiInformations.contains(pojoWifiInformation)) {
                        if (this.pojoWifiInformations.get(pojoWifiInformations.indexOf(pojoWifiInformation)).getSecurityMethod().compareTo("OPEN") == 0) {
                            EliteSession.eLog.i(MODULE, "  ");
                            String response;
                            response = getAutoDetectResponse("");
                            getWifiTaskCompleteListener().getResponseData(response);
                            //Hotspot name removed as of now as per the requirement, can be invoked direclty.
                           /* IEliteSMPAPI api = new EliteSMPAPI(this);
                            try {
                                EliteSession.eLog.i(MODULE, " prepareResponseForAutoDetectedWifi invoke get partner data");
                                api.intializeURL(getMetaDataValue(GRADLE_SMP_SERVER_URL));
                                api.getPartnerData(EliteSMPConstants.ELITESMP_REQUEST_GETPARTNERDATA);
                            } catch (Exception e) {
                                EliteSession.eLog.e(MODULE, " login to open ssid fail. Reason : " + e.getMessage());
                            }*/
                        } else {
                            String response;
                            response = getAutoDetectResponse("");
                            getWifiTaskCompleteListener().getResponseData(response);
                        }
                    } else {
                        EliteSession.eLog.i(MODULE, " prepareResponseForAutoDetectedWifi not connected with  configured Wi-Fi");
                        getResponseMap(null, EliteSMPConstants.ELITESMP_REQUEST_GETPARTNERDATA);
                    }
                }
            } catch (Exception e) {
                throw new Exception("fail to prepare response for auto detected Wi-Fi. Reason : " + e.getMessage());
            }
        }

        /**
         * Prepare success response
         *
         * @param successMessage success message to show client
         * @return json String
         */
        private String getSuccessResponse(String successMessage, int reqId) {
            return getSuccessResponse(successMessage, null, reqId);
        }

        private String getSuccessResponse(String successMessage, Map<String, String> responseMap, int reqId) {
            try {
                JSONObject job = new JSONObject();
                job.put(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.SUCCESSCODE);
                job.put(EliteWiFIConstants.RESPONSEMESSAGE1, successMessage);
                job.put(EliteWiFIConstants.REQUESTID, reqId);
                if (responseMap != null) {
                    EliteSession.eLog.d(MODULE, " Setting hotspotname, remaining time and volume : ");
                    job.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HOTSPOT_NAME), responseMap.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_LOCATION)));
                    job.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_REMAINING_QUOTATIME), responseMap.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_REMAINING_QUOTATIME)));
                    if (responseMap.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_REMAINING_QUOTAVOLUME)) != null) {
                        job.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_REMAINING_QUOTAVOLUME), responseMap.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_REMAINING_QUOTAVOLUME)));

                    }
                }
                EliteSession.eLog.i(MODULE, " Final getSuccessResponse : " + job.toString());
                return job.toString();

            } catch (JSONException je) {
                EliteSession.eLog.e(MODULE, je.getMessage());
            } catch (Exception e) {
                EliteSession.eLog.d(MODULE, e.getMessage());
            }
            return "";

//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.SUCCESSCODE);
//            jsonObject.addProperty(EliteWiFIConstants.RESPONSEMESSAGE1, successMessage);
//            jsonObject.addProperty(EliteWiFIConstants.REQUESTID, getREQUESTID());
//            if (responseMap != null) {
//                jsonObject.addProperty(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HOTSPOT_NAME), responseMap.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_LOCATION)));
//                jsonObject.addProperty(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_REMAINING_QUOTATIME), responseMap.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_REMAINING_QUOTATIME)));
//            }
//            EliteSession.eLog.i(MODULE, " Final getSuccessResponse : " + jsonObject.toString());
//            return jsonObject.toString();
        }

        /**
         * Prepare fail response
         *
         * @param errorMessage error message to show client
         * @return Json string
         */
        private String getFailResponse(int errorCode, String errorMessage, int reqId) {
            try {
                JSONObject job = new JSONObject();
                job.put(EliteWiFIConstants.RESPONSECODE1, errorCode);
                job.put(EliteWiFIConstants.RESPONSEMESSAGE1, errorMessage);
                job.put(EliteWiFIConstants.REQUESTID, reqId);
                EliteSession.eLog.i(MODULE, " Final Response : " + job.toString());
                return job.toString();
            } catch (JSONException je) {
                EliteSession.eLog.e(MODULE, je.getMessage());
            } catch (Exception e) {
                EliteSession.eLog.d(MODULE, e.getMessage());
            }
            return "";
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty(EliteWiFIConstants.RESPONSECODE1, errorCode);
//            jsonObject.addProperty(EliteWiFIConstants.RESPONSEMESSAGE1, errorMessage);
//            jsonObject.addProperty(EliteWiFIConstants.REQUESTID, getREQUESTID());
//            EliteSession.eLog.i(MODULE, " Final Response : " + jsonObject.toString());
//            return jsonObject.toString();
        }

        public void setAutoLogin(PojoWifiAutoLogin autoLogin) {
            this.autoLogin = autoLogin;
        }

        public boolean isLogin() {
            return login;
        }

        public void setLogin(boolean login) {
            this.login = login;
        }

        public void setAutoDetectWifi(boolean autoDetectWifi) {
            this.autoDetectWifi = autoDetectWifi;
        }

        public void setConnectToWifi(boolean connectToWifi) {
            this.connectToWifi = connectToWifi;
        }

        public void setPersonalWifiOnly(boolean personalWifiOnly) {
            this.personalWifiOnly = personalWifiOnly;
        }

        public void setOperatorWifiOnly(boolean operatorWifiOnly) {
            this.operatorWifiOnly = operatorWifiOnly;
        }

        public void setOpenSSID(boolean openSSID) {
            this.openSSID = openSSID;
        }

        @Override
        public void isWiFiInRange(boolean status) {

        }

        @Override
        public void getResponseData(String responseData) {
            // if(isAutoDetectWifi()) {
            getWifiTaskCompleteListener().getResponseData(responseData);
            // }
        }

        @Override
        public void getPackageList(List<Plan> planList, int requestId) {

        }

        @Override
        public void getResponseMap(Map<String, String> responseMap, int requestId) {
            EliteSession.eLog.d(MODULE, "Callback completed and received call in getResponseMap");
            if (requestId == EliteSMPConstants.ELITESMP_REQUEST_DOLOGIN && responseMap != null) {
                if (responseMap.containsKey(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE)) {
                    if (responseMap.get(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE).equals(EliteSMPConstants.ELITESMP_REPLYMESSAGE_SUCCESS)) {
                        if (isWifiReset) {
                            CommonWiFiUtility.turnOffWiFi(LibraryApplication.getLibraryApplication().getWiFiManager());
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            CommonWiFiUtility.turnOnWiFi(LibraryApplication.getLibraryApplication().getWiFiManager());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        getWifiTaskCompleteListener().getResponseData(getSuccessResponse("Successfully login to internet", responseMap, EliteWiFIConstants.AUTO_LOGIN_FREE_WIFI));
                        return;

                    } else {
                        getWifiTaskCompleteListener().getResponseData(getFailResponse(Integer.parseInt(responseMap.get(EliteSMPConstants.ELITESMPKEY_RESULTCODE)), responseMap.get(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE), EliteWiFIConstants.AUTO_LOGIN_FREE_WIFI));
                        return;
                    }
                }
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTOLOGIN, EliteWiFIConstants.FAILURE_MESSAGE_FAILTOLOGIN, EliteWiFIConstants.AUTO_LOGIN_FREE_WIFI));
                return;
            } else if (requestId == EliteSMPConstants.ELITESMP_REQUEST_GETPARTNERDATA) {
                try {
                    EliteSession.eLog.i(MODULE, " getpartner result found");
                    String response;
                    if (responseMap != null
                            && responseMap.containsKey(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE)
                            && responseMap.get(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE).equals(EliteSMPConstants.ELITESMP_REPLYMESSAGE_SUCCESS)
                            && responseMap.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_REDIRECTIONURL)) != null) {
                        EliteSession.eLog.i(MODULE, " getpartner response proper");
                        URL url = new URL(responseMap.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteSMPUtilConstants.KEY_REDIRECTIONURL)));
                        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
                        String query = url.getQuery();
                        EliteSession.eLog.i(MODULE, " getpartner getting query data");

                        EliteSession.eLog.d(MODULE, "After reading query params, preparing response");
                        String hotspotName = "";
                        try {
                            String[] pairs = query.split("&");
                            for (String pair : pairs) {
                                int idx = pair.indexOf("=");
                                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
                            }
                            if (query_pairs.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_LOCATION)) != null) {
                                hotspotName = query_pairs.get(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_LOCATION));
                            }
                            EliteSession.eLog.i(MODULE, "Hotspot name found from URL is " + hotspotName);
                        } catch (Exception e) {
                            EliteSession.eLog.e(MODULE, e.getMessage());
                        }

                        response = getAutoDetectResponse(hotspotName);
                    } else {
                        EliteSession.eLog.d(MODULE, "Response map is null");
                        response = getAutoDetectResponse("");
                    }
                    getWifiTaskCompleteListener().getResponseData(response);
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, e.getMessage());
                    getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_FAILTODETECT, EliteWiFIConstants.FAILURE_MESSAGE_FAILTODETECT, EliteWiFIConstants.AUTO_DETECT));
                }
            } else if (requestId == EliteSMPConstants.ELITESMP_REQUEST_PGLOGIN) {
                EliteSession.eLog.d(MODULE, "successfully invoked pglogin");
                if (responseMap.containsKey(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE) && responseMap.containsKey(EliteSMPConstants.ELITESMPKEY_RESULTCODE)) {
                    if (responseMap.get(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE).equals(EliteSMPConstants.ELITESMP_REPLYMESSAGE_SUCCESS) && responseMap.get(EliteSMPConstants.ELITESMPKEY_RESULTCODE).equals(EliteSMPConstants.LOGIN_SUCCESS_CODE_ACTUAL)) {
                                          /*  if(!checkInternetConnection()) {
                                                return;
											}*/
                        getWifiTaskCompleteListener().getResponseData(getSuccessResponse("Successfully login to internet", null, EliteWiFIConstants.PGLOGIN));
                        return;

                    } else {
                        getWifiTaskCompleteListener().getResponseData(getFailResponse(Integer.parseInt(responseMap.get(EliteSMPConstants.ELITESMPKEY_RESULTCODE)), responseMap.get(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE), EliteWiFIConstants.PGLOGIN));
                        return;
                    }
                }
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_PGLOGIN, EliteWiFIConstants.FAILURE_PGLOGIN_REQUESTITMEOUT, EliteWiFIConstants.PGLOGIN));
            } else if (requestId == EliteSMPConstants.ELITESMP_REQUEST_PGLOGOUT) {
                EliteSession.eLog.d(MODULE, "successfully invoked pglogout");
                if (responseMap.containsKey(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE) && responseMap.containsKey(EliteSMPConstants.ELITESMPKEY_RESULTCODE)) {
                    if (responseMap.get(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE).equals(EliteSMPConstants.ELITESMP_REPLYMESSAGE_SUCCESS) && responseMap.get(EliteSMPConstants.ELITESMPKEY_RESULTCODE).equals(EliteSMPConstants.LOGIN_SUCCESS_CODE_ACTUAL)) {
                                          /*  if(!checkInternetConnection()) {
                                                return;
											}*/
                        getWifiTaskCompleteListener().getResponseData(getSuccessResponse("Successfully logout to internet", null, EliteWiFIConstants.PGLOGOOUT));
                        return;

                    } else {
                        getWifiTaskCompleteListener().getResponseData(getFailResponse(Integer.parseInt(responseMap.get(EliteSMPConstants.ELITESMPKEY_RESULTCODE)), responseMap.get(EliteSMPConstants.ELITESMPKEY_REPLYMESSAGE), EliteWiFIConstants.PGLOGOOUT));
                        return;
                    }
                }
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_PGLOGOOUT, EliteWiFIConstants.FAILURE_PGLOGOOUT_REQUESTITMEOUT, EliteWiFIConstants.PGLOGOOUT));
            }

            if (requestId == EliteSMPConstants.ELITESMP_REQUEST_DOLOGIN && responseMap == null) {
                getWifiTaskCompleteListener().getResponseData(getFailResponse(EliteWiFIConstants.FAILURE_CODE_REQUESTITMEOUT, EliteWiFIConstants.FAILURE_MESSAGE_REQUESTITMEOUT, EliteWiFIConstants.AUTO_LOGIN_FREE_WIFI));

            }

        }

        @Override
        public void getGenericResponse(String responseMap, int requestId) {

        }

        private String getAutoDetectResponse(String hotspotName) {
            //JsonObject jsonObject = new JsonObject();
            JSONObject job = new JSONObject();
            try {
                EliteSession.eLog.i(MODULE, "getAutoDetectResponse invoked with hotspot name " + hotspotName);
                // jsonObject.addProperty(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.SUCCESSCODE);
                job.put(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.SUCCESSCODE);
                // JsonArray jsonArray = new JsonArray();
                JSONArray jarray = new JSONArray();
                if (pojoWifiInformations == null || this.pojoWifiInformations.isEmpty()) {
                    EliteSession.eLog.i(MODULE, " inside getAutoDetectResponse with emtpy information");

                    job.put(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.FAILURE_CODE_NOWIFI1);
                    job.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_NOWIFI1);
                    job.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.AUTO_DETECT);

//                    jsonObject.addProperty(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.FAILURE_CODE_NOWIFI1);
//                    jsonObject.addProperty(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_NOWIFI1);
//                    jsonObject.addProperty(EliteWiFIConstants.REQUESTID, getREQUESTID());
                    EliteSession.eLog.i(MODULE, " Final Response : " + job.toString());
                    return job.toString();
                }


                for (PojoWifiInformation wifiInformation : this.pojoWifiInformations) {

                    JSONObject ssidDetail = new JSONObject();
                    //  JsonObject ssidDetail = new JsonObject();
                    ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_SSID_NAME), wifiInformation.getSsidName());
                    ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_IS_NEARBY), 1);
                    EliteSession.eLog.i(MODULE, " inside getAutoDetectResponse Method, information found");
                    if (wifiInformation.getSsidName().equals(this.connectedSSIDName)) {

                        ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_IS_CONNECTED), 1);
                        EliteSession.eLog.i(MODULE, " inside getAutoDetectResponse Method checking with connected ssid");
//                        CommonWiFiUtility.isInternetAvailableCheck(CoreConstants.INTERNET_CHECK_URL,EliteWiFiAPI.this,EliteWiFIConstants.AUTO_DETECT);

                        if (CommonWiFiUtility.isMobileDataConnected()) {
                            ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HAS_INTERNET), 2);
                            isWifiReset = true;
                        } else if (CommonWiFiUtility.isInternetAvailable(CoreConstants.INTERNET_CHECK_URL)) {
                            ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HAS_INTERNET), 1);
                            isWifiReset = false;
                        } else {
                            ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HAS_INTERNET), 0);
                            isWifiReset = false;
                        }


/*                        if (CommonWiFiUtility.isInternetAvailable(CoreConstants.INTERNET_CHECK_URL))
                            ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HAS_INTERNET), 1);
                        else
                            ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HAS_INTERNET), 0);*/


//                        ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HOTSPOT_NAME), hotspotName.isEmpty() ?
//                                "" : hotspotName);
                    } else {
                        EliteSession.eLog.i(MODULE, " inside getAutoDetectResponse Method Not connected ssid information");
                        ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_IS_CONNECTED), 0);
                        ssidDetail.put(ElitePropertiesUtil.getInstance().getConfigProperty(EliteWiFIConstants.KEY_HAS_INTERNET), 0);

                    }
                    jarray.put(ssidDetail);
                }

                job.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.SUCCESS_MESSAGE_AUTODETECT);
                job.put(EliteWiFIConstants.RESPONSEDATA1, jarray.toString());
                job.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.AUTO_DETECT);
                EliteSession.eLog.i(MODULE, " getAutoDetectResponse , final value " + job.toString());

            } catch (JSONException je) {
                EliteSession.eLog.e(MODULE, je.getMessage());
            } catch (Exception e) {
                try {
                    job.put(EliteWiFIConstants.RESPONSECODE1, EliteWiFIConstants.FAILURE_CODE_FAILTOGET);
                    job.put(EliteWiFIConstants.RESPONSEMESSAGE1, EliteWiFIConstants.FAILURE_MESSAGE_FAILTOGET);
                    job.put(EliteWiFIConstants.REQUESTID, EliteWiFIConstants.AUTO_DETECT);
                    EliteSession.eLog.d(MODULE, " fail to prepare response for auto detected Wi-Fi. Reason : " + e.getMessage());
                } catch (JSONException je) {
                    EliteSession.eLog.e(MODULE, je.getMessage());
                }


            } finally {
//                pojoWifiInformations = null;
//                connectedSSIDName = null;
            }
            return job.toString();
        }
    }

    public class PojoConfigModelResponse implements Serializable {

        private String responseMessage;
        private int responseCode;
        private String userIdentity;

        public String getResponseMessage() {
            return responseMessage;
        }

        public void setResponseMessage(String responseMessage) {
            this.responseMessage = responseMessage;
        }

        public int getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }

        public String getUserIdentity() {
            return userIdentity;
        }

        public void setUserIdentity(String userIdentity) {
            this.userIdentity = userIdentity;
        }
    }
}
