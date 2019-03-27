package com.elitecorelib.andsf.api;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.elitecore.wifi.api.CommonWiFiUtility;
import com.elitecore.wifi.api.EliteWiFIConstants;
import com.elitecore.wifi.api.EliteWiFiAPI;
import com.elitecore.wifi.api.IWiFiConfiguration;
import com.elitecore.wifi.listener.OnInternetCheckCompleteListner;
import com.elitecore.wifi.listener.OnWiFiTaskCompleteListner;
import com.elitecorelib.andsf.pojo.ANDSFConfig;
import com.elitecorelib.andsf.utility.ANDSFUtility;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.pojo.PojoServerKeyMapping;
import com.elitecorelib.core.pojo.PojoSubscriber;
import com.elitecorelib.core.services.BackgroundProcessService;
import com.elitecorelib.core.services.ConnectionManagerClass;
import com.elitecorelib.core.services.ConnectionManagerListner;
import com.elitecorelib.core.services.InterNetAvailabilityCheckTask;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.elitecorelib.andsf.utility.ANDSFConstant.IS_ANDSF_NOTIFICATION;
import static com.elitecorelib.core.CoreConstants.APPLICATION_NOTIFICATION;
import static com.elitecorelib.core.CoreConstants.ENABLE;
import static com.elitecorelib.core.CoreConstants.KEY_MCC;
import static com.elitecorelib.core.CoreConstants.KEY_MNC;
import static com.elitecorelib.core.CoreConstants.MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID;
import static com.elitecorelib.core.CoreConstants.MONETIZATION_UPDATEDEVICETOKEN_REQUESTID;
import static com.elitecorelib.core.CoreConstants.Seprator;
import static com.elitecorelib.core.utility.SharedPreferencesConstant.KEY_CURRENTTRIGGERCOUNT;

public class ANDSFClient {

    private static ANDSFClient client;
    private final String MODULE = "ANDSFClient";
    private SharedPreferencesTask spTask;
    private Context context;

    //  SingleTone Class
    public static ANDSFClient getClient() {

        if (client == null) {
            client = new ANDSFClient();
        }
        return client;
    }

    /**
     * Set FlexibleMargin for Signal
     *
     * @param dbm
     */
    private void setSignalFlexibleMargin(int dbm) {
        EliteSession.eLog.i(MODULE, "Set flexible margin - " + dbm);
        if (dbm >= 0) {
            spTask.saveInt(SharedPreferencesConstant.REQUIREDSIGNALLEVEL_USER, dbm);
        } else {
            EliteSession.eLog.e(MODULE, "set proper value of dbm");
        }
    }

