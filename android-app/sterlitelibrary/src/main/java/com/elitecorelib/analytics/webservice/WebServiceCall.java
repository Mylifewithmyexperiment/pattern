package com.elitecorelib.analytics.webservice;

import com.elitecorelib.analytics.constants.WSConstants;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadFail;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadPojo;
import com.elitecorelib.analytics.pojo.AnalyticsOffloadSuccess;
import com.elitecorelib.analytics.pojo.AnalyticsWSResponse;
import com.elitecorelib.analytics.pojo.BaseDTO;
import com.elitecorelib.analytics.utility.AnalyticsUtility;
import com.elitecorelib.core.CoreConstants;
import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.LibraryApplication;
import com.elitecorelib.core.services.ConnectionManagerClass;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;
import com.elitecorelib.core.utility.ElitelibUtility;
import com.elitecorelib.core.utility.SharedPreferencesConstant;
import com.elitecorelib.core.utility.SharedPreferencesTask;

import java.util.List;

public class WebServiceCall {

    private static final String MODULE = "AnalyticWebServiceCall";

    private static final SharedPreferencesTask task = LibraryApplication.getLibraryApplication().getlibrarySharedPreferences();

    private static String getAnalyticsURL() {
//        return ElitelibUtility.getKeyPairvalue(SharedPreferencesConstant.ANALYTICS_URL, "http://103.23.140.233:42080/ccc/api/");
        return "http://103.23.140.233:42080/ccc/api/";
    }

    public static void callWS(Object object, ConnectionManagerCompleteListner callbackListner, int requestId, String url) {
        ConnectionManagerTaskNew connectionManagerTaskNew = new ConnectionManagerTaskNew(callbackListner, requestId);
        String requestBody = "";
        requestBody = AnalyticsUtility.toJSON(object);
        EliteSession.eLog.d(MODULE, "Calling Web Service:" + url + "\t Parameter:" + requestBody);
        connectionManagerTaskNew.execute(requestBody, url);
    }

    public static void callWS(ConnectionManagerCompleteListner callbackListner, int requestId, String url) {
        callWS(null, callbackListner, requestId, url);
    }


    public static void callAnalyticsWS(AnalyticsOffloadPojo analyticsOffloadPojo, ConnectionManagerCompleteListner callbackListner, int requestId) {
        callWS(analyticsOffloadPojo, callbackListner, requestId, getAnalyticsURL() + WSConstants.OFFLOADSTATISTICS);
    }

    public static boolean isSuccessResponse(AnalyticsWSResponse response) {
        if (response != null) {
            if (response.getResponseCode() == 200 || response.getResponseCode() == 1)
                return true;
        }
        return false;
    }
}
