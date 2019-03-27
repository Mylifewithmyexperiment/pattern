package com.elitecorelib.core.pojo;

import java.io.Serializable;

public class PojoServiceResponseNotification implements Serializable {

    private static final long serialVersionUID = 3280394759140733685L;
    private String responseMessage;
    private int responseCode;
    private String requestType;
    private Message responseData;


    public Message getResponseData() {
        return responseData;
    }

    public void setResponseData(Message responseData) {
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

    public class Message {

        private String message;
        private String termsAndConditionMode;
        private String termsAndCondition;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTermsAndConditionMode() {
            return termsAndConditionMode;
        }

        public void setTermsAndConditionMode(String termsAndConditionMode) {
            this.termsAndConditionMode = termsAndConditionMode;
        }

        public String getTermsAndCondition() {
            return termsAndCondition;
        }

        public void setTermsAndCondition(String termsAndCondition) {
            this.termsAndCondition = termsAndCondition;
        }

    }

}

