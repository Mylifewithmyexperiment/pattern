package com.elitecorelib.core.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmObject;

/**
 * Created by harshesh.soni on 1/3/2017.
 */
public class PojoServerKeyMapping implements Serializable {

    private String responseMessage;
    private int responseCode;
    private String requestType;
    private List<KEYPAIR> responseData;

    protected PojoServerKeyMapping(Parcel in) {
        responseMessage = in.readString();
        responseCode = in.readInt();
        requestType = in.readString();
    }

    public List<KEYPAIR> getResponseData() {
        return responseData;
    }

    public void setResponseData(List<KEYPAIR> responseData) {
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

    public class KEYPAIR{

        private String fieldName;
        private String paramValue;
        private String paramKey;

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getParamValue() {
            return paramValue;
        }

        public void setParamValue(String paramValue) {
            this.paramValue = paramValue;
        }

        public String getParamKey() {
            return paramKey;
        }

        public void setParamKey(String paramKey) {
            this.paramKey = paramKey;
        }
    }

}