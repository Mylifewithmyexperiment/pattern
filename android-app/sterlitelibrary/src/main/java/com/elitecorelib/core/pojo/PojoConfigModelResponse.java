package com.elitecorelib.core.pojo;

import java.io.Serializable;

public class PojoConfigModelResponse implements Serializable {

    private static final long serialVersionUID = 3280394759140733685L;
    private String responseMessage;
    private int responseCode;
    private String requestType;
    private PojoConfigModel responseData;

    public PojoConfigModel getResponceData() {
        return responseData;
    }

    public void setResponceData(PojoConfigModel responceData) {
        this.responseData = responceData;
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
