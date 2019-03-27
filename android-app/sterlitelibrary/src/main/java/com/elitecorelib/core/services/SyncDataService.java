package com.elitecorelib.core.services;

import android.app.IntentService;
import android.content.Intent;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.dao.DBOperation;
import com.elitecorelib.core.dao.DBQueryFieldConstants;
import com.elitecorelib.core.pojo.PojoAdMapping;
import com.elitecorelib.core.pojo.PojoConfigModelResponse;
import com.elitecorelib.core.pojo.PojoServerKeyMapping;
import com.elitecorelib.core.pojo.PojoServiceResponseAdMapping;
import com.elitecorelib.core.pojo.PojoServiceResponseLocation;
import com.elitecorelib.core.pojo.PojoServiceResponseSyncData;
import com.elitecorelib.core.pojo.PojoServiceResponseVersionData;
import com.elitecorelib.core.pojo.PojoServiceResponseWiFiData;
import com.elitecorelib.core.pojo.PojoSyncData;
import com.elitecorelib.core.pojo.PojoVersionData;
import com.elitecorelib.core.pojo.PojoWiFiProfiles;
import com.elitecorelib.core.utility.DefaultConfig;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chirag Parmar on 17-Mar-17.
 */

public class SyncDataService extends IntentService implements ConnectionManagerCompleteListner {

