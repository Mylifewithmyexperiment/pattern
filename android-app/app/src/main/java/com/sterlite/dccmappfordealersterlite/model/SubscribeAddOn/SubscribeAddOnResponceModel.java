package com.sterlite.dccmappfordealersterlite.model.SubscribeAddOn;

import org.codehaus.jackson.annotate.JsonProperty;

public class SubscribeAddOnResponceModel {
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("accountNumber")
    private Object accountNumber;
    @JsonProperty("responseObject")
    private Object responseObject;
    @JsonProperty("externalSystemDataList")
    private Object externalSystemDataList;
    @JsonProperty("responseCode")
    public String getResponseCode() {
        return responseCode;
    }

    @JsonProperty("responseCode")
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @JsonProperty("responseMessage")
    public String getResponseMessage() {
        return responseMessage;
    }

    @JsonProperty("responseMessage")
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @JsonProperty("accountNumber")
    public Object getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty("accountNumber")
    public void setAccountNumber(Object accountNumber) {
        this.accountNumber = accountNumber;
    }

    @JsonProperty("responseObject")
    public Object getResponseObject() {
        return responseObject;
    }

    @JsonProperty("responseObject")
    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    @JsonProperty("externalSystemDataList")
    public Object getExternalSystemDataList() {
        return externalSystemDataList;
    }

    @JsonProperty("externalSystemDataList")
    public void setExternalSystemDataList(Object externalSystemDataList) {
        this.externalSystemDataList = externalSystemDataList;
    }


}
