package com.elitecorelib.core.pojo;

import io.realm.RealmList;

/**
 * Created by harshesh.soni on 3/27/2018.
 */
public class PojoBlackListSSID {

    private String responseMessage,requestType;
    private int responseCode;
    private RealmList<PojoBlackListData> responseData;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public RealmList<PojoBlackListData> getResponseData() {
        return responseData;
    }

    public void setResponseData(RealmList<PojoBlackListData> responseData) {
        this.responseData = responseData;
    }
}
