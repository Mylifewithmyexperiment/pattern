package com.sterlite.dccmappfordealersterlite.model.MyOrdersListing;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by etech-10 on 18/9/18.
 */

public class MyOrdersListResponseModel {

    @JsonProperty("orders")
    private List<Order> orders;
//    @JsonProperty("pagination")
//    private Pagination pagination;
    @JsonProperty("orders")
    public List<Order> getOrders() {
        return orders;
    }
    @JsonProperty("responseCode")
    private String responseCode;
    @JsonProperty("responseMessage")
    private String responseMessage;
    @JsonProperty("responseDescription")
    private Object responseDescription;

    @JsonProperty("orders")
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

//    @JsonProperty("pagination")
//    public Pagination getPagination() {
//        return pagination;
//    }

//    @JsonProperty("pagination")
//    public void setPagination(Pagination pagination) {
//        this.pagination = pagination;
//    }

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
}
