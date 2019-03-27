package com.sterlite.dccmappfordealersterlite.model.MyOrdersListing;

import org.codehaus.jackson.annotate.JsonProperty;

public class Order {
    String name, total;
    boolean isOpen=true, isTrackable=true;
    @JsonProperty("code")
    private String orderId;
    @JsonProperty("placed")
    private String date;
    @JsonProperty("status")
    private String status;
    @JsonProperty("statusDisplay")
    private String orderStatus;
    @JsonProperty("total")
    private Total objTotal;

    public Order(String orderId, String date, String name, String orderStatus, String total, boolean isOpen, boolean isTrackable) {
        this.orderId = orderId;
        this.date = date;
        this.name = name;
        this.orderStatus = orderStatus;
        this.total = total;
        this.isOpen = isOpen;
        this.isTrackable = isTrackable;
    }

    public Order(String orderId, String date, String status, String orderStatus, Total objTotal) {
        this.orderId = orderId;
        this.date = date;
        this.status = status;
        this.orderStatus = orderStatus;
        this.objTotal = objTotal;
    }

    public Order() {
    }

    @JsonProperty("code")
    public String getOrderId() {
        return orderId;
    }
    @JsonProperty("code")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @JsonProperty("placed")
    public String getDate() {
        return date;
    }
    @JsonProperty("placed")
    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isTrackable() {
        return isTrackable;
    }

    public void setTrackable(boolean trackable) {
        isTrackable = trackable;
    }
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("total")
    public Total getObjTotal() {
        return objTotal;
    }
    @JsonProperty("total")
    public void setObjTotal(Total objTotal) {
        this.objTotal = objTotal;
    }
}