    public void invokeANDSFClient(final ANDSFConfig config) throws Exception {

        if (config.getContext() == null)
            throw new Exception("Context is null, Please pass the context in ANDSFConfig Class");

        context = config.getContext();

        EliteSession.setELiteConnectSession(context);
        spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

        if (config.isPolicyCall()) {

            if (config.getMonetizationURL() == null || config.getMonetizationURL().equals(""))
                throw new Exception("MonetizaionURL is null, Please pass the MonetizationURL in ANDSFConfig Class");

            if (config.getANDSFURL() == null || config.getANDSFURL().equals(""))
                throw new Exception("ANDSFURL is null, Please pass the ANDSFURL in ANDSFConfig Class");

            if (config.getApplicationName() == null || config.getApplicationName().equals(""))
                throw new Exception("ApplicationName is null, Please pass the ApplicationName in ANDSFConfig Class");

            if (config.getLogo() == 0)
                throw new Exception("Logo is null, Please pass the Logo in ANDSFConfig Class");

            if (config.getNotificationLogo() == 0)
                throw new Exception("NotificationLogo is null, Please pass the NotificationLogo in ANDSFConfig Class");

            if (config.getSharedKey() == null || config.getSharedKey().equals(""))
                throw new Exception("SharedKey is null, Please pass the SharedKey in ANDSFConfig Class");

            if (config.getNotificationKEY() != null && !config.getNotificationKEY().equals(""))
                spTask.saveString(CoreConstants.DEVICEID, config.getNotificationKEY());

            spTask.saveInt(CoreConstants.KEY_LOGO, config.getLogo());
            spTask.saveInt(CoreConstants.KEY_NOTIFICATION_LOGO, config.getNotificationLogo());
            spTask.saveString(CoreConstants.MENU_TITLE_PREF, config.getApplicationName());
            spTask.saveString(CoreConstants.MONETIZATION_URL, config.getMonetizationURL());
            spTask.saveString(CoreConstants.ANDSF_URL, config.getANDSFURL());
            spTask.saveString(APPLICATION_NOTIFICATION, ENABLE);
            spTask.saveInt(SharedPreferencesConstant.WIFISCANINTERVAL, 10);
            spTask.saveBoolean(IS_ANDSF_NOTIFICATION, true);
            spTask.saveString(CoreConstants.LIBRARY_PACKAGE_NAME, "com.elitecore.eliteconnect");
//            spTask.saveString(CoreConstants.LIBRARY_PACKAGE_NAME, "com.jio.jioplay.tv");

            EliteSession.eLog.i(MODULE, "Registration status - " + spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER));

            if (spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER)) {

                if (CommonWiFiUtility.checkValidOperator(ElitelibUtility.getKeyPairvalue(KEY_MCC, KEY_MCC), ElitelibUtility.getKeyPairvalue(KEY_MNC, KEY_MNC), Seprator)) {

                    ComponentName comp = new ComponentName(context.getPackageName(), BackgroundProcessService.class.getName());
                    ComponentName service = context.startService(new Intent().setComponent(comp));
                    if (null == service) {
                        // something really wrong here
                        EliteSession.eLog.d(MODULE, "Could not start service " + comp.toString());
                    }

                    ANDSFUtility.callPolicyPull(context);
                    ANDSFUtility.callPolicyEvaluation(context);
                } else {
                    EliteSession.eLog.i(MODULE, "MCC/MNC not Valid, Active Sim Not Valid operator");
                }
            } else {

                EliteSession.eLog.i(MODULE, "Invoke Internet Check");
                new InterNetAvailabilityCheckTask(new OnInternetCheckCompleteListner() {
                    @Override
                    public void isInterNetAvailable(int requestId, String status, String json) {

                        if (status.equals(CoreConstants.INTERNET_CHECK_SUCCESS)) {
                            EliteSession.eLog.i(MODULE, "Internet Available, Registration Process Start");
                            spTask.saveString(CoreConstants.MONETIZATION_URL, config.getMonetizationURL());

                            IWiFiConfiguration configuration = new EliteWiFiAPI(wifiListner);
                            configuration.doRegistration(config.getSharedKey(), new PojoSubscriber());
                        } else {
                            EliteSession.eLog.i(MODULE, "Please check Internet Connection");
                        }
                    }
                }, CoreConstants.INTERNET_CHECK_URL).execute();
            }
        } else {
            try {
                EliteSession.eLog.i(MODULE, "Notification Click or Boot Device to Call ANDSF Policy Evaluate");

                if (spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER)) {

                    if (CommonWiFiUtility.checkValidOperator(ElitelibUtility.getKeyPairvalue(KEY_MCC, KEY_MCC), ElitelibUtility.getKeyPairvalue(KEY_MNC, KEY_MNC), Seprator)) {

                        ComponentName comp = new ComponentName(context.getPackageName(), BackgroundProcessService.class.getName());
                        ComponentName service = context.startService(new Intent().setComponent(comp));
                        if (null == service) {
                            // something really wrong here
                            EliteSession.eLog.d(MODULE, "Could not start service " + comp.toString());
                        }

                        ANDSFUtility.callPolicyPull(context);
                        ANDSFUtility.callPolicyEvaluation(context);
                    } else {
                        EliteSession.eLog.i(MODULE, "MCC/MNC not Valid, Active Sim Not Valid operator");
                    }
                } else {
                    EliteSession.eLog.i(MODULE, "Registration Required.");
                }
            } catch (Exception e) {
                Log.e(MODULE, "Error while Notification " + e.getMessage());
            }

        }
    }

    private final OnWiFiTaskCompleteListner wifiListner = new OnWiFiTaskCompleteListner() {

        @Override
        public void onWiFiTaskComplete(String result) {
        }

        @Override
        public void isWiFiInRange(boolean status) {
        }

        @Override
        public void getResponseData(String responseData) {
            EliteSession.eLog.i(MODULE, "Monetization registration Response");
            EliteSession.eLog.i(MODULE, responseData);

            try {
                JSONObject object = new JSONObject(responseData);
                if (object.getInt(EliteWiFIConstants.RESPONSECODE) == 1) {


                    ANDSFUtility.invokeDynamicParameter(connectionManagerListner);
                } else {
                    EliteSession.eLog.i(MODULE, " Registration not success");
                }
            } catch (JSONException je) {
                EliteSession.eLog.e(MODULE, " json excepotion -" + je.getMessage());
            }
        }

        @Override
        public void onWiFiScanComplete(List<String> ssidList) {

        }
    };


    private final ConnectionManagerListner connectionManagerListner = new ConnectionManagerListner() {
        @Override
        public void onConnectionSuccess(String result, int requestID) {

            if (result == null || result.equals("")) {
                EliteSession.eLog.e(MODULE, "Server Not Reachable or Call after some time");
            } else {

                if (requestID == MONETIZATION_UPDATEDEVICETOKEN_REQUESTID) {
                    EliteSession.eLog.i(MODULE, "Update DeviceToken response");
                    EliteSession.eLog.i(MODULE, result);

                } else if (requestID == MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID) {
                    EliteSession.eLog.i(MODULE, result);
                    try {

                        PojoServerKeyMapping resObj = new Gson().fromJson(result, PojoServerKeyMapping.class);
                        if (resObj != null) {
                            if (resObj.getResponseCode() == 1) {
                                // set values in preferences from getparameter response
                                ElitelibUtility.setKeyPairValue(resObj.getResponseData());

                                if (CommonWiFiUtility.checkValidOperator(ElitelibUtility.getKeyPairvalue(KEY_MCC, KEY_MCC), ElitelibUtility.getKeyPairvalue(KEY_MNC, KEY_MNC), Seprator)) {

                                    ComponentName comp = new ComponentName(context.getPackageName(), BackgroundProcessService.class.getName());
                                    ComponentName service = context.startService(new Intent().setComponent(comp));
                                    if (null == service) {
                                        // something really wrong here
                                        EliteSession.eLog.d(MODULE, "Could not start service " + comp.toString());
                                    }

                                    spTask.saveLong(SharedPreferencesConstant.NEXT_USERPARAMETERINTERVAL, System.currentTimeMillis() +
                                            Integer.parseInt(spTask.getString(SharedPreferencesConstant.ANDSF_USERPARAMETERINTERVAL)) * 60 * 1000);
                                    ANDSFUtility.callPolicyPull(context);
                                    ANDSFUtility.callPolicyEvaluation(context);

                                    ElitelibUtility.geGSMCellLocation(context);

                                } else {
                                    EliteSession.eLog.i(MODULE, "MCC/MNC not Valid, Active Sim Not Valid operator");
                                }

                                spTask.saveBoolean(CoreConstants.DO_REGISTER, true);
                                /*
                                    During registration first time get paramter already invoked so this will skip it for the first time in Policy Pull Receiver.
                                 */
                                spTask.saveInt(KEY_CURRENTTRIGGERCOUNT, 1);
                            } else {
                                EliteSession.eLog.d(MODULE, resObj.getResponseMessage());
                            }
                        } else {
                            EliteSession.eLog.e(MODULE, "getDynamicParameter result null");
                        }
                    } catch (JsonParseException e) {
                        EliteSession.eLog.e(MODULE, "getDynamicParameter -" + e.getMessage());
                    } catch (Exception e) {
                        EliteSession.eLog.e(MODULE, "getDynamicParameter -" + e.getMessage());
                    }
                }
            }
        }

        @Override
        public void onConnectionFailure(String message, int resultCode) {
            EliteSession.eLog.i(MODULE, message);
        }
    };

    public void updateNotificationToken(final String notificationToken) throws Exception {

        if (spTask == null) {
            throw new Exception("Please invokeANDSFClinet method call, before updateNotificationToken call ");
        } else if (notificationToken == null || notificationToken.equals("")) {
            throw new Exception("NotificationToken required, Please Enter Notification Token");
        } else {
            if (spTask.getBooleanFirstFalse(CoreConstants.DO_REGISTER)) {

                new InterNetAvailabilityCheckTask(new OnInternetCheckCompleteListner() {
                    @Override
                    public void isInterNetAvailable(int requestId, String status, String json) {

                        if (status.equals(CoreConstants.INTERNET_CHECK_SUCCESS)) {
                            EliteSession.eLog.i(MODULE, "Internet Available, UpdateDeviceToken Process Start");

                            try {
                                JSONObject jsonObject = new JSONObject();
                                if (spTask.getString(CoreConstants.USERIDENTITY) != null && !spTask.getString(CoreConstants.USERIDENTITY).equals("")) {

                                    try {
                                        jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                                        jsonObject.put(CoreConstants.REGISTEREDDEVICEID, notificationToken);
                                        jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                                        jsonObject.put(CoreConstants.imsi, spTask.getString(CoreConstants.imsi));
                                        jsonObject.put(CoreConstants.imei, spTask.getString(CoreConstants.imei));
                                        jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));

                                        ConnectionManagerClass connectionManagerClass = new ConnectionManagerClass(connectionManagerListner, MONETIZATION_UPDATEDEVICETOKEN_REQUESTID);
                                        connectionManagerClass.execute(jsonObject.toString(), spTask.getString(CoreConstants.MONETIZATION_URL) + CoreConstants.MONETIZATION_UPDATEDEVICETOKEN);

                                    } catch (JSONException e) {

                                        EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service Device ID update");
                                        EliteSession.eLog.e(MODULE, e.getMessage());
                                    }
                                }
                            } catch (Exception e) {
                                EliteSession.eLog.e(MODULE, " Main Error onHandleIntent invoking service Device ID update");
                                EliteSession.eLog.e(MODULE, e.getMessage());
                            }
                        } else {
                            EliteSession.eLog.i(MODULE, "Please check Internet Connection");
                        }
                    }
                }, CoreConstants.INTERNET_CHECK_URL).execute();

            } else {
                EliteSession.eLog.i(MODULE, "Regitration required, Please invokeANDSFClinet method");
            }
        }
    }
}
