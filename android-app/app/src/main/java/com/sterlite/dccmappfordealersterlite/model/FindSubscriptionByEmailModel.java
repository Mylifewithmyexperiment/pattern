package com.sterlite.dccmappfordealersterlite.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by elitecore on 19/9/18.
 */

public class FindSubscriptionByEmailModel {
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("responseDescription")
    private Object responseDescription;


    @JsonProperty("dnNumber")
    private String dnNumber;

    public String getDnNumber() {
        return dnNumber;
    }

    public void setDnNumber(String dnNumber) {
        this.dnNumber = dnNumber;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Object getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(Object responseDescription) {
        this.responseDescription = responseDescription;
    }


    @Override
    public String toString() {
        return "FindSubscriptionByEmailModel{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", responseDescription=" + responseDescription +
                '}';
    }
}
