package com.sterlite.dccmappfordealersterlite.Utils;

import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.dao.DBOperation;
import com.elitecorelib.core.pojo.PojoAdMapping;
import com.elitecorelib.core.pojo.PojoServiceResponseAdMapping;
import com.elitecorelib.core.pojo.PojoSubscriber;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;
import com.elitecorelib.core.utility.SharedPreferencesTask;
import com.google.gson.Gson;

import org.json.JSONObject;
import static com.sterlite.dccmappfordealersterlite.Utils.AppUtils.loadJSONFromAsset;

/**
 * Created by harshesh.soni on 2/3/2017.
 */
public class AppAdServiceCall implements ConnectionManagerCompleteListner {

    private static final String MODULE = "BSNLServiceCall";
    private PojoSubscriber subscriberData;
    private ConnectionManagerCompleteListner callback;
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    public AppAdServiceCall(ConnectionManagerCompleteListner callback) {
        this.callback = callback;
    }

    public void showAdvertisement() {

       /* try {
            DBOperation.getDBHelperOperation().open();
            if (DBOperation.getDBHelperOperation().getAdMappingHashMap() == null || DBOperation.getDBHelperOperation().getAdMappingHashMap().size() == 0) {
                EliteSession.eLog.d(MODULE, "CaLLING ADVERTISEMENT SERVICE ");
                //Call for ad parameter configuration
                JSONObject jsonObject = new JSONObject();
                try {
//                    jsonObject.put(CoreConstants.SECRETKEY, LibraryApplication.getLibraryApplication()
//                            .getlibrarySharedPreferences().getString(CoreConstants.SECRETKEY));
                    jsonObject.put(CoreConstants.SECRETKEY, "a2281564a95075783b89a93b81a7983debe61de909f6eb3fc6eb6e61654c03d2");
                    jsonObject.put(CoreConstants.operatingSystem, CoreConstants.operatingSystemValue);
                    jsonObject.put(CoreConstants.DEVICECATEGORY, AppUtils.getDeviceResolution());
//                    jsonObject.put(CoreConstants.USERIDENTITY, spTask.getString(CoreConstants.USERIDENTITY));
                    jsonObject.put(CoreConstants.USERIDENTITY, "356411095507439");

                    ConnectionManagerTaskNew task = new ConnectionManagerTaskNew(this, CoreConstants
                            .MONETIZATION_GETDYNAMICADVERTISE_REQUESTID);
                    task.execute(jsonObject.toString(), CoreConstants.BASE_URL_AD + CoreConstants.GETDYNAMICADVERTISEMENT);
                } catch (Exception e) {
                    EliteSession.eLog.e(MODULE, "Error while fetching advertise data " + e.getMessage());
                }
            } else {
                EliteSession.eLog.d(MODULE, "ADMAPPING IS ALREADY DONE ");
                LibraryApplication.getLibraryApplication().setAdMappingHashMap(DBOperation.getDBHelperOperation().
                        getAdMappingHashMap());
            }
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "Error while doing admapping " + e.getMessage()+"");
        }*/
    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestId) {
       /* result = loadJSONFromAsset("advertiesment.json");
        if (requestId == CoreConstants.MONETIZATION_GETDYNAMICADVERTISE_REQUESTID && result != null) {
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
//                        DBOperation.getDBHelperOperation().updateSyncData(CoreConstants.MODULE_DYNAMICADVERTISEMENT, serverSyncDataMap.get(CoreConstants.MODULE_DYNAMICADVERTISEMENT).getModifiedDate());
//                        spTask.saveBoolean(CoreConstants.HOTSPOT_SYNC_STATUS, true);

                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
                EliteSession.eLog.e(MODULE, " Error while parsing advertisement configuration " + e.getMessage());
            }

        }
        callback.onConnnectionManagerTaskComplete(result, requestId);*/
    }
}
