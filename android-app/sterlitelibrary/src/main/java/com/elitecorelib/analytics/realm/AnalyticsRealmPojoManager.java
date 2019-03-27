package com.elitecorelib.analytics.realm;

import com.elitecorelib.analytics.pojo.AnalyticsOffloadFail;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadSuccess;
import com.elitecorelib.analytics.pojo.AnalyticsPolicyDetails;

import java.util.HashMap;
import java.util.Map;

import io.realm.RealmModel;

public class AnalyticsRealmPojoManager {

    private static AnalyticsRealmManager<AnalyticsOffloadFail> offloadFailRM;
    private static AnalyticsRealmManager<AnalyticsOffloadSuccess> offloadSuccessRM;
    private static AnalyticsRealmManager<AnalyticsPolicyDetails> policyDetailsRM;

    private static Map<Class,AnalyticsRealmManager> map = new HashMap();

    static {
        offloadFailRM = new AnalyticsRealmManager<AnalyticsOffloadFail>(AnalyticsOffloadFail.class);
        offloadSuccessRM = new AnalyticsRealmManager<AnalyticsOffloadSuccess>(AnalyticsOffloadSuccess.class);
        policyDetailsRM = new AnalyticsRealmManager<AnalyticsPolicyDetails>(AnalyticsPolicyDetails.class);
        map.put(AnalyticsOffloadFail.class, offloadFailRM);
        map.put(AnalyticsOffloadSuccess.class, offloadSuccessRM);
        map.put(AnalyticsPolicyDetails.class, policyDetailsRM);
    }

    public synchronized static <T extends RealmModel> AnalyticsRealmManager<T> getRealmManager(Class<T> type) {
        AnalyticsRealmManager realmManager = map.get(type);
        if(realmManager == null) {
            realmManager = new AnalyticsRealmManager<T>(type);
            map.put(type,realmManager);
        }
        return realmManager;
    }

    public static AnalyticsRealmManager<AnalyticsOffloadFail> getOffloadFailRM() {
        return offloadFailRM;
    }

    public static AnalyticsRealmManager<AnalyticsOffloadSuccess> getOffloadSuccessRM() {
        return offloadSuccessRM;
    }



    public static AnalyticsRealmManager<AnalyticsPolicyDetails> getPolicyDetailsRM() {
        return policyDetailsRM;
    }

    public static void insertOffloadFail(AnalyticsOffloadFail analyticsOffloadFail, RealmOperationType type) {
        if (type == RealmOperationType.INSERT) {
            analyticsOffloadFail.setId(offloadFailRM.getCountId("id"));
        }
        offloadFailRM.insertData(analyticsOffloadFail);
    }

    public static void insertOffloadSuccess(AnalyticsOffloadSuccess analyticsOffloadSuccess, RealmOperationType type) {
        if (type == RealmOperationType.INSERT) {
            analyticsOffloadSuccess.setId(offloadSuccessRM.getCountId("id"));
        }
        offloadSuccessRM.insertData(analyticsOffloadSuccess);
    }

    public static void insertPolicyDetails(AnalyticsPolicyDetails analyticsPolicyDetails, RealmOperationType type) {
        if (type == RealmOperationType.INSERT) {
            analyticsPolicyDetails.setId(policyDetailsRM.getCountId("id"));
        }
        policyDetailsRM.insertData(analyticsPolicyDetails);
    }
}
