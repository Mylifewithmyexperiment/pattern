package com.elitecorelib.core.pojo;

import java.io.Serializable;
import java.util.List;

public class PojoServiceResponseLocation implements Serializable {

    private static final long serialVersionUID = 3280394759140733685L;
    private String responseMessage;
    private int responseCode;
    private String requestType;
    private List<PojoLocation> responseData;

    public List<PojoLocation> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<PojoLocation> responseData) {
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