    private final String MODULE = "SyncDataService";
    private HashMap<String, PojoSyncData> serverSyncDataMap = new HashMap<String, PojoSyncData>();
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    public SyncDataService() {
        super(SyncDataService.class.getSimpleName());
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        try {

            EliteSession.eLog.i(MODULE, "onHandleIntent invoking service for Sync Data");
            JSONObject jsonObject = new JSONObject();
            if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null &&
                    spTask.getString(CoreConstants.USERIDENTITY) != null && spTask.getString(CoreConstants.USERIDENTITY).length() != 0) {
                try {

                    jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                    jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                    jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));
                    ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_SYNCDATA_REQUESTID);
                    task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_SYNCDATA);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service for Sync Data");
                    EliteSession.eLog.e(MODULE, e.getMessage());
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestId) {
        // TODO Auto-generated method stub
        if (requestId == CoreConstants.MONETIZATION_SYNCDATA_REQUESTID && result != null) {
            EliteSession.eLog.d(MODULE, " Response for " + CoreConstants.MONETIZATION_SYNCDATA + " " + result);
            try {
                EliteSession.eLog.d(MODULE, "Parsing Service Response for Sync Data");
                PojoServiceResponseSyncData syncDataServiceList = new Gson().fromJson(result, PojoServiceResponseSyncData.class);
                if (syncDataServiceList != null && syncDataServiceList.getResponseCode() == 1) {
                    if (syncDataServiceList.getResponseData() != null) {
                        for (PojoSyncData pojosyncdata : syncDataServiceList.getResponseData()) {
                            serverSyncDataMap.put(pojosyncdata.getModuleName(), pojosyncdata);
                        }
                    }

                    ArrayList<PojoSyncData> dbSyncDataList = DBOperation.getDBHelperOperation().getAllSyncData();

                    if (dbSyncDataList == null) {
                        EliteSession.eLog.d(MODULE, " Sync Data not availabe in db inserting first time");
                        if (syncDataServiceList.getResponseData() != null) {
                            for (PojoSyncData pojoSyncData : syncDataServiceList.getResponseData()) {
                                DBOperation.getDBHelperOperation().insertSyncData(pojoSyncData.getPojoSyncDataId(), pojoSyncData.getModuleName(), pojoSyncData.getModifiedDate());
                            }
                        }
                    } else {
                        EliteSession.eLog.d(MODULE, " Sync data available in db, check for any  module update");
                        try {
                            for (PojoSyncData pojoSyncData : dbSyncDataList) {

                                if (pojoSyncData.getModifiedDate().equals(serverSyncDataMap.get(pojoSyncData.getModuleName()).getModifiedDate()) ) {
                                    EliteSession.eLog.d(MODULE, " It seems Module is  updated for  " + pojoSyncData.getModuleName());
                                } else {
                                    EliteSession.eLog.d(MODULE, " It seems Module is Not updated for  " + pojoSyncData.getModuleName());
                                    EliteSession.eLog.d(MODULE, " Last Update Date  " + pojoSyncData.getModifiedDate());
                                    /*
                                     * Advertisment Module Update invoke
									 */
                                    if (pojoSyncData.getModuleName().equals(CoreConstants.MODULE_DYNAMICADVERTISEMENT)) {
                                        EliteSession.eLog.d(MODULE, " Calling module update service for " + CoreConstants.MODULE_DYNAMICADVERTISEMENT);
                                        EliteSession.eLog.d(MODULE, "CaLLING ADVERTISEMENT SERVICE ");
                                        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null &&
                                                spTask.getString(CoreConstants.USERIDENTITY) != null) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {

                                                jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                                                jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                                                jsonObject.put(CoreConstants.DEVICECATEGORY, ElitelibUtility.getDeviceResolution());
                                                jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));


                                                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_GETDYNAMICADVERTISE_REQUESTID);
                                                task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETDYNAMICADVERTISE);
                                            } catch (Exception e) {
                                                EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service for Sync Data " + CoreConstants.MODULE_DYNAMICADVERTISEMENT);
                                                EliteSession.eLog.e(MODULE, e.getMessage());
                                            }
                                        }
                                    }
                                    /*
                                     * Wifi Management Update Service Invoke
									 */
                                    else if (pojoSyncData.getModuleName().equals(CoreConstants.MODULE_WIFIMANAGEMENT)) {
                                        EliteSession.eLog.d(MODULE, " Calling module update service for " + CoreConstants.MODULE_WIFIMANAGEMENT);
                                        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null &&
                                                spTask.getString(CoreConstants.USERIDENTITY) != null) {
                                            try {
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                                                jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                                                jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));

                                                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_GETWIFIDATA_REQUESTID);
                                                task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETWIFIDATA);
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service for Sync Data " + CoreConstants.MODULE_WIFIMANAGEMENT);
                                                EliteSession.eLog.e(MODULE, e.getMessage());
                                            }
                                        }
                                    }
                                    /*
									 * HOTSPOT location update service invoke
									 */
                                    else if (pojoSyncData.getModuleName().equals(CoreConstants.MODULE_LOCATION) ) {
                                        EliteSession.eLog.d(MODULE, " Calling module update service for " + CoreConstants.MODULE_LOCATION);
                                        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null &&
                                                spTask.getString(CoreConstants.USERIDENTITY) != null) {
                                            try {
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                                                jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                                                jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));

                                                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_SYNCLOCATION_REQUESTID);
                                                task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_SYNCLOCATION);
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service for Sync Data " + CoreConstants.MODULE_LOCATION);
                                                EliteSession.eLog.e(MODULE, e.getMessage());
                                            }
                                        }
                                    }
									/*
									 *   server configuration update service invoke
									 */
                                    else if (pojoSyncData.getModuleName().equals(CoreConstants.MODULE_SERVERCONFIG) ) {
                                        EliteSession.eLog.d(MODULE, " Calling module update service for " + CoreConstants.MODULE_SERVERCONFIG);
                                        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null &&
                                                spTask.getString(CoreConstants.USERIDENTITY) != null) {
                                            try {
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                                                jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                                                jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));
                                                jsonObject.put(CoreConstants.APPLANGUAGE, spTask.getString(CoreConstants.ADVERTISEMENT_LANGUAGE));

                                                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_GETSYSTEMCONFIGURATION_REQUESTID);
                                                task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETSYSTEMCONFIGURATION);
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service for Sync Data " + CoreConstants.MODULE_SERVERCONFIG);
                                                EliteSession.eLog.e(MODULE, e.getMessage());
                                            }
                                        }
                                    }
									/*
									 * Version module update service invoke
									 */
                                    else if (pojoSyncData.getModuleName().equals(CoreConstants.MODULE_VERSION) ) {
                                        EliteSession.eLog.d(MODULE, " Calling module update service for " + CoreConstants.MODULE_VERSION);
                                        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null &&
                                                spTask.getString(CoreConstants.USERIDENTITY) != null) {
                                            try {
                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                                                jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                                                jsonObject.put(CoreConstants.VERSION, ElitelibUtility.getCurrentAppVersion(getApplicationContext()));
                                                jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));

                                                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_GETLATESTVERSION_REQUESTID);
                                                task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETLATESTVERSION);
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service for Sync Data " + CoreConstants.MODULE_VERSION);
                                                EliteSession.eLog.e(MODULE, e.getMessage());
                                            }
                                        }
                                    }
                                    /**
                                     * Application configureation param update call
                                     */
                                    else if (pojoSyncData.getModuleName().equals(CoreConstants.MODULE_APPCONFIGURATIONPARAM) ) {
                                        EliteSession.eLog.d(MODULE, " Calling module update service for " + CoreConstants.MODULE_APPCONFIGURATIONPARAM);
                                        if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null &&
                                                spTask.getString(CoreConstants.USERIDENTITY) != null) {

                                            try {

                                                JSONObject jsonObject = new JSONObject();
                                                jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication()
                                                        .getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                                                jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));
                                                jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);

                                                ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this,
                                                        CoreConstants.MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID);
                                                task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() +
                                                        CoreConstants.MONETIZATION_GET_DYNAMIC_PARAMETER_URL);

                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service for Sync Data " + CoreConstants.MODULE_VERSION);
                                                EliteSession.eLog.e(MODULE, e.getMessage());
                                            }
                                        }
                                    }


                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            EliteSession.eLog.d(MODULE, "Error while module update " + e.getMessage());
                        }

                        if(serverSyncDataMap.size() != dbSyncDataList.size()) {

                            EliteSession.eLog.d(MODULE,"Module Count Change, Re-insert to Module");

                            DBOperation.getDBHelperOperation().deleteAllValues(DBQueryFieldConstants.TABLENAME_SYNCDATA_MAPPING);

                            if (syncDataServiceList.getResponseData() != null) {
                                for (PojoSyncData pojoSyncData : syncDataServiceList.getResponseData()) {
                                    DBOperation.getDBHelperOperation().insertSyncData(pojoSyncData.getPojoSyncDataId(), pojoSyncData.getModuleName(), pojoSyncData.getModifiedDate());
                                }
                            }
                        }
                    }
                    spTask.saveBoolean(CoreConstants.IS_FIRSTTIME_SYNC, false);
                    EliteSession.eLog.d(MODULE, " Reset the SYnc timer interval with the current time");
                    try {
                        int syncTime = Integer.parseInt(spTask.getString(CoreConstants.SYNCINTERVALTIME));
                        spTask.saveLong(CoreConstants.NEXT_SYNCINTERVALTIME, System.currentTimeMillis() + (syncTime * 60 * 1000));
                    } catch (Exception e) {
                        // TODO: handle exception
                        EliteSession.eLog.d(MODULE, "setting config property Errot to set next time interval");
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.e(MODULE, "Error while parsing the service response for Sync Data");
                EliteSession.eLog.e(MODULE, e.getMessage());
            }
        }
		/*
		 * Advertisment service response
		 */
        else if (requestId == CoreConstants.MONETIZATION_GETDYNAMICADVERTISE_REQUESTID && result != null) {
            EliteSession.eLog.d(MODULE, " Response for " + CoreConstants.MONETIZATION_GETDYNAMICADVERTISE + " " + result);
            try {
                Gson gson = new Gson();
                PojoServiceResponseAdMapping resObj = gson.fromJson(result, PojoServiceResponseAdMapping.class);
                if (resObj.getResponseCode() == 1) {
                    if (resObj.getResponseData() != null && resObj.getResponseData().size() > 0) {


                        for (int j = 0; j < resObj.getResponseData().size(); j++) {
                            PojoAdMapping adMappingObj = resObj.getResponseData().get(j);
                            DBOperation.getDBHelperOperation().insertAdMapping(adMappingObj.getScreenLocation(), adMappingObj.getDeviceCatagory(), adMappingObj.getInvocationCode(), adMappingObj.getScreenName());

                        }
                        LibraryApplication.getLibraryApplication().setAdMappingHashMap(DBOperation.getDBHelperOperation().getAdMappingHashMap());
                        DBOperation.getDBHelperOperation().updateSyncData(CoreConstants.MODULE_DYNAMICADVERTISEMENT, serverSyncDataMap.get(CoreConstants.MODULE_DYNAMICADVERTISEMENT).getModifiedDate());

                        spTask.saveBoolean(CoreConstants.HOTSPOT_SYNC_STATUS, true);

                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.e(MODULE, " Error while parsing advertisement configuration " + e.getMessage());
            }

        }
		/*
		 * WiFi data service response parsing
		 */
        else if (requestId == CoreConstants.MONETIZATION_GETWIFIDATA_REQUESTID && result != null) {
            EliteSession.eLog.d(MODULE, " Response for " + CoreConstants.MONETIZATION_GETWIFIDATA + " " + result);
            try {
                Gson gson = new Gson();
                PojoServiceResponseWiFiData resObj = gson.fromJson(result, PojoServiceResponseWiFiData.class);
                if (resObj.getResponseCode() == 1) {
                    if (resObj.getResponseData() != null) {
                        DBOperation dbHelperOperationObj = DBOperation.getDBHelperOperation();

                        dbHelperOperationObj.deleteWhere(DBQueryFieldConstants.TABLENAME_CONNECTION, new String[]{DBQueryFieldConstants.IS_LOCAL}, new String[]{String.valueOf(0)});
                        dbHelperOperationObj.deleteWhere(DBQueryFieldConstants.TABLENAME_PROFILE, new String[]{DBQueryFieldConstants.IS_LOCAL}, new String[]{String.valueOf(0)});

						/*
						 * To update wifi settings functionality need to be written here
						 */
//                        List<PojoWiFiProfiles> profileList = resObj.getResponseData();
//                        for (PojoWiFiProfiles pojoWiFiProfiles : profileList) {
//                            EliteSession.eLog.d(MODULE, String.valueOf(pojoWiFiProfiles.getWifiSettingSet()));
//
//                            ElitelibUtility.setProfile(pojoWiFiProfiles);
//                            ElitelibUtility.setConnections(pojoWiFiProfiles.getAndroidSettingName(), pojoWiFiProfiles.getWifiSettingSet());
//
//                        }
                        DBOperation.getDBHelperOperation().updateSyncData(CoreConstants.MODULE_WIFIMANAGEMENT, serverSyncDataMap.get(CoreConstants.MODULE_WIFIMANAGEMENT).getModifiedDate());
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.e(MODULE, " Error while parsing wifi configuration " + e.getMessage());
            }
        }
		/*
		 * Sync Location Data service Parsing
		 */
        else if (requestId == CoreConstants.MONETIZATION_SYNCLOCATION_REQUESTID && result != null) {
            EliteSession.eLog.d(MODULE, " Response for " + CoreConstants.MONETIZATION_SYNCLOCATION);
            try {
                EliteSession.eLog.d(MODULE, " Parsing and saving location data");
                Gson gson = new Gson();
                PojoServiceResponseLocation resObjLoc = gson.fromJson(result, PojoServiceResponseLocation.class);
                if (resObjLoc.getResponseData() != null && resObjLoc.getResponseCode() == 1 && resObjLoc.getResponseData().size() > 0) {

                    EliteSession.eLog.d(MODULE, " Removing  old location data from db");
                    DBOperation.getDBHelperOperation().deleteAllValues(DBQueryFieldConstants.TABLENAME_LOCATION);
                    DBOperation.getDBHelperOperation().insertBulkLocation(resObjLoc);
                    DBOperation.getDBHelperOperation().updateSyncData(CoreConstants.MODULE_LOCATION, serverSyncDataMap.get(CoreConstants.MODULE_LOCATION).getModifiedDate());


                    EliteSession.eLog.d(MODULE, " Refreshing preferneces with new  location data");
                    Thread tUpdate = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            ElitelibUtility.loadAllLocation();
                        }
                    });
                    tUpdate.start();

                }
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.d(MODULE, "Error while parsing location data " + e.getMessage());
            }

        }
		/*
		 *  Get System Server configuration response parsing.
		 */
        else if (requestId == CoreConstants.MONETIZATION_GETSYSTEMCONFIGURATION_REQUESTID && result != null) {
            EliteSession.eLog.d(MODULE, " Response for " + CoreConstants.MONETIZATION_GETSYSTEMCONFIGURATION + " " + result);
            try {
                final PojoConfigModelResponse resObj = new Gson().fromJson(result, PojoConfigModelResponse.class);
                if (resObj.getResponseCode() == 1 && resObj.getResponceData() != null) {
                    // TODO Auto-generated method stub
                    ElitelibUtility.updateServerConfigProperty(resObj.getResponceData());

                    int regVal = DBOperation.getDBHelperOperation().getRegistration(spTask.getString(CoreConstants.imei));


                    if (ElitelibUtility.getConfigProperty(CoreConstants.NFCALLBACKMODE).equals(CoreConstants.NFCALLBACKMODETIME_VALUE)) {

                        //ElitelibUtility.loadAllLocation();
                        ElitelibUtility.locationUpdateCall(LibraryApplication.getLibraryApplication().getLibraryContext());

                        EliteSession.eLog.d(MODULE, "onReceive NFCALLBACKMODE value is 1 for time base call,scheduling location service call alarm, with given interval - ");
                    } else if (ElitelibUtility.getConfigProperty(CoreConstants.NFCALLBACKMODE).equals(CoreConstants.NFCALLBACKMODEDISTANCE_VALUE) ) {
                        EliteSession.eLog.d(MODULE, "onReceive NFCALLBACKMODE value is 2 for distance base call,calling location service only once");

                        //ElitelibUtility.loadAllLocation();
                        ElitelibUtility.locationUpdateCall(LibraryApplication.getLibraryApplication().getLibraryContext());

                    }
                    // Analytic process
                    ElitelibUtility.eventAnalyticProcess(this);

                    if (regVal == 1) {
                        spTask.saveString(SharedPreferencesConstant.ACTIVEPROFILE, "");
                        spTask.saveString(SharedPreferencesConstant.ACTIVECONNECTION, "");

                        DBOperation.getDBHelperOperation().deleteAllValues(DBQueryFieldConstants.TABLENAME_CONNECTION);
                        DBOperation.getDBHelperOperation().deleteAllValues(DBQueryFieldConstants.TABLENAME_PROFILE);
                        DBOperation.getDBHelperOperation().deleteAllValues(DBQueryFieldConstants.TABLENAME_RELATION);

                        if (spTask.getString(CoreConstants.WIFISETTINGS_FROM).equals(CoreConstants.WIFISETTINGS_SERVER) ) {
                            try {
                                if (LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY) != null &&
                                        spTask.getString(CoreConstants.USERIDENTITY) != null) {
                                    //String wifiServerData=null;
                                    EliteSession.eLog.d(MODULE, " Calling module update service for " + CoreConstants.MODULE_WIFIMANAGEMENT);
                                    try {
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                                        jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                                        jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));

                                        ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants.MONETIZATION_GETWIFIDATA_REQUESTID);
                                        task.execute(jsonObject.toString(), LibraryApplication.getGetterSetterObj().getSERVERHOSTMONETIZATION() + CoreConstants.MONETIZATION_GETWIFIDATA);
                                    } catch (Exception e) {
                                        EliteSession.eLog.e(MODULE, "Error onHandleIntent invoking service for Sync Data " + CoreConstants.MODULE_WIFIMANAGEMENT);
                                        EliteSession.eLog.e(MODULE, e.getMessage());
                                    }

                                }
                            } catch (Exception e) {
                                EliteSession.eLog.e(MODULE, e.getMessage());
                            }
                        } else if (spTask.getString(CoreConstants.WIFISETTINGS_FROM).equals(CoreConstants.WIFISETTINGS_APPLICATION) ) {
                            String defaultProfile = ElitelibUtility.getConfigProperty(CoreConstants.DEFAULT_PROFILE_NAME);
                            if (defaultProfile != null) {
                                // Configuration file is available. Therefore set
                                // default profile, connection and create default wifi access
                                // point.
                                DefaultConfig.getInstance().setDefaultProfile();
                                DefaultConfig.getInstance().setDefaultConnection(ElitelibUtility.getConfigProperty(CoreConstants.DEFAULT_AUTHENTICATION_METHOD));

                            }
                        }
                    }
                }
                DBOperation.getDBHelperOperation().updateSyncData(CoreConstants.MODULE_SERVERCONFIG, serverSyncDataMap.get(CoreConstants.MODULE_SERVERCONFIG).getModifiedDate());
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.e(MODULE, " Error while parsing server configuration " + e.getMessage());
            }
        }
		/*
		 * Version Module update response parsing
		 */
        else if (requestId == CoreConstants.MONETIZATION_GETLATESTVERSION_REQUESTID && result != null) {
            EliteSession.eLog.d(MODULE, " Response for " + CoreConstants.MONETIZATION_GETLATESTVERSION + " " + result);
            try {
                Gson gson = new Gson();
                if (result.contains(CoreConstants.VERSION_ALREADYUPDATED)) {
                    spTask.saveBoolean(SharedPreferencesConstant.VERSION_ALREADY_UPDATED, true);
                    spTask.saveString(SharedPreferencesConstant.VERSION_FORCE_UPGRADE, CoreConstants.VERSION_FORCEUPDATE_FALSE);
                    spTask.saveString(SharedPreferencesConstant.VERSION_UPDATE_URL, "");
                    spTask.saveString(SharedPreferencesConstant.VERSION_NEW_FROM_SERVER, ElitelibUtility.getCurrentAppVersion(getApplicationContext()));
                } else {
                    final PojoServiceResponseVersionData resObj = gson.fromJson(result, PojoServiceResponseVersionData.class);
                    if (resObj.getResponseCode() == 1 && resObj.getResponseData() != null) {
                        PojoVersionData versionData = resObj.getResponseData();
                        EliteSession.eLog.d(MODULE, versionData.getVersion());
                        spTask.saveBoolean(CoreConstants.NEED_DEVICEID_UPDATED, true);
                        spTask.saveBoolean(SharedPreferencesConstant.VERSION_ALREADY_UPDATED, false);
                        spTask.saveString(SharedPreferencesConstant.VERSION_FORCE_UPGRADE, versionData.getForceUpdate());
                        spTask.saveString(SharedPreferencesConstant.VERSION_UPDATE_URL, versionData.getURL());
                        spTask.saveString(SharedPreferencesConstant.VERSION_NEW_FROM_SERVER, versionData.getVersion());
                        spTask.saveBoolean(SharedPreferencesConstant.MONETIZATIONREG, true);
                    }
                }
                EliteSession.eLog.d(MODULE,"Update Date in Database "+serverSyncDataMap.get(CoreConstants.MODULE_VERSION).getModifiedDate());
                DBOperation.getDBHelperOperation().updateSyncData(CoreConstants.MODULE_VERSION, serverSyncDataMap.get(CoreConstants.MODULE_VERSION).getModifiedDate());
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.e(MODULE, " Error while parsing server configuration " + e.getMessage());
            }
        }
        /*
		 * Version Module update response parsing
		 */
        else if (requestId == CoreConstants.MONETIZATION_GET_DYNAMIC_PARAMETER_REQUESTID && result != null) {
            EliteSession.eLog.d(MODULE, " Response for " + CoreConstants.MONETIZATION_GET_DYNAMIC_PARAMETER_URL + " " + result);
            EliteSession.eLog.d(MODULE, "Dynamic parameter method response  :: " + result);
            try {

                PojoServerKeyMapping resObj = new Gson().fromJson(result, PojoServerKeyMapping.class);
                if (resObj != null) {
                    if (resObj.getResponseCode() == 1) {
                        // set values in preferences from getparameter response
                        ElitelibUtility.setKeyPairValue(resObj.getResponseData());
                        DBOperation.getDBHelperOperation().updateSyncData(CoreConstants.MODULE_APPCONFIGURATIONPARAM, serverSyncDataMap.get(CoreConstants.MODULE_APPCONFIGURATIONPARAM).getModifiedDate());
                    } else {
                        EliteSession.eLog.d(MODULE, resObj.getResponseMessage());
                    }
                } else {
                    EliteSession.eLog.e(MODULE, "result null");
                }
            } catch (JsonParseException e) {
                EliteSession.eLog.e(MODULE, "json exception - "+e.getMessage());
            } catch (Exception e) {
                EliteSession.eLog.e(MODULE, "exception - "+e.getMessage());
            }
        }
    }
}
