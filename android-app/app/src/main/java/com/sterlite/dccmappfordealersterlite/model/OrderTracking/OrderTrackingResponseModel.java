package com.sterlite.dccmappfordealersterlite.model.OrderTracking;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import com.sterlite.dccmappfordealersterlite.model.Track;

public class OrderTrackingResponseModel {
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("responseDescription")
    private Object responseDescription;
    @JsonProperty("displayOrderStatus")
    private String displayOrderStatus;
    @JsonProperty("currentOrderStatus")
    private String currentOrderStatus;
    @JsonProperty("statuses")
    private List<String> statuses = null;

    @JsonIgnore
    private List<Track> trackArrayList=new ArrayList<>();
    @JsonIgnore
    private String currentOrderStatusMessage;

    @JsonProperty("currentOrderStatus")
    public String getCurrentOrderStatus() {
        return currentOrderStatus;
    }

    @JsonProperty("currentOrderStatus")
    public void setCurrentOrderStatus(String currentOrderStatus) {
        this.currentOrderStatus = currentOrderStatus;
    }

    @JsonProperty("statuses")
    public List<String> getStatuses() {
        return statuses;
    }

    @JsonProperty("statuses")
    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public List<Track> getTrackArrayList() {
        return trackArrayList;
    }

    public void setTrackArrayList(List<Track> trackArrayList) {
        this.trackArrayList = trackArrayList;
    }

    public String getCurrentOrderStatusMessage() {
        return currentOrderStatusMessage;
    }

    public void setCurrentOrderStatusMessage(String currentOrderStatusMessage) {
        this.currentOrderStatusMessage = currentOrderStatusMessage;
    }
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
    @JsonProperty("responseDescription")
    public Object getResponseDescription() {
        return responseDescription;
    }
    @JsonProperty("responseDescription")
    public void setResponseDescription(Object responseDescription) {
        this.responseDescription = responseDescription;
    }

    public String getDisplayOrderStatus() {
        return displayOrderStatus;
    }

    public void setDisplayOrderStatus(String displayOrderStatus) {
        this.displayOrderStatus = displayOrderStatus;
    }
}
