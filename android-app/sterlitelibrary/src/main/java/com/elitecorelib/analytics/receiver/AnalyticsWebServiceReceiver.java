package com.elitecorelib.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.elitecore.wifi.listener.OnInternetCheckCompleteListner;
import com.elitecorelib.analytics.constants.WSConstants;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadFail;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadSuccess;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadPojo;
import com.elitecorelib.analytics.pojo.AnalyticsPolicyDetails;
import com.elitecorelib.analytics.pojo.AnalyticsWSResponse;
import com.elitecorelib.analytics.pojo.BaseDTO;
import com.elitecorelib.analytics.realm.AnalyticsRealmPojoManager;
import com.elitecorelib.analytics.utility.AnalyticsUtility;
import com.elitecorelib.analytics.webservice.WebServiceCall;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.InterNetAvailabilityCheckTask;
import com.elitecorelib.core.utility.SharedPreferencesTask;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.realm.RealmModel;
import io.realm.Sort;

public class AnalyticsWebServiceReceiver extends BroadcastReceiver implements ConnectionManagerCompleteListner {

    private final static String MODULE = "AnalyticsWebServiceReceiver";
    private static AnalyticsOffloadPojo analyticsOffloadPojo;
    int i = 0;
    Class[] classes = new Class[]{AnalyticsOffloadSuccess.class, AnalyticsOffloadFail.class, AnalyticsPolicyDetails.class};
    private Context mContext;
    private SharedPreferencesTask spTask = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    Map<Class,Double> percentageByClass = new LinkedHashMap<>();
    @Override
    public void onReceive(Context context, Intent intent) {
        percentageByClass = AnalyticsUtility.getPercentageMap();
        EliteSession.eLog.d(MODULE, "onReceive Call Started");
        mContext = context;
        try {
            new InterNetAvailabilityCheckTask(new OnInternetCheckCompleteListner() {
                @Override
                public void isInterNetAvailable(int requestId, String status, String json) {
                    if (status.equals(CoreConstants.INTERNET_CHECK_SUCCESS)) {
                        EliteSession.eLog.i(MODULE, "Internet Available, Analytics Web service call Start");
                        callAnalyticsWebService();
                    } else {
                        EliteSession.eLog.i(MODULE, "Please check Internet Connection");
                    }
                }
            }, CoreConstants.INTERNET_CHECK_URL).execute();
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE, "onReceive error:: " + e.getMessage());
        }
    }

    public static void main(String arg[]) {

    }

    private void callAnalyticsWebService() {
        if (isRecordExist()) {
            int fetchcount = AnalyticsUtility.getAnalyticFetchCount();
            boolean isAnyRecordPresent = false;
            analyticsOffloadPojo = new AnalyticsOffloadPojo();
            int totalRecord = 0;
            int remainingRecord = 0;
            Set<Map.Entry<Class, Double>> entrySet = percentageByClass.entrySet();
            for(Map.Entry<Class,Double> entry : entrySet) {
                if (totalRecord >= fetchcount)
                    break;
                int numOfRecordToFetch = ((int) ((fetchcount * entry.getValue()) / 100)); //((1000 * 50)/100)
                numOfRecordToFetch += remainingRecord;
                List returnList = getRecords(entry.getKey(), numOfRecordToFetch);
                int listSize = 0;
                if (returnList != null && !returnList.isEmpty()) {
                    listSize = returnList.size();
                    addListInPojo(returnList);
                    isAnyRecordPresent = true;
                    totalRecord += listSize;
                }
                if (listSize < numOfRecordToFetch)
                    remainingRecord = numOfRecordToFetch - listSize;
                else
                    remainingRecord = 0;
            }
            /*if(remainingRecord > 0 && totalRecord < fetchcount) {
                for(Map.Entry<Class,Double> entry : entrySet) {
                    if (totalRecord >= fetchcount || remainingRecord <= 0)
                        break;
                    int numOfRecordToFetch = remainingRecord;
                    List returnList = getRecords(entry.getKey(), numOfRecordToFetch);
                    if (returnList != null && !returnList.isEmpty()) {
                        int listSize = returnList.size();
                        addListInPojo(returnList);
                        totalRecord += listSize;
                        remainingRecord -= listSize;
                    }
                }
            }*/
            if (isAnyRecordPresent)
                WebServiceCall.callAnalyticsWS(analyticsOffloadPojo, this, WSConstants.REQID_ANAYTIC_OFFLAOD_STATISTICS);
            //}
        }
    }

    private void addListInPojo(List list) {
        if (list != null && !list.isEmpty()) {
            Object firstElm = list.get(0);
            if (firstElm instanceof AnalyticsOffloadSuccess)
                analyticsOffloadPojo.addSuccess(list);
            else if (firstElm instanceof AnalyticsOffloadFail)
                analyticsOffloadPojo.addFail(list);
            else if (firstElm instanceof AnalyticsPolicyDetails)
                analyticsOffloadPojo.addPolicyfetch(list);
        }
    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestID) {
        EliteSession.eLog.d(MODULE, "RequestId:" + requestID + " Response :" + result);
        AnalyticsWSResponse response = AnalyticsUtility.getWSResponseObjFromRespose(result);
        if (WebServiceCall.isSuccessResponse(response)) {
            deleteRecords();
            if (isRecordExist())
                callAnalyticsWebService();
        } else {

        }
    }

    private boolean isRecordExist() {
        for (Class type : classes) {
            boolean isRecordExist = AnalyticsRealmPojoManager.getRealmManager(type).isRecordExist();
            if (isRecordExist)
                return true;
        }
        return false;
    }

    private void deleteRecords() {
        List<AnalyticsOffloadSuccess> successList = analyticsOffloadPojo.getSuccess();
        deleteRecords(successList, AnalyticsOffloadSuccess.class);
        List<AnalyticsOffloadFail> failList = analyticsOffloadPojo.getFail();
        deleteRecords(failList, AnalyticsOffloadFail.class);
        List<AnalyticsPolicyDetails> policyList = analyticsOffloadPojo.getPolicyfetch();
        deleteRecords(policyList, AnalyticsPolicyDetails.class);
    }

    private <T extends RealmModel> void deleteRecords(List<? extends BaseDTO> baseDTOList, Class<T> type) {
        if (baseDTOList != null && !baseDTOList.isEmpty()) {
            BaseDTO firstElem = baseDTOList.get(0);
            long startId = firstElem.getId();
            BaseDTO lstElem = baseDTOList.get(baseDTOList.size() - 1);
            long endtId = lstElem.getId();
            AnalyticsRealmPojoManager.getRealmManager(type).deleteById(startId, endtId);
        }
    }

    <T extends RealmModel> List<T> getRecords(Class<T> type, int numOfRecordToFetch) {
        List<T> successList = AnalyticsRealmPojoManager.getRealmManager(type).getLastRecord(numOfRecordToFetch, "id", Sort.ASCENDING);
        EliteSession.eLog.d(MODULE, type + " numOfRecordToFetch:" + numOfRecordToFetch + " Got Record from DB: "+ successList.size());
        return successList;
    }
}
