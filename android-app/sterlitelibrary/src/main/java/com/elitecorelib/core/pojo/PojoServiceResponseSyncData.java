package com.elitecorelib.core.pojo;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;

/**
 * @author viratsinh.parmar
 *         <p>
 *         Service Response parsing for the Sync Data
 */
public class PojoServiceResponseSyncData {


    private static final long serialVersionUID = 1L;

    private String responseMessage;
    private int responseCode;
    private String requestType;
    private RealmList<PojoSyncData> responseData;


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

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public RealmList<PojoSyncData> getResponseData() {
        return responseData;
    }

    public void setResponseData(RealmList<PojoSyncData> responseData) {
        this.responseData = responseData;
    }
}
