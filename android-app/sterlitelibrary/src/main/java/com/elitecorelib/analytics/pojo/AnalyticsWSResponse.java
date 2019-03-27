package com.elitecorelib.analytics.pojo;

import com.elitecorelib.core.pojo.PojoWiFiProfile;

import java.util.List;

public class AnalyticsWSResponse {
    private static final long serialVersionUID = 1L;
    private String responseMessage;
    private int responseCode;
    private String requestType;
    private Object responseData;

    public Object getResponseData() {
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

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

}
