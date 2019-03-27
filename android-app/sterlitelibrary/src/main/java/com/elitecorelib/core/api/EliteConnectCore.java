package com.elitecorelib.core.api;

import com.elitecorelib.core.EliteSession;
import com.elitecorelib.core.listner.CoreTaskCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerCompleteListner;
import com.elitecorelib.core.services.ConnectionManagerTaskNew;


public class EliteConnectCore implements ConnectionManagerCompleteListner {

    private static final String MODULE = "EliteConnectCore";
    private CoreTaskCompleteListner listner;
    private int requestId;

    /**
     * @param listner
     */
    public EliteConnectCore(CoreTaskCompleteListner listner, int requestId) {
        this.listner = listner;
        this.requestId = requestId;

    }

    /**
     * @param url
     * @param inputJSON
     */
    public void invokeService(final String url, final String inputJSON) {
        //		if(!isLicenseValidated)
        //			throw new Exception("Application ID is not Valid");


        try {
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew");
            new ConnectionManagerTaskNew(EliteConnectCore.this, requestId).execute(inputJSON, url);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " Error while Calling ConnectionManagerTaskNew " + e.getMessage());
        }


    }

    public void invokeService(final String url, final String inputJSON, String contentType) {
        //		if(!isLicenseValidated)
        //			throw new Exception("Application ID is not Valid");
        try {
            EliteSession.eLog.d(MODULE + " Calling ConnectionManagerTaskNew");
            new ConnectionManagerTaskNew(EliteConnectCore.this, requestId).execute(inputJSON, url, contentType);
        } catch (Exception e) {
            EliteSession.eLog.e(MODULE + " Error while Calling ConnectionManagerTaskNew " + e.getMessage());
        }
    }

    @Override
    public void onConnnectionManagerTaskComplete(String result, int requestId) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        EliteSession.eLog.i(MODULE + " onConnnectionManagerTaskComplete Result is ");
        listner.onServiceTaskComplete(result, requestId);
    }
}
