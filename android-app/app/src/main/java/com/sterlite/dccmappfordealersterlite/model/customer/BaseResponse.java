package com.sterlite.dccmappfordealersterlite.model.customer;

/**
 * Created by elitecore on 11/10/18.
 */

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("responseCode")
    private long responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("exception")
    private Throwable exception;

    public long getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(long responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return this.responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Throwable getException() {
        return this.exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String toString() {
        StringBuilder strResultObject = new StringBuilder();
        strResultObject = strResultObject.append("BaseResponse [responseCode=").append(this.responseCode)
                .append(",responseMessage=").append(this.responseMessage);
        strResultObject = strResultObject.append(",exception=").append(this.exception);
        strResultObject = strResultObject.append("]");
        return strResultObject.toString();
    }
}