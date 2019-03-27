package com.elitecorelib.analytics.utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.elitecorelib.analytics.pojo.AnalyticsOffloadFail;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadPojo;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadSuccess;
import com.elitecorelib.analytics.pojo.AnalyticsPolicyDetails;
import com.elitecorelib.analytics.pojo.AnalyticsWSResponse;
import com.elitecorelib.analytics.realm.AnalyticsRealmClassInvoke;
import com.elitecorelib.analytics.realm.AnalyticsRealmPojoManager;
import com.elitecorelib.analytics.realm.RealmOperationType;
import com.elitecorelib.analytics.receiver.AnalyticsWebServiceReceiver;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.wifi.receiver.ANDSFPolicyPullReceiver;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class AnalyticsUtility {

    protected static final String MODULE = "AnalyticsUtility";

    private static final int BREQCODE_WEBSERVICERECEIVER_CONSTANT = 890528;

    public static String toJSON(Object obj) {
        Gson gson = new Gson();
        String jsonString = "";
        if (obj instanceof RealmObject || obj instanceof RealmResults) {
            Realm analyticsRealm = AnalyticsRealmClassInvoke.getAnalyticsRealm();
            RealmObject realmObject = (RealmObject) obj;
            jsonString = gson.toJson(analyticsRealm.copyFromRealm(realmObject));
        } else {
            jsonString = gson.toJson(obj);
        }
        return jsonString;
    }

    public static AnalyticsWSResponse getWSResponseObjFromRespose(String responseBody) {
        AnalyticsWSResponse analyticsWSResponse = null;
        if (responseBody != null && !responseBody.trim().isEmpty()) {
            Gson gson = new Gson();
            analyticsWSResponse = gson.fromJson(responseBody, AnalyticsWSResponse.class);
        }
        return analyticsWSResponse;
    }


    public static void scheduleAnalyticReportWSCall(Context ctx) {
        insertDummyRecord();
        Intent intent = new Intent(ctx, AnalyticsWebServiceReceiver.class);
        boolean isReportWSCallAlarmSet = (PendingIntent.getBroadcast(ctx, BREQCODE_WEBSERVICERECEIVER_CONSTANT, intent, PendingIntent.FLAG_NO_CREATE) != null);
        EliteSession.eLog.i(MODULE, "is Policy Alarm Set status - " + isReportWSCallAlarmSet);
        isReportWSCallAlarmSet = false;
        if (isReportWSCallAlarmSet) {
            EliteSession.eLog.i(MODULE, "Report WS Call Alarm Already set");
        } else {
//            int reportWSCallTimer = Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANALYTICS_WSCALLINTERVAL, "1440")); // 24 hr
            int reportWSCallTimer = Integer.parseInt("1440"); // 24 hr
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, BREQCODE_WEBSERVICERECEIVER_CONSTANT, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (reportWSCallTimer * 60 * 1000), pendingIntent);
            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (reportWSCallTimer * 1000), pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (60 * 60 * 1000), pendingIntent);
            EliteSession.eLog.d(MODULE, "Scheduled AnalyticsWebServiceReceiver for every second :" + reportWSCallTimer);
        }
    }

    public static void cancelAnalyticReportWSCall(Context ctx) {
        Intent intent = new Intent(ctx, AnalyticsWebServiceReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, BREQCODE_WEBSERVICERECEIVER_CONSTANT, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }


    public static int getAnalyticFetchCount() {
//        return Integer.parseInt(ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANLTCS_RECORD_FETCH_COUNT, "15"));
        return Integer.parseInt("15");
    }


    private static void insertDummyRecord() {
        insertSuccessDummyRecord();
        insertFailDummyRecord();
        insertPolicyFetchRecord();
    }

    private static void insertSuccessDummyRecord() {
        AnalyticsOffloadSuccess analyticsOffloadSuccess1 = new AnalyticsOffloadSuccess(356480083215933l, 1, "kkPolicy1",
                "AHD", "ssid1", "40424",
                1, 1, new Date(), new Date(), 10, "AUTO");
        AnalyticsOffloadSuccess analyticsOffloadSuccess2 = new AnalyticsOffloadSuccess(356480083215933l, 2, "kkPolicy2",
                "AHD", "ssid2", "40424",
                1, 1, new Date(), new Date(), 10, "AUTO");
        AnalyticsOffloadSuccess analyticsOffloadSuccess3 = new AnalyticsOffloadSuccess(356480083215933l, 3, "kkPolicy3",
                "AHD", "ssid3", "40424",
                1, 1, new Date(), new Date(), 10, "AUTO");
        AnalyticsOffloadSuccess analyticsOffloadSuccess4 = new AnalyticsOffloadSuccess(356480083215933l, 4, "kkPolicy4",
                "AHD", "ssid4", "40424",
                1, 1, new Date(), new Date(), 10, "AUTO");
        AnalyticsOffloadSuccess analyticsOffloadSuccess5 = new AnalyticsOffloadSuccess(356480083215933l, 5, "kkPolicy5",
                "AHD", "ssid5", "40424",
                1, 1, new Date(), new Date(), 10, "AUTO");


        AnalyticsRealmPojoManager.insertOffloadSuccess(analyticsOffloadSuccess1, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadSuccess(analyticsOffloadSuccess2, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadSuccess(analyticsOffloadSuccess3, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadSuccess(analyticsOffloadSuccess4, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadSuccess(analyticsOffloadSuccess5, RealmOperationType.INSERT);
        EliteSession.eLog.d(MODULE, "Inserted Success Dummy Records");
    }

    private static void insertFailDummyRecord() {
        AnalyticsOffloadFail fail1 = new AnalyticsOffloadFail(356480083215933l, "1", "ssid1",
                "40424", 1, "kkPolicy1",
                new Date(), "1", "categorytype1", "category1", "10", "AUTO");
        AnalyticsOffloadFail fail2 = new AnalyticsOffloadFail(356480083215933l, "2", "ssid1",
                "40424", 1, "kkPolicy2",
                new Date(), "1", "categorytype1", "category1", "10", "AUTO");
        AnalyticsOffloadFail fail3 = new AnalyticsOffloadFail(356480083215933l, "3", "ssid1",
                "40424", 1, "kkPolicy3",
                new Date(), "1", "categorytype1", "category1", "10", "AUTO");
        AnalyticsOffloadFail fail4 = new AnalyticsOffloadFail(356480083215933l, "4", "ssid1",
                "40424", 1, "kkPolicy4",
                new Date(), "1", "categorytype1", "category1", "10", "AUTO");
        AnalyticsOffloadFail fail5 = new AnalyticsOffloadFail(356480083215933l, "5", "ssid1",
                "40424", 1, "kkPolicy5",
                new Date(), "1", "categorytype1", "category1", "10", "AUTO");
        AnalyticsOffloadFail fail6 = new AnalyticsOffloadFail(356480083215933l, "6", "ssid1",
                "40424", 1, "kkPolicy6",
                new Date(), "1", "categorytype1", "category1", "10", "AUTO");
        AnalyticsOffloadFail fail7 = new AnalyticsOffloadFail(356480083215933l, "7", "ssid1",
                "40424", 1, "kkPolicy7",
                new Date(), "1", "categorytype1", "category1", "10", "AUTO");
        AnalyticsRealmPojoManager.insertOffloadFail(fail1, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadFail(fail2, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadFail(fail3, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadFail(fail4, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadFail(fail5, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadFail(fail6, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertOffloadFail(fail7, RealmOperationType.INSERT);
        EliteSession.eLog.d(MODULE, "Inserted Fail Dummy Records");
    }

    private static void insertPolicyFetchRecord() {
        AnalyticsPolicyDetails policyDetails1 = new AnalyticsPolicyDetails(356480083215933l, 1, "kkPolicy1", "AHD"
                , new Date(), "40424", "Success", "none");
        AnalyticsPolicyDetails policyDetails2 = new AnalyticsPolicyDetails(356480083215933l, 1, "kkPolicy1", "AHD"
                , new Date(), "40424", "Success", "none");
        AnalyticsPolicyDetails policyDetails3 = new AnalyticsPolicyDetails(356480083215933l, 1, "kkPolicy1", "AHD"
                , new Date(), "40424", "Success", "none");
        AnalyticsPolicyDetails policyDetails4 = new AnalyticsPolicyDetails(356480083215933l, 1, "kkPolicy1", "AHD"
                , new Date(), "40424", "Success", "none");
        AnalyticsRealmPojoManager.insertPolicyDetails(policyDetails1, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertPolicyDetails(policyDetails2, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertPolicyDetails(policyDetails3, RealmOperationType.INSERT);
        AnalyticsRealmPojoManager.insertPolicyDetails(policyDetails4, RealmOperationType.INSERT);
    }

    public static Map<Class, Double> getPercentageMap() {
        double failPer;
        double successPer;
        double policyfetchPer;
        try {
//            String weightage = ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANLTCS_RECORD_FETCH_WEIGHTAGE, "fail:2,success:1,policyfetch:1");
            String weightage = "fail:2,success:1,policyfetch:1";
            Map<String, String> weightageBykey = getKeyValuesFromWeightageStr(weightage);
            int failWeight = Integer.parseInt(weightageBykey.get("fail"));
            int successWeight = Integer.parseInt(weightageBykey.get("success"));
            int policyfetchWeight = Integer.parseInt(weightageBykey.get("policyfetch"));
            int total = failWeight + successWeight + policyfetchWeight;
            failPer = (failWeight * 100) / total;
            successPer = (successWeight * 100) / total;
            policyfetchPer = (policyfetchWeight * 100) / total;
        } catch (Exception e) {
            EliteSession.eLog.e("Error while calculationg percentage for fetch record message:" + e.getMessage());
            e.printStackTrace();
            failPer = 50;
            successPer = 25;
            policyfetchPer = 25;
        }
        Map<Class, Double> percentageByClass = new LinkedHashMap<>();
        percentageByClass.put(AnalyticsOffloadFail.class, failPer);
        percentageByClass.put(AnalyticsOffloadSuccess.class, successPer);
        percentageByClass.put(AnalyticsPolicyDetails.class, policyfetchPer);
        EliteSession.eLog.d(MODULE, "Percentage Map:"+ percentageByClass);
        return percentageByClass;
    }

    public static Map getKeyValuesFromWeightageStr(String str) {
        Map map = new HashMap();
        String[] keyValueParts = str.split(",");
        for (String s : keyValueParts) {
            String parts[] = s.split(":");
            map.put(parts[0], parts[1]);
        }
        return map;
    }
}
