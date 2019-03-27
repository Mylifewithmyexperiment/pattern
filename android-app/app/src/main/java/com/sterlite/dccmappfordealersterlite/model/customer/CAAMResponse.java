package com.sterlite.dccmappfordealersterlite.model.customer;

/**
 * Created by elitecore on 11/10/18.
 */

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CAAMResponse<T> extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("responseMessageDetails")
    private List<ResponseMessageDetail> responseMessageDetails;

    @JsonProperty("responseObject")
    private T responseObject;

    public CAAMResponse() {
    }

    public CAAMResponse(List<ResponseMessageDetail> responseMessage) {
        this.responseMessageDetails = responseMessage;
    }

    public CAAMResponse(List<ResponseMessageDetail> responseMessageDetails, T responseObject) {
        this.responseMessageDetails = responseMessageDetails;
        this.responseObject = responseObject;
    }

    public List<ResponseMessageDetail> getResponseMessageDetails() {
        return this.responseMessageDetails;
    }

    public void setResponseMessageDetails(List<ResponseMessageDetail> responseMessageDetails) {
        this.responseMessageDetails = responseMessageDetails;
    }

    public T getResponseObject() {
        return this.responseObject;
    }

    public void setResponseObject(T responseObject) {
        this.responseObject = responseObject;
    }

    public String toString() {
        return "CamResponse [responseMessageDetails=" + this.responseMessageDetails + ", responseObject="
                + this.responseObject + ", responseCode=" + this.getResponseCode() + ", responseMessage="
                + this.getResponseMessage() + ", exception=" + this.getException() + "]";
    }
}