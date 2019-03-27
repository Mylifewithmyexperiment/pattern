package com.elitecorelib.core.pojo;

import java.io.Serializable;
import java.util.List;

public class PojoServiceResponseWiFiData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String responseMessage;
    private int responseCode;
    private String requestType;
    private List<PojoWiFiProfile> responseData;

    public List<PojoWiFiProfile> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<PojoWiFiProfile> responseData) {
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
